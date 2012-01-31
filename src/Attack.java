/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.util.*;

public class Attack {
    AttackResponse[][][] arena;

    public Attack()
    {
        /* Create array of size of the game */
        arena = new AttackResponse[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ];

        /* Initialize the game array to UNKNOWN */
        for( int i = 0; i < arena.length; i++ )
            for( int j = 0; j < arena[i].length; j++ )
                for( int k = 0; k < arena[i][j].length; k++ )
                    arena[i][j][k] = AttackResponse.UNKNOWN;
        }
}
