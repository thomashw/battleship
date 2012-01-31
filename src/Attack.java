/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.util.*;

public class Attack {
    /* Tracks what turn we're on */
    private static int currentTurn;

    /* Holds HIT, MISS, or UNKNOWN info on all squares of the arena */
    AttackResponse[][][] arena;

    /* Each index holds the probability of a ship being at that index in the arena */
    int[][][] shipProbability;

    /* Holds the coordinate with the current highest probability of holding a ship */
    int currentBestAttack;
    Coordinate bestAttackCoordinate;

    /* Used to randomly fire on opponent */
    Random rand;

    public Attack()
    {
        /* Initialize to 1 for first turn */
        currentTurn = 1;

        /* Create array of size of the game to holds HIT/MISS/UNKNOWN for each square */
        arena = new AttackResponse[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Create array of size of the game to hold probability of a ship being at each square */
        shipProbability = new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

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

        /* Generate a random point */
        do {
            x = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
            y = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
            z = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
        } while( arena[x][y][z] != AttackResponse.UNKNOWN );

        /* Update best attack coordinate */
        bestAttackCoordinate.setX( x );
        bestAttackCoordinate.setY( y );
        bestAttackCoordinate.setZ( z );

        /* Find the most likely square to contain a ship (also updates best attack coordinate) */
        calculateArenaProbabilities();

        return bestAttackCoordinate;
    }

    private void calculateArenaProbabilities()
    {
        currentBestAttack = 0;

        for( int x = 0; x < shipProbability.length; x++ )
            for( int y = 0; y < shipProbability[x].length; y++ )
                for( int z = 0; z < shipProbability[x][y].length; z++ ) {
                    shipProbability[x][y][z] = 0;

                    /* If the cell us UNKNOWN, calculate how many ways ships could fit into it  */
                    /* and update the probability array. Otherwise, skip it.                    */
                    if( arena[x][y][z] != AttackResponse.UNKNOWN ) {
                        continue;
                    } else {
                        /* Calculate how many ways FF could fit into this square.                   */
                        /* Assuming the cell is an "end" cell, an FF could fit if there             */
                        /* were 2 cells in the +x direction, 2 cells in the -x direction,           */
                        /* 2 cells in the +y direction, 2 cells in the -y direction,                */
                        /* 2 cells in the +z direction, or 2 cells in the -z direction.             */
                        /* Assuming the cell is a "middle" cell, an FF could fit if there           */
                        /* were 1 cell in the +z & 1 cell in the -z, 1 cell in the +x & 1 cell      */
                        /* in the -x, or 1 cell in the +y & 1 cell in the -y.                       */

                        /* Check if 2 cells in the +x direction */
                        if( x < (shipProbability.length - 2) )
                            if( arena[x+1][y][z] == AttackResponse.UNKNOWN && arena[x+2][y][z] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Check if 2 cells in the -x direction */
                        if( x > 1 )
                            if( arena[x-1][y][z] == AttackResponse.UNKNOWN && arena[x-2][y][z] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Check if 2 cells in the +y direction */
                        if( y < (shipProbability[x].length - 2) )
                            if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y+2][z] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Check if 2 cells in the -y direction */
                        if( y > 1 )
                            if( arena[x][y-1][z] == AttackResponse.UNKNOWN && arena[x][y-2][z] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Check if 2 cells in the +z direction */
                        if( z < (shipProbability[x][y].length - 2) )
                            if( arena[x][y][z+1] == AttackResponse.UNKNOWN && arena[x][y][z+2] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Check if 2 cells in the -z direction */
                        if( z > 1 )
                            if( arena[x][y][z-1] == AttackResponse.UNKNOWN && arena[x][y][z-2] == AttackResponse.UNKNOWN )
                                shipProbability[x][y][z]++;

                        /* Update the best coordinate */
                        if( shipProbability[x][y][z] > currentBestAttack ) {
                            currentBestAttack = shipProbability[x][y][z];

                            bestAttackCoordinate.setX( x );
                            bestAttackCoordinate.setY( y );
                            bestAttackCoordinate.setZ( z );
                        }
                    }
                }
    }

    public void processAttackResponse( String response, Coordinate c )
    {
        /* Check whether the response contains HIT or MISS */
        /* and update 'arena' */
        if( response.contains( Const.kAttackResponseStrHit ) ) {
            arena[c.getX()][c.getY()][c.getZ()] = AttackResponse.HIT;
        } else if( response.contains( Const.kAttackResponseStrMiss ) ) {
            arena[c.getX()][c.getY()][c.getZ()] = AttackResponse.MISS;
        }

        Log.WriteLog( "Result: " + arena[c.getX()][c.getY()][c.getZ()] );
    }
}
