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

                        /* Add probability of hitting the FF */
                        shipProbability[x][y][z] += probabilityFF( x, y, z );

                        /* Add probability of hitting the SSK */
                        shipProbability[x][y][z] += probabilitySSK( x, y, z );

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
    
    private int probabilitySSK( int x, int y, int z )
    {
        /* Assume probability is 0 */
        int probability = 0;

        /* Calculate how many ways SSK could fit around this square.        */
        /* Assuming the cell is an "end" cell, an SSK could fit if there    */
        /* were 4 cells in the +x direction, 4 cells in the -x direction,   */
        /* 4 cells in the +y direction, 4 cells in the -y direction,        */
        /* 4 cells in the +z direction, or 4 cells in the -z direction.     */
        /* Assuming the cell is "one in from the end", an SSK could fit     */
        /* if there were 1 cell in the +x & 3 cells in the -x,              */
        /* 1 cell in the -x & 3 cells in the +x, 1 cell in the -y &         */
        /* 3 cells in the +y, 1 cell in the +y & 3 cells in the -y,         */
        /* 1 cell in the +z & 3 cells in the -z, or 1 cell in the -z        */
        /* & 3 cells in the +z.                                             */
        /* Assuming the cell is the "middle" cell, an SSK could fit if      */
        /* there were 2 cells in the +x & 2 cells in the -x, 2 cells in     */
        /* the +y & 2 cells in the -y, or 2 cells in the +z & 2 cells in    */
        /* -z.                                                              */

        /************************/
        /* Cell is on the "end" */
        /************************/

        /* Check if 4 cells in +x direction */
        if( x < shipProbability.length - 4 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+3][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+4][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 4 cells in -x direction */
        if( x > 3 )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-3][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-4][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 4 cells in +y direction */
        if( y < shipProbability[x].length - 4 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+3][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+4][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 4 cells in -y direction */
        if( y > 3 )
            if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-3][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-4][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 4 cells in +z direction */
        if( z < shipProbability[x][y].length - 4 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+3] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+4] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 4 cells in -z direction */
        if( z > 3 )
            if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-3] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-4] == AttackResponse.UNKNOWN )
                probability++;

        /*********************************/
        /* Cell is "one in from the end" */
        /*********************************/

        /* Check if 1 cell in +x & 3 cells in -x directions */
        if( x < (shipProbability.length - 1) && x > 2 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-3][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +x & 1 cell in -x directions */
        if( x < (shipProbability.length - 3) && x > 0 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+3][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell in +y & 3 cells in -y directions */
        if( y < (shipProbability[x].length - 1) && y > 2 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-3][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +y & 1 cell in -y directions */
        if( y < (shipProbability[x].length - 3) && y > 0 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+3][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell in +z & 3 cells in -z directions */
        if( z < (shipProbability[x][y].length - 1) && z > 2 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-3] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +z & 1 cell in -z directions */
        if( z < (shipProbability[x][y].length - 3) && z > 0 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+3] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /***************************/
        /* Cell is in the "middle" */
        /***************************/

        /* Check if 2 cells in the +x & 2 cells in the -x directions */
        if( x < (shipProbability.length - 2) && x > 1 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +y & 2 cells in the -y directions */
        if( y < (shipProbability.length - 2) && y > 1 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +z & 2 cells in the -z directions */
        if( z < (shipProbability.length - 2) && z > 1 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
    }
    
    private int probabilityFF( int x, int y, int z )
    {
        /* Assume probability is 0 */
        int probability = 0;

        /* Calculate how many ways FF could fit around this square.                 */
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
                probability++;

        /* Check if 2 cells in the -x direction */
        if( x > 1 )
            if( arena[x-1][y][z] == AttackResponse.UNKNOWN && arena[x-2][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +y direction */
        if( y < (shipProbability[x].length - 2) )
            if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y+2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the -y direction */
        if( y > 1 )
            if( arena[x][y-1][z] == AttackResponse.UNKNOWN && arena[x][y-2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +z direction */
        if( z < (shipProbability[x][y].length - 2) )
            if( arena[x][y][z+1] == AttackResponse.UNKNOWN && arena[x][y][z+2] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the -z direction */
        if( z > 1 )
            if( arena[x][y][z-1] == AttackResponse.UNKNOWN && arena[x][y][z-2] == AttackResponse.UNKNOWN )
                probability++;
        
        /* Check if 1 cell +x & 1 cell -x */
        if( x < shipProbability.length - 1 && x > 0 )
            if( arena[x+1][y][z] == AttackResponse.UNKNOWN && arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell +y & 1 cell -y */
        if( y < shipProbability[x].length - 1 && y > 0 )
            if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell +z & 1 cell -z */
        if( z < shipProbability[x][y].length - 1 && z > 0 )
            if( arena[x][y][z+1] == AttackResponse.UNKNOWN && arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
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
