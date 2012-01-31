/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.util.*;

public class Attack {
    /* Holds HIT, MISS, or UNKNOWN info on all squares of the arena */
    AttackResponse[][][] arena;

    /* Used to randomly fire on opponent */
    Random rand;

    public Attack()
    {
        /* Create array of size of the game */
        arena = new AttackResponse[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Initialize the game array to UNKNOWN */
        for( int i = 0; i < arena.length; i++ )
            for( int j = 0; j < arena[i].length; j++ )
                for( int k = 0; k < arena[i][j].length; k++ )
                    arena[i][j][k] = AttackResponse.UNKNOWN;

        /* Initialize Random object used to randomly choose firing points */
        rand = new Random();
    }

    public Coordinate generateAttack()
    {
        int x = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
        int y = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );
        int z = rand.nextInt( Const.kMaxCoord - Const.kMinCoord + 1 );

        return new Coordinate( x, y, z );
    }

    public void processAttackResponse( String response, Coordinate c )
    {
        /* Check whether the response contains HIT or MISS */
        /* and update 'arena' */
         if( response.contains( Const.kAttackResponseHit ) ) {
             arena[c.getX()][c.getY()][c.getZ()] = AttackResponse.HIT;
         } else if( response.contains( Const.kAttackResponseMiss ) ) {
             arena[c.getX()][c.getY()][c.getZ()] = AttackResponse.MISS;
         }
        
        Log.WriteLog( "Result: " + arena[c.getX()][c.getY()][c.getZ()] );
    }
}
