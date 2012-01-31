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

    /* Used to randomly fire on opponent */
    Random rand;

    public Attack()
    {
        /* Initialize to 1 for first turn */
        currentTurn = 1;

        /* Begin by searching for ships */
        attackMode = AttackMode.AttackModeSearch;

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

                        /* Add probability of hitting the FF */
                        shipSearchProbability[x][y][z] += Probability.probabilityFF( x, y, z, arena );

                        /* Add probability of hitting the SSK */
                        shipSearchProbability[x][y][z] += Probability.probabilitySSK( x, y, z, arena );

                        /* Add probability of hitting the DDH */
                        shipSearchProbability[x][y][z] += Probability.probabilityDDH( x, y, z, arena );

                        /* Add probability of hitting the BB */
                        shipSearchProbability[x][y][z] += Probability.probabilityBB( x, y, z, arena );

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

        /* Loop through all the hits and calculate the next best cell to fire at */
        for( int i = 0; i < numHits; i++ ) {
            for( int j = 0; j < 6; j++ ) {

                /* Get the hit coordinates */
                x = hitCoordinates[i].x;
                y = hitCoordinates[i].y;
                z = hitCoordinates[i].z;

                /* Now we want to test all the cells around this hit to see which has the highest probability   */
                /* of containing a ship.                                                                        */

                shipSearchProbability[x][y][z] = 0;

                /* Add probability of hitting the FF */
                shipSearchProbability[x][y][z] += Probability.probabilityFF( x, y, z, arena );

                /* Add probability of hitting the SSK */
                shipSearchProbability[x][y][z] += Probability.probabilitySSK( x, y, z, arena );

                /* Add probability of hitting the DDH */
                shipSearchProbability[x][y][z] += Probability.probabilityDDH( x, y, z, arena );

                /* Add probability of hitting the BB */
                shipSearchProbability[x][y][z] += Probability.probabilityBB( x, y, z, arena );

                /* Update the best coordinate */
                if( shipSearchProbability[x][y][z] > currentBestAttack ) {
                    currentBestAttack = shipSearchProbability[x][y][z];

                    bestAttackCoordinate.x = x;
                    bestAttackCoordinate.y = y;
                    bestAttackCoordinate.z = z;
                }
            }
        }
    }

    public void processAttackResponse( String response, Coordinate c )
    {
        /* Check whether the response contains HIT or MISS */
        /* and update 'arena' */
        if( response.contains( Const.kAttackResponseStrHit ) ) {
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
}
