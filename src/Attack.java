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

    /* Holds an ArrayList of coordinates of the latest HITS while in AttackModeSink */
    ArrayList hitCoordinates;

    /* Holds hit coordinates which we know belong to specific ships                 */
    ArrayList ignoreHitCoordinates;

    /* If we've hit two ships, we don't know which hits were for which ships.   */
    /* But, once we sink a ship, we will know how many hits in the hit array    */
    /* have been accounted for. Once we sink another ship, if the number of     */
    /* hits in the hit array add up to the size of both ships, we can now       */
    /* ignore those hits and go back to "search" mode. If the number of hits    */
    /* don't add up to the size of the sunk ships, we should stay in "sink"     */
    /* mode. This variable holds the number of hits in the hitCoordinates       */
    /* array that can be accounted for by sunk ships.                           */
    int sunkShipHits;

    /* Tracks what turn we're on */
    private static int currentTurn;

    private int currentEdgeCoordinate;

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

    public Attack()
    {
        /* Initialize to 1 for first turn */
        currentTurn = 1;

        /* Begin by searching for ships */
        attackMode = AttackMode.AttackModeSearch;

        /* Track the enemies ships */
        enemyShips = new EnemyShips();

        /* Is used while in AttackModeSink to continue finding the ship. Emptied after each ship is sunk. */
        hitCoordinates = new ArrayList();

        /* Used while in AttackModeSink to ignore certain hit coordinates while searching for more hits. */
        ignoreHitCoordinates = new ArrayList();

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

        /* Switch between the intentional edge coordinates and calculating      */
        /* probabilities for the best squares to fire at until we've            */
        /* fired at all 24 intentional edge coordinates.                        */
        if( currentTurn % 2 == 0 && currentEdgeCoordinate < Const.kEdgeSearchCoordinates.length )
        {
            bestAttackCoordinate.x = Const.kEdgeSearchCoordinates[currentEdgeCoordinate].x;
            bestAttackCoordinate.y = Const.kEdgeSearchCoordinates[currentEdgeCoordinate].y;
            bestAttackCoordinate.z = Const.kEdgeSearchCoordinates[currentEdgeCoordinate].z;
            currentEdgeCoordinate++;
        } else {
            for( int x = 0; x < shipSearchProbability.length; x++ )
                for( int y = 0; y < shipSearchProbability[x].length; y++ )
                    for( int z = 0; z < shipSearchProbability[x][y].length; z++ ) {
                        shipSearchProbability[x][y][z] = 0;

                        /* If the cell is UNKNOWN, calculate how many ways ships could fit into it  */
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

                            /* Add probability of hitting the BB if it's not already sunk */
                            if( enemyShips.CVL == EnemyShips.ShipStatus.ShipStatusAlive ) {
                                shipSearchProbability[x][y][z] += Probability.probabilityCVL( x, y, z, arena );
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
    }

    private void calculateSinkProbabilities()
    {
        currentBestAttack = 0;
        int x, y, z;

        /* Clear the sink array of probabilities */
        emptySinkArray();

        /* Loop through all the hits and calculate the next best cell to fire at */
        for( int i = 0; i < hitCoordinates.size(); i++ ) {

            Coordinate c = (Coordinate)hitCoordinates.get(i);

            /* Get the hit coordinates */
            x = c.x;
            y = c.y;
            z = c.z;

            /* Now we want to test all the cells around this hit to see which has the highest probability   */
            /* of containing a ship.                                                                        */

            shipSinkProbability[x][y][z] = 0;

            /* See if this coordinate is in the array of hit coordinates to ignore. */
            /* If it is, don't calculate the probability for this coordinate.       */
            /* We're ignoring coordinates when we know they belong to ships that    */
            /* have been sunk.                                                      */
            boolean ignore = false;
            for( int j = 0; j < ignoreHitCoordinates.size(); j++ ) {
                Coordinate temp = (Coordinate)ignoreHitCoordinates.get(j);
                if( temp.x == c.x && temp.y == c.y && temp.z == c.z ) {
                    ignore = true;
                    break;
                }
            }

            if( ignore == true )
                continue;

            /* Find probability of hitting the FF again if it's not already sunk */
            if( enemyShips.FF == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityFF( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the SSK again if it's not already sunk */
            if( enemyShips.SSK == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilitySSK( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the DDH again if it's not already sunk */
            if( enemyShips.DDH == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityDDH( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the BB again if it's not already sunk */
            if( enemyShips.BB == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityBB( x, y, z, arena, shipSinkProbability );
            }

            /* Find probability of hitting the CVL again if it's not already sunk */
            if( enemyShips.CVL == EnemyShips.ShipStatus.ShipStatusAlive ) {
                Probability.probabilityCVL( x, y, z, arena, shipSinkProbability );
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
    }

    public void processAttackResponse( String response, Coordinate c )
    {
        /* Check whether the response contains HIT or MISS */
        /* and update 'arena' */
        if( response.contains( Const.kAttackResponseStrHit ) ) {

            /* Update the arena */
            arena[c.x][c.y][c.z] = AttackResponse.HIT;

            /* Update the hit coordinates array */
            hitCoordinates.add( new Coordinate( c.x, c.y, c.z ) );

            if( switchToSearch( response ) ) {
                /* Start searching for a new ship */
                attackMode = AttackMode.AttackModeSearch;
            } else {
                /* Switch to AttackModeSink */
                attackMode = AttackMode.AttackModeSink;
            }
        } else if( response.contains( Const.kAttackResponseStrMiss ) ) {
            /* Update the arena */
            if( arena[c.x][c.y][c.z] == AttackResponse.UNKNOWN )
                arena[c.x][c.y][c.z] = AttackResponse.MISS;
        }
    }


    private boolean switchToSearch(String response)
    {
        /* If we've sunk any ship, we will want to switch back to "search" mode */
        /* and clear the hitCoordinate array as it's only used while sinking    */
        /* a ship.                                                              */
        if( response.contains( Const.kShipNameFF ) ) {
            enemyShips.FF = EnemyShips.ShipStatus.ShipStatusSunk;
            sunkShipHits += Const.kNumCoordinatesFF;

            /* The last fired square obviously belongs to FF since it was sunk  */
            /* so it can be ignored even if we are not                          */
            /* going back to search mode.                                       */
            ignoreHitCoordinates.add(new Coordinate(bestAttackCoordinate));

            /* If the array tracking hit coordinates has the same number as hits    */
            /* as the ship, we can remove those hit coordinates from the array      */
            /* since we don't need to check around those coordinates for new        */
            /* hits anymore. If the number of coordinates in the array does not     */
            /* match the size of the ship, this means we've hit two ships during    */
            /* this attack and we don't really know which coordinates are whose     */
            /* so we can't get rid of any in the array. Furthermore, if we've       */
            /* sunk a ship and hit only its points, we should go back to "search"   */
            /* mode (return true). If we've hit two ships, we should stay in        */
            /* "sink" mode until we've sunk all the ships we've hit.                */
            if( hitCoordinates.size() == Const.kNumCoordinatesFF || hitCoordinates.size() == sunkShipHits ) {
                /* We've sunk the ship and we're removing the hits from the         */
                /* hitCoordinate array, so we should reset the number               */
                /* of hits we're tracking in the hitCoordinate array that account   */
                /* for sunk ships.                                                  */
                sunkShipHits = 0;

                /* Clear the arrayList of hit coordinates since we no longer need   */
                /* to look for ships around the "hit" squares because we've sunk    */
                /* the ship.                                                        */
                hitCoordinates.clear();

                return true;
            }
        } else if( response.contains( Const.kShipNameBB ) ) {
            enemyShips.BB = EnemyShips.ShipStatus.ShipStatusSunk;
            sunkShipHits += Const.kNumCoordinatesBB;
            ignoreHitCoordinates.add(new Coordinate(bestAttackCoordinate));

            if( hitCoordinates.size() == Const.kNumCoordinatesBB || hitCoordinates.size() == sunkShipHits ) {
                sunkShipHits = 0;
                hitCoordinates.clear();
                return true;
            }
        } else if( response.contains( Const.kShipNameCVL ) ) {
            enemyShips.CVL = EnemyShips.ShipStatus.ShipStatusSunk;
            sunkShipHits += Const.kNumCoordinatesCVL;
            ignoreHitCoordinates.add(new Coordinate(bestAttackCoordinate));

            if( hitCoordinates.size() == Const.kNumCoordinatesCVL || hitCoordinates.size() == sunkShipHits ) {
                sunkShipHits = 0;
                hitCoordinates.clear();
                return true;
            }
        } else if( response.contains( Const.kShipNameDDH ) ) {
            enemyShips.DDH = EnemyShips.ShipStatus.ShipStatusSunk;
            sunkShipHits += Const.kNumCoordinatesDDH;
            ignoreHitCoordinates.add(new Coordinate(bestAttackCoordinate));

            if( hitCoordinates.size() == Const.kNumCoordinatesDDH || hitCoordinates.size() == sunkShipHits ) {
                sunkShipHits = 0;
                hitCoordinates.clear();
                return true;
            }
        } else if( response.contains( Const.kShipNameSSK ) ) {
            enemyShips.SSK = EnemyShips.ShipStatus.ShipStatusSunk;
            sunkShipHits += Const.kNumCoordinatesSSK;
            ignoreHitCoordinates.add(new Coordinate(bestAttackCoordinate));

            if( hitCoordinates.size() == Const.kNumCoordinatesSSK || hitCoordinates.size() == sunkShipHits ) {
                sunkShipHits = 0;
                hitCoordinates.clear();
                return true;
            }
        }

        return false;
    }

    private void emptySinkArray()
    {
        for( int i = 0; i < shipSinkProbability.length; i++ )
            for( int j = 0; j < shipSinkProbability[i].length; j++ )
                for( int k = 0; k < shipSinkProbability[i][j].length; k++ )
                    shipSinkProbability[i][j][k] = 0;
    }
}
