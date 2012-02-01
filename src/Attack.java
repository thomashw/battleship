/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.util.*;

public class Attack {

    private enum AttackMode {
        AttackModeSearch,
        AttackModeSink
    }

    /* Once we hit a ship, we'll switch to sink until we've sunk it */
    AttackMode attackMode;

    /* Holds an array of coordinates of the latest HITS while in AttackModeSink */
    int numHits;
    Coordinate[] hitCoordinates;

    /* Tracks what turn we're on */
    private static int currentTurn;

    /* Holds HIT, MISS, or UNKNOWN info on all squares of the arena */
    AttackResponse[][][] arena;

    /* Each index indicates the probability of a ship being at that index in the arena while in search mode */
    int[][][] shipSearchProbability;

    /* Each index indicates the probability of a ship being at that index in the arena while in sink mode */
    int[][][] shipSinkProbability;

    /* Holds the coordinate with the current highest probability of holding a ship */
    int currentBestAttack;
    Coordinate bestAttackCoordinate;

    /* Holds the alive/dead status of the enemies ships */
    EnemyShips enemyShips;

    /* Used to randomly fire on opponent */
    Random rand;

    public Attack()
    {
        /* Initialize to 1 for first turn */
        currentTurn = 1;

        /* Begin by searching for ships */
        attackMode = AttackMode.AttackModeSearch;

        /* Track the enemies ships */
        enemyShips = new EnemyShips();

        /* Is used while in AttackModeSink to continue finding the ship. Emptied after each ship is sunk. */
        numHits = 0;
        hitCoordinates = new Coordinate[Const.kMaxHitCoords];

        /* Create array of size of the game to holds HIT/MISS/UNKNOWN for each square */
        arena = new AttackResponse[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Create array of size of the game to hold probability of a ship being at each square while searching */
        shipSearchProbability = new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Create array of size of the game to hold probability of a ship being at each square while sinking */
        shipSinkProbability = new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Create the best attack coordinate to hold the coordinate with the highest probability */
        /* of hiding a ship. It changes every turn based on misses/hits */
        bestAttackCoordinate = new Coordinate();

        /* Initialize the game array to UNKNOWN */
        for( int i = 0; i < arena.length; i++ )
            for( int j = 0; j < arena[i].length; j++ )
                for( int k = 0; k < arena[i][j].length; k++ )
                    arena[i][j][k] = AttackResponse.UNKNOWN;

        /* Initialize Random object used to randomly choose firing points */
        rand = new Random();
    }

    public void incrementTurn()
    {
        currentTurn++;
    }

    public int getCurrentTurn()
    {
        return currentTurn;
    }

    public Coordinate generateAttack()
    {
        int x, y, z;

        switch (attackMode) {
            case AttackModeSearch:

                /* Generate a random point */
                do {
                    x = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
                    y = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
                    z = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
                } while( arena[x][y][z] != AttackResponse.UNKNOWN );

                /* Update best attack coordinate */
                bestAttackCoordinate.x = x;
                bestAttackCoordinate.y = y;
                bestAttackCoordinate.z = z;

                /* Find the most likely square to contain a ship (also updates best attack coordinate) */
                calculateSearchProbabilities();
                break;
            case AttackModeSink:
                /* Find the most likely square to contain a ship after a hit */
                calculateSinkProbabilities();
                break;
            default:
                break;
        }

        return bestAttackCoordinate;
    }

    private void calculateSearchProbabilities()
    {
        currentBestAttack = 0;

        for( int x = 0; x < shipSearchProbability.length; x++ )
            for( int y = 0; y < shipSearchProbability[x].length; y++ )
                for( int z = 0; z < shipSearchProbability[x][y].length; z++ ) {
                    shipSearchProbability[x][y][z] = 0;

                    /* If the cell us UNKNOWN, calculate how many ways ships could fit into it  */
                    /* and update the probability array. Otherwise, skip it.                    */
                    if( arena[x][y][z] != AttackResponse.UNKNOWN ) {
                        continue;
                    } else {

                        /* Add probability of hitting the FF if it's not already sunk */
                        if( enemyShips.FF == EnemyShips.ShipStatus.ShipStatusAlive ) {
                            shipSearchProbability[x][y][z] += Probability.probabilityFF( x, y, z, arena );
                        }

                        /* Add probability of hitting the SSK if it's not already sunk */
                        if( enemyShips.SSK == EnemyShips.ShipStatus.ShipStatusAlive ) {
                            shipSearchProbability[x][y][z] += Probability.probabilitySSK( x, y, z, arena );
                        }

                        /* Add probability of hitting the DDH if it's not already sunk */
                        if( enemyShips.DDH == EnemyShips.ShipStatus.ShipStatusAlive ) {
                            shipSearchProbability[x][y][z] += Probability.probabilityDDH( x, y, z, arena );
                        }

                        /* Add probability of hitting the BB if it's not already sunk */
                        if( enemyShips.BB == EnemyShips.ShipStatus.ShipStatusAlive ) {
                            shipSearchProbability[x][y][z] += Probability.probabilityBB( x, y, z, arena );
                        }

                        /* Update the best coordinate */
                        if( shipSearchProbability[x][y][z] > currentBestAttack ) {
                            currentBestAttack = shipSearchProbability[x][y][z];

                            bestAttackCoordinate.x = x ;
                            bestAttackCoordinate.y = y;
                            bestAttackCoordinate.z = z;
                        }
                    }
                }
    }

    private void calculateSinkProbabilities()
    {
        currentBestAttack = 0;
        int x, y, z;

        /* Clear the sink array of probabilities */
        emptySinkArray();

        /* Loop through all the hits and calculate the next best cell to fire at */
        for( int i = 0; i < numHits; i++ ) {

            /* Get the hit coordinates */
            x = hitCoordinates[i].x;
            y = hitCoordinates[i].y;
            z = hitCoordinates[i].z;

            /* Now we want to test all the cells around this hit to see which has the highest probability   */
            /* of containing a ship.                                                                        */

            shipSinkProbability[x][y][z] = 0;

            /* Find probability of hitting the FF again if it's not already sunk */
            if( enemyShips.FF == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityFF( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the SSK again if it's not already sunk*/
            if( enemyShips.SSK == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilitySSK( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the DDH again if it's not already sunk*/
            if( enemyShips.DDH == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityDDH( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the BB again if it's not already sunk*/
            if( enemyShips.BB == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityBB( x, y, z, arena, shipSinkProbability );
            }
        }

        /* The array now contains the probabilities of a ship being at points   */
        /* surrounding the hits. Let's find the highest probability and use it   */
        /* for the next fire request.                                           */

        for( int i = 0; i < shipSinkProbability.length; i++ )
            for( int j = 0; j < shipSinkProbability[i].length; j++ )
                for( int k = 0; k < shipSinkProbability[i][j].length; k++ ) {
                    if( shipSinkProbability[i][j][k] > currentBestAttack ) {
                        currentBestAttack = shipSinkProbability[i][j][k];

                        bestAttackCoordinate.x = i;
                        bestAttackCoordinate.y = j;
                        bestAttackCoordinate.z = k;
                    }
                }

        /* If for some reason the program didn't find a good attack, just put it back into search mode  */
        /* to avoid falling into an infinite loop of crappy shots.                                      */
        if( currentBestAttack == 0 ) {
            /* Will stop us from using old values in the hitCoordinate array upon the next hit */
            numHits = 0;

            /* Start searching for a new ship */
            attackMode = AttackMode.AttackModeSearch;
        }
    }

    public void processAttackResponse( String response, Coordinate c )
    {
        /* Check whether the response contains HIT or MISS */
        /* and update 'arena' */
        if( response.contains( Const.kAttackResponseStrHit ) && shipIsSunk( response ) ) {
            /* Update the arena */
            arena[c.x][c.y][c.z] = AttackResponse.HIT;

            /* Will stop us from using old values in the hitCoordinate array upon the next hit */
            numHits = 0;

            /* Start searching for a new ship */
            attackMode = AttackMode.AttackModeSearch;
        } else if( response.contains( Const.kAttackResponseStrHit ) ) {
            /* Update the arena */
            arena[c.x][c.y][c.z] = AttackResponse.HIT;

            /* Switch to AttackModeSink */
            attackMode = AttackMode.AttackModeSink;

            /* Update the hit coordinates array */
            hitCoordinates[numHits++] = new Coordinate( c.x, c.y, c.z );

        } else if( response.contains( Const.kAttackResponseStrMiss ) ) {
            /* Update the arena */
            arena[c.x][c.y][c.z] = AttackResponse.MISS;
        }

        Log.WriteLog( "Result: " + arena[c.x][c.y][c.z] );
    }

    private boolean shipIsSunk( String response )
    {
        /* Assume false */
        boolean sunk = false;

        /* If we've sunk any ship, we will want to switch back to "search" mode */
        /* and clear the hitCoordinate array as it's only used while sinking    */
        /* a ship.                                                              */
        if( response.contains( Const.kShipNameFF ) ) {
            enemyShips.FF = EnemyShips.ShipStatus.ShipStatusSunk;
            sunk = true;
        } else if( response.contains( Const.kShipNameBB ) ) {
            enemyShips.BB = EnemyShips.ShipStatus.ShipStatusSunk;
            sunk = true;
        } else if( response.contains( Const.kShipNameCVL ) ) {
            enemyShips.CVL = EnemyShips.ShipStatus.ShipStatusSunk;
            sunk = true;
        } else if( response.contains( Const.kShipNameDDH ) ) {
            enemyShips.DDH = EnemyShips.ShipStatus.ShipStatusSunk;
            sunk = true;
        } else if( response.contains( Const.kShipNameSSK ) ) {
            enemyShips.SSK = EnemyShips.ShipStatus.ShipStatusSunk;
            sunk = true;
        }

        if( sunk == true ) {

        }

        return sunk;
    }

    private void emptySinkArray()
    {
        for( int i = 0; i < shipSinkProbability.length; i++ )
            for( int j = 0; j < shipSinkProbability[i].length; j++ )
                for( int k = 0; k < shipSinkProbability[i][j].length; k++ )
                    shipSinkProbability[i][j][k] = 0;
    }
}
