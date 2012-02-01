/**
 * Author: Thomas Hewton-Waters
 * Created on: January 31, 2012
 */

public class Probability {

    public static int probabilityBB( int x, int y, int z, AttackResponse[][][] arena )
    {
        return probabilityBB( x, y, z, arena, new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ]);
    }

    public static int probabilityBB( int x, int y, int z, AttackResponse[][][] arena, int[][][] shipSinkProb )
    {
        /* Assume probability is 0 */
        int probability = 0;

        /* Calculate how many ways BB could fit around this square.             */
        /* 8 squares * 3 planes = 24 possibilities.                             */

        /*************/
        /* x-y plane */
        /*************/

        /* The cell is the top-left cell in the x-y plane.                      */
        /* Going clockwise: 2 cells in +y direction, 2 cells in -x direction,   */
        /* 2 cells in -y direction, 2 cells in +x direction.                    */
        if( y < (arena[x].length - 2) && x > 1 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y+2][z] != AttackResponse.MISS &&
                    arena[x-2][y+2][z] != AttackResponse.MISS &&
                    arena[x-2][y+1][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {

                /* If we're in "search" mode and all of the cells are unknown,  */
                /* then the ship could potentially fit here so let's add 1 to   */
                /* the probability. Note the return value of these methods      */
                /* is only used while in search mode.                           */
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                /* If the cell is unknown and we're in "sink" mode, let's add   */
                /* 1 to its probability.                                        */
                /* If it's a hit already, we don't want to shoot there again    */
                /* so let's ignore it.                                          */
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /* The cell is the top-middle cell in the x-y plane.                    */
        /* Going clockwise: 1 cell in +y dir, 2 cells in -x dir, 2 cells in     */
        /* -y dir, 2 cells in +x dir.                                           */
        if( y < (arena[x].length - 1) && y > 0 && x > 1 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS &&
                    arena[x-2][y+1][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-2][y-1][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS ) {

                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;
            }

        /* The cell is the top-right cell in the x-y plane.                     */
        /* Going clockwise: 2 cells in -x dir, 2 cells in -y dir, 2 cells in    */
        /* +x dir, 2 cells in +y dir.                                           */
        if( y > 1 && x > 1 )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-2][y-1][z] != AttackResponse.MISS &&
                    arena[x-2][y-2][z] != AttackResponse.MISS &&
                    arena[x-1][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS ) {

                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;
            }

        /* The cell is the right-middle cell in the x-y plane.                  */
        /* Going clockwise: 1 cell in -x dir, 2 cells in -y dir, 2 cells in     */
        /* +x dir, 2 cells in +y dir.                                           */
        if( y > 1 && x > 0 && x < (arena.length - 1) )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x-1][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x+1][y-2][z] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS ) {

                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is the right-bottom cell in the x-y plane.                  */
        /* Going clockwise: 2 cells in -y dir, 2 cells in +x dir, 2 cells in    */
        /* +y dir, 1 cell in -x dir.                                            */
        if( y > 1 && x < (arena.length - 2) )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x+1][y-2][z] != AttackResponse.MISS &&
                    arena[x+2][y-2][z] != AttackResponse.MISS &&
                    arena[x+2][y-1][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS ) {

                if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is the bottom-middle cell in the x-y plane.                 */
        /* Clockwise: 1 cell in -y, 2 in +x, 2 in +y, 2 in -x, 1 in -y.         */
        if( y > 0 && y < (arena[x].length - 1) &&  x < (arena.length - 2) )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS &&
                    arena[x+2][y-1][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+2][y+1][z] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+1][z] != AttackResponse.MISS ) {

                if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;
            }

        /* The cell is the left-bottom cell in the x-y plane.                   */
        /* Clockwise: 2 cells +x, 2 cells +y, 2 cells -x, 1 cell -y.            */
        if( y < (arena[x].length - 2) && x < (arena.length - 2) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+2][y+1][z] != AttackResponse.MISS &&
                    arena[x+2][y+2][z] != AttackResponse.MISS &&
                    arena[x+1][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+1][z] != AttackResponse.MISS ) {

                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;
            }

        /* The cell is the left-middle cell in the x-y plane.                   */
        /* Clockwise: 1 cell +x, 2 cells +y, 2 cells -x, 2 cells -y.            */
        if( y < (arena[x].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS &&
                    arena[x+1][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {

                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /*************/
        /* x-z plane */
        /*************/

        /* The cell is the top-left cell in the x-z plane.                      */
        /* Going clockwise: 2 cells in +z direction, 2 cells in -x direction,   */
        /* 2 cells in -z direction, 2 cells in +x direction.                    */
        if( z < (arena[x][y].length - 2) && x > 1 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+2] != AttackResponse.MISS &&
                    arena[x-2][y][z+2] != AttackResponse.MISS &&
                    arena[x-2][y][z+1] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {

                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /* The cell is the top-middle cell in the x-z plane.                    */
        /* Going clockwise: 1 cell in +z dir, 2 cells in -x dir, 2 cells in     */
        /* -z dir, 2 cells in +x dir.                                           */
        if( z < (arena[x][y].length - 1) && z > 0 && x > 1 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x-2][y][z+1] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z-1] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS ) {

                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;
            }

        /* The cell is the top-right cell in the x-z plane.                     */
        /* Going clockwise: 2 cells in -x dir, 2 cells in -z dir, 2 cells in    */
        /* +x dir, 2 cells in +z dir.                                           */
        if( z > 1 && x > 1 )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z-1] != AttackResponse.MISS &&
                    arena[x-2][y][z-2] != AttackResponse.MISS &&
                    arena[x-1][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS ) {

                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;
            }

        /* The cell is the right-middle cell in the x-z plane.                  */
        /* Going clockwise: 1 cell in -x dir, 2 cells in -z dir, 2 cells in     */
        /* +x dir, 2 cells in +z dir.                                           */
        if( z > 1 && x > 0 && x < (arena.length - 1) )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS &&
                    arena[x-1][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x+1][y][z-2] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS ) {

                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is the right-bottom cell in the x-z plane.                  */
        /* Going clockwise: 2 cells in -z dir, 2 cells in +x dir, 2 cells in    */
        /* +z dir, 1 cell in -x dir.                                            */
        if( z > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x+1][y][z-2] != AttackResponse.MISS &&
                    arena[x+2][y][z-2] != AttackResponse.MISS &&
                    arena[x+2][y][z-1] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS ) {

                if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is the bottom-middle cell in the x-z plane.                 */
        /* Clockwise: 1 cell in -z, 2 in +x, 2 in +z, 2 in -x.         */
        if( z > 0 && z < (arena[x][y].length - 1) &&  x < (arena.length - 2) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS &&
                    arena[x+2][y][z-1] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z+1] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+1] != AttackResponse.MISS ) {

                if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;

                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;
            }

        /* The cell is the left-bottom cell in the x-z plane.                   */
        /* Clockwise: 2 cells +x, 2 cells +z, 2 cells -x, 1 cell -z.            */
        if( z < (arena[x][y].length - 2) && x < (arena.length - 2) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z+1] != AttackResponse.MISS &&
                    arena[x+2][y][z+2] != AttackResponse.MISS &&
                    arena[x+1][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+1] != AttackResponse.MISS ) {

                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;
            }

        /* The cell is the left-middle cell in the x-z plane.                   */
        /* Clockwise: 1 cell +x, 2 cells +z, 2 cells -x, 2 cells -z.            */
        if( z < (arena[x][y].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS &&
                    arena[x+1][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {

                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        return probability;
    }

    public static int probabilityDDH( int x, int y, int z, AttackResponse[][][] arena )
    {
        return probabilityDDH( x, y, z, arena, new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ] );
    }

    public static int probabilityDDH( int x, int y, int z, AttackResponse[][][] arena, int[][][] shipSinkProb )
    {
        /* Assume probability is 0 */
        int probability = 0;

        /* Calculate how many ways DDH could fit around this square.            */
        /* 5 squares * 3 planes = 15 possibilities.                             */

        /*************/
        /* x-y plane */
        /*************/

        /* The cell is a top cell in the x-y plane. It has 2 cells in the       */
        /* -x direction forming 3 straight cells. There's two cells in the      */
        /* y-plane beside its middle cell forming the +.                        */
        if( x > 1 && y > 0 && y < (arena[x].length - 1) )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS ) {
                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+1][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /* The cell is a bottom cell in the x-y plane. It has 2 cells in the    */
        /* +x direction forming 3 straight cells. There's two cells in the      */
        /* y-plane beside its middle cell forming the +.                        */
        if( x < (arena.length - 2) && y > 0 && y < (arena[x].length - 1) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+1][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is a left cell in the x-y plane. There's 2 cells in the     */
        /* +y direction forming 3 straight cells. There's two cells in the      */
        /* x-plane beside the middle cell forming the +.                        */
        if( y < (arena[x].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y+1][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;
            }

        /* The cell is a right cell in the x-y plane. There's 2 cells in the    */
        /* -y direction forming 3 straight ells. There's two cells in the       */
        /* x-plane beside the middle cell forming the +.                        */
        if( y > 1 && x > 0 && x < (arena.length-1) )
            if (    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS) {
                if (    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y-1][z] == AttackResponse.UNKNOWN)
                    probability++;
                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;
            }

        /* The cell is a middle cell in the x-y plane. There's 1 cell in the +y */
        /* direction, 1 cell in the -y direction, 1 cell in the +x direction, & */
        /* 1 cell in the -x direction.                                          */
        if( y < (arena[x].length - 1) && y > 0 && x < (arena.length - 1) && x > 0 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /*************/
        /* x-z plane */
        /*************/

        /* The cell is a top cell in the x-z plane. It has 2 cells in the       */
        /* -x direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( x > 1 && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS ) {
                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /* The cell is a bottom cell in the x-z plane. It has 2 cells in the    */
        /* +x direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( x < (arena.length - 2) && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;
            }

        /* The cell is a left cell in the x-z plane. There's 2 cells in the     */
        /* +z direction forming 3 straight cells. There's two cells in the      */
        /* x-plane beside the middle cell forming the +.                        */
        if( z < (arena[x][y].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;
            }

        /* The cell is a right cell in the x-z plane. There's 2 cells in the    */
        /* -z direction forming 3 straight ells. There's two cells in the       */
        /* x-plane beside the middle cell forming the +.                        */
        if( z > 1 && x > 0 && x < (arena.length-1) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS ) {
                if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z-1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;
            }

        /* The cell is a middle cell in the x-z plane. There's 1 cell in the +z */
        /* direction, 1 cell in the -z direction, 1 cell in the +x direction, & */
        /* 1 cell in the -x direction.                                          */
        if( z < (arena[x][y].length - 1) && z > 0 && x < (arena.length - 1) && x > 0 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;
            }

        /*************/
        /* y-z plane */
        /*************/

        /* The cell is a top cell in the y-z plane. It has 2 cells in the       */
        /* -y direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( y > 1 && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-1][z-1] != AttackResponse.MISS &&
                    arena[x][y-1][z+1] != AttackResponse.MISS ) {
                if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;
            }

        /* The cell is a bottom cell in the y-z plane. It has 2 cells in the    */
        /* +y direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( y < (arena[x].length - 2) && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+1][z-1] != AttackResponse.MISS &&
                    arena[x][y+1][z+1] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;
            }

        /* The cell is a left cell in the y-z plane. There's 2 cells in the     */
        /* +z direction forming 3 straight cells. There's two cells in the      */
        /* y-plane beside the middle cell forming the +.                        */
        if( z < (arena[x][y].length - 2) && y > 0 && y < (arena[x].length - 1) )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y-1][z+1] != AttackResponse.MISS &&
                    arena[x][y+1][z+1] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z+1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;
            }

        /* The cell is a right cell in the y-z plane. There's 2 cells in the    */
        /* -z direction forming 3 straight ells. There's two cells in the       */
        /* y-plane beside the middle cell forming the +.                        */
        if( z > 1 && y > 0 && y < (arena[x].length-1) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y-1][z-1] != AttackResponse.MISS &&
                    arena[x][y+1][z-1] != AttackResponse.MISS ) {
                if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z-1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;
            }

        /* The cell is a middle cell in the y-z plane. There's 1 cell in the +z */
        /* direction, 1 cell in the -z direction, 1 cell in the +y direction, & */
        /* 1 cell in the -y direction.                                          */
        if( z < (arena[x][y].length - 1) && z > 0 && y < (arena[x].length - 1) && y > 0 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y][z-1]++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;
            }

        return probability;
    }

    public static int probabilitySSK( int x, int y, int z, AttackResponse[][][] arena )
    {
        return probabilitySSK( x, y, z, arena, new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ] );
    }

    public static int probabilitySSK( int x, int y, int z, AttackResponse[][][] arena, int[][][] shipSinkProb )
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
        if( x < arena.length - 4 )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+3][y][z] != AttackResponse.MISS &&
                    arena[x+4][y][z] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+3][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+4][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x+1][y][z]++;
            }

        /* Check if 4 cells in -x direction */
        if( x > 3 )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-3][y][z] != AttackResponse.MISS &&
                    arena[x-4][y][z] != AttackResponse.MISS ) {
                if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-3][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-4][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x-1][y][z]++;
            }

        /* Check if 4 cells in +y direction */
        if( y < arena[x].length - 4 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+3][z] != AttackResponse.MISS &&
                    arena[x][y+4][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+3][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+4][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y+1][z]++;
            }

        /* Check if 4 cells in -y direction */
        if( y > 3 )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-3][z] != AttackResponse.MISS &&
                    arena[x][y-4][z] != AttackResponse.MISS ) {
                if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-3][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-4][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y-1][z]++;
            }

        /* Check if 4 cells in +z direction */
        if( z < arena[x][y].length - 4 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+3] != AttackResponse.MISS &&
                    arena[x][y][z+4] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+3] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+4] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z+1]++;
            }

        /* Check if 4 cells in -z direction */
        if( z > 3 )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-3] != AttackResponse.MISS &&
                    arena[x][y][z-4] != AttackResponse.MISS ) {
                if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-3] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-4] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z-1]++;
            }

        /*********************************/
        /* Cell is "one in from the end" */
        /*********************************/

        /* Check if 1 cell in +x & 3 cells in -x directions */
        if( x < (arena.length - 1) && x > 2 )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-3][y][z] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-3][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x-1][y][z]++;
            }

        /* Check if 3 cells in +x & 1 cell in -x directions */
        if( x < (arena.length - 3) && x > 0 )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+3][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+3][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x-1][y][z]++;
            }

        /* Check if 1 cell in +y & 3 cells in -y directions */
        if( y < (arena[x].length - 1) && y > 2 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-3][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-3][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y-1][z]++;
            }

        /* Check if 3 cells in +y & 1 cell in -y directions */
        if( y < (arena[x].length - 3) && y > 0 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+3][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+3][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y-1][z]++;
            }

        /* Check if 1 cell in +z & 3 cells in -z directions */
        if( z < (arena[x][y].length - 1) && z > 2 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-3] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-3] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z-1]++;
            }

        /* Check if 3 cells in +z & 1 cell in -z directions */
        if( z < (arena[x][y].length - 3) && z > 0 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+3] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+3] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x][y][z+1] != AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] != AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z-1]++;
            }

        /***************************/
        /* Cell is in the "middle" */
        /***************************/

        /* Check if 2 cells in the +x & 2 cells in the -x directions */
        if( x < (arena.length - 2) && x > 1 )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS ) {
                if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                        arena[x-2][y][z] == AttackResponse.UNKNOWN )
                    probability++;
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x-1][y][z]++;
            }

        /* Check if 2 cells in the +y & 2 cells in the -y directions */
        if( y < (arena.length - 2) && y > 1 )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS ) {
                if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                        arena[x][y-2][z] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y-1][z]++;
            }

        /* Check if 2 cells in the +z & 2 cells in the -z directions */
        if( z < (arena.length - 2) && z > 1 )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS ) {
                if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                        arena[x][y][z-2] == AttackResponse.UNKNOWN )
                    probability++;

                if( arena[x][y][z+1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z+1]++;

                if( arena[x][y][z-1] == AttackResponse.UNKNOWN )
                shipSinkProb[x][y][z-1]++;
            }

        return probability;
    }

    public static int probabilityFF( int x, int y, int z, AttackResponse[][][] arena )
    {
        return probabilityFF( x, y, z, arena, new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ] );
    }

    public static int probabilityFF( int x, int y, int z, AttackResponse[][][] arena, int[][][] shipSinkProb )
    {
        /* Assume probability is 0 */
        int searchProbability = 0;

        /* Calculate how many ways FF could fit around this square.                 */
        /* Assuming the cell is an "end" cell, an FF could fit if there             */
        /* were 2 cells in the +x direction, 2 cells in the -x direction,           */
        /* 2 cells in the +y direction, 2 cells in the -y direction,                */
        /* 2 cells in the +z direction, or 2 cells in the -z direction.             */
        /* Assuming the cell is a "middle" cell, an FF could fit if there           */
        /* were 1 cell in the +z & 1 cell in the -z, 1 cell in the +x & 1 cell      */
        /* in the -x, or 1 cell in the +y & 1 cell in the -y.                       */

        /* Check if 2 cells in the +x direction */
        if( x < (arena.length - 2) )
            if( arena[x+1][y][z] != AttackResponse.MISS && arena[x+2][y][z] != AttackResponse.MISS ) {
                shipSinkProb[x+1][y][z]++;
                searchProbability++;
            }

        /* Check if 2 cells in the -x direction */
        if( x > 1 )
            if( arena[x-1][y][z] != AttackResponse.MISS && arena[x-2][y][z] != AttackResponse.MISS ) {
                shipSinkProb[x-1][y][z]++;
                searchProbability++;
            }
        /* Check if 2 cells in the +y direction */
        if( y < (arena[x].length - 2) )
            if( arena[x][y+1][z] != AttackResponse.MISS && arena[x][y+2][z] != AttackResponse.MISS ) {
                shipSinkProb[x][y+1][z]++;
                searchProbability++;
            }
        /* Check if 2 cells in the -y direction */
        if( y > 1 )
            if( arena[x][y-1][z] != AttackResponse.MISS && arena[x][y-2][z] != AttackResponse.MISS ) {
                shipSinkProb[x][y-1][z]++;
                searchProbability++;
            }

        /* Check if 2 cells in the +z direction */
        if( z < (arena[x][y].length - 2) )
            if( arena[x][y][z+1] != AttackResponse.MISS && arena[x][y][z+2] != AttackResponse.MISS ) {
                shipSinkProb[x][y][z+1]++;
                searchProbability++;
            }

        /* Check if 2 cells in the -z direction */
        if( z > 1 )
            if( arena[x][y][z-1] != AttackResponse.MISS && arena[x][y][z-2] != AttackResponse.MISS ) {
                shipSinkProb[x][y][z-1]++;
                searchProbability++;
            }

        /* Check if 1 cell +x & 1 cell -x */
        if( x < arena.length - 1 && x > 0 )
            if( arena[x+1][y][z] != AttackResponse.MISS && arena[x-1][y][z] != AttackResponse.MISS ) {
                if( arena[x+1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x+1][y][z]++;

                if( arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x-1][y][z]++;

                if( arena[x+1][y][z] == AttackResponse.UNKNOWN && arena[x-1][y][z] == AttackResponse.UNKNOWN )
                    searchProbability++;
            }

        /* Check if 1 cell +y & 1 cell -y */
        if( y < arena[x].length - 1 && y > 0 )
            if( arena[x][y+1][z] != AttackResponse.MISS && arena[x][y-1][z] != AttackResponse.MISS ) {
                if( arena[x][y+1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y+1][z]++;

                if( arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    shipSinkProb[x][y-1][z]++;

                if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y-1][z] == AttackResponse.UNKNOWN )
                    searchProbability++;
            }

        /* Check if 1 cell +z & 1 cell -z */
        if( z < arena[x][y].length - 1 && z > 0 )
            if( arena[x][y][z+1] != AttackResponse.MISS && arena[x][y][z-1] != AttackResponse.MISS ) {
                shipSinkProb[x][y][z+1]++;
                shipSinkProb[x][y][z-1]++;
                searchProbability++;
            }

        return searchProbability;
    }

    public static int probabilityCVL( int x, int y, int z, AttackResponse[][][] arena )
    {
        return probabilityCVL( x, y, z, arena, new int[Const.kGameDimensionX][Const.kGameDimensionY][Const.kGameDimensionZ] );
    }

    public static int probabilityCVL( int x, int y, int z, AttackResponse[][][] arena, int[][][] shipSinkProb )
    {
        /* Assume probability is 0 */
        int searchProbability = 0;

        /* Since CVL is symmetrical, I don't have to account for all the axes!      */
        /* Therefore, there should be 13 different possibilities.                   */

        /* The cell is the very bottom cell in the z-x plane. The y-axis is coming  */
        /* out of the page. Need -2y, +2y, +4z, +2x, -2x to fit this shape.         */
        if( z < (arena[x][y].length - 4) && y > 1 && y < (arena[x].length - 2) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+3] != AttackResponse.MISS &&
                    arena[x][y][z+4] != AttackResponse.MISS &&
                    arena[x+1][y][z+2] != AttackResponse.MISS &&
                    arena[x+2][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z+2] != AttackResponse.MISS &&
                    arena[x][y+1][z+2] != AttackResponse.MISS &&
                    arena[x][y+2][z+2] != AttackResponse.MISS &&
                    arena[x][y-1][z+2] != AttackResponse.MISS &&
                    arena[x][y-2][z+2] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y][z+1]++;
            }

        /* The cell is 1 above the very bottom cell in the z-x plane.   */
        /* Need -1z, +3z, +2x, -2x, +2y, -2y to fit this shape.         */
        if( z > 0 && z < (arena[x][y].length - 3) && y > 1 && y < (arena[x].length - 2) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z+3] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS &&
                    arena[x+2][y][z+1] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x-2][y][z+1] != AttackResponse.MISS &&
                    arena[x][y+1][z+1] != AttackResponse.MISS &&
                    arena[x][y+2][z+1] != AttackResponse.MISS &&
                    arena[x][y-1][z+1] != AttackResponse.MISS &&
                    arena[x][y-2][z+1] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y][z-1]++;
                shipSinkProb[x][y][z+1]++;
            }

        /* The cell is in the very middle.                              */
        /* Need +2z, -2z, +2x, -2x, +2y, -2y to fit this shape.         */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 1 && y < (arena[x].length - 2) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z+2] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y][z+1]++;
                shipSinkProb[x][y][z-1]++;
                shipSinkProb[x+1][y][z]++;
                shipSinkProb[x-1][y][z]++;
                shipSinkProb[x][y+1][z]++;
                shipSinkProb[x][y-1][z]++;
            }

        /* The cell is one above the middle.                            */
        /* Need +1z, -3z, +2y, -2y, +2x, -2x to fit this shape.         */
        if( z > 2 && z < (arena[x][y].length - 1) && y > 1 && y < (arena[x].length - 2) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z+1] != AttackResponse.MISS &&
                    arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-3] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS &&
                    arena[x+2][y][z-1] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS &&
                    arena[x-2][y][z-1] != AttackResponse.MISS &&
                    arena[x][y+1][z-1] != AttackResponse.MISS &&
                    arena[x][y+2][z-1] != AttackResponse.MISS &&
                    arena[x][y-1][z-1] != AttackResponse.MISS &&
                    arena[x][y-2][z-1] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y][z+1]++;
                shipSinkProb[x][y][z-1]++;
            }

        /* The cell is at the very top.                                 */
        /* Need -4z, +2y, -2y, +2x, -2x to fit this shape.              */
        if( z > 3 && y > 1 && y < (arena[x].length - 2) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z-1] != AttackResponse.MISS &&
                    arena[x][y][z-2] != AttackResponse.MISS &&
                    arena[x][y][z-3] != AttackResponse.MISS &&
                    arena[x][y][z-4] != AttackResponse.MISS &&
                    arena[x+1][y][z-2] != AttackResponse.MISS &&
                    arena[x+2][y][z-2] != AttackResponse.MISS &&
                    arena[x-1][y][z-2] != AttackResponse.MISS &&
                    arena[x-2][y][z-2] != AttackResponse.MISS &&
                    arena[x][y+1][z-2] != AttackResponse.MISS &&
                    arena[x][y+2][z-2] != AttackResponse.MISS &&
                    arena[x][y-1][z-2] != AttackResponse.MISS &&
                    arena[x][y-2][z-2] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y][z-1]++;
            }

        /* The cell is 1 space to the left (-x) of the middle on the x-axis.    */
        /* Need -1x, +3x, -2z, +2z, -2y, +2y.                                   */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 1 && y < (arena[x].length - 2) && x > 0 && x < (arena.length - 3) )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+3][y][z] != AttackResponse.MISS &&
                    arena[x+1][y][z+1] != AttackResponse.MISS &&
                    arena[x+1][y][z+2] != AttackResponse.MISS &&
                    arena[x+1][y][z-1] != AttackResponse.MISS &&
                    arena[x+1][y][z-2] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS &&
                    arena[x+1][y+2][z] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS &&
                    arena[x+1][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x-1][y][z]++;
                shipSinkProb[x+1][y][z]++;
            }

        /* The cell is 2 spaces to the left (-x) of the middle.                 */
        /* Need +4x, -2z, +2z, -2y, +2y.                                        */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 1 && y < (arena[x].length - 2) && x < (arena.length - 4) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z] != AttackResponse.MISS &&
                    arena[x+3][y][z] != AttackResponse.MISS &&
                    arena[x+4][y][z] != AttackResponse.MISS &&
                    arena[x+2][y][z+1] != AttackResponse.MISS &&
                    arena[x+2][y][z+2] != AttackResponse.MISS &&
                    arena[x+2][y][z-1] != AttackResponse.MISS &&
                    arena[x+2][y][z-2] != AttackResponse.MISS &&
                    arena[x+2][y+1][z] != AttackResponse.MISS &&
                    arena[x+2][y+2][z] != AttackResponse.MISS &&
                    arena[x+2][y-1][z] != AttackResponse.MISS &&
                    arena[x+2][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x+1][y][z]++;
            }

        /* The cell is 1 space to the right (+x) of the middle.                 */
        /* Need -3x, +1x, -2z, +2z, -2y, +2y.                                   */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 1 && y < (arena[x].length - 2) && x > 2 && x < (arena.length - 1) )
            if(     arena[x+1][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-3][y][z] != AttackResponse.MISS &&
                    arena[x-1][y][z+1] != AttackResponse.MISS &&
                    arena[x-1][y][z+2] != AttackResponse.MISS &&
                    arena[x-1][y][z-1] != AttackResponse.MISS &&
                    arena[x-1][y][z-2] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS &&
                    arena[x-1][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x-1][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x+1][y][z]++;
                shipSinkProb[x-1][y][z]++;
            }

        /* The cell is 2 spaces to the right (+x) of the middle.                */
        /* Need -4x, -2z, +2z, -2y, +2y.                                        */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 1 && y < (arena[x].length - 2) && x > 3 )
            if(     arena[x-1][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z] != AttackResponse.MISS &&
                    arena[x-3][y][z] != AttackResponse.MISS &&
                    arena[x-4][y][z] != AttackResponse.MISS &&
                    arena[x-2][y][z+1] != AttackResponse.MISS &&
                    arena[x-2][y][z+2] != AttackResponse.MISS &&
                    arena[x-2][y][z-1] != AttackResponse.MISS &&
                    arena[x-2][y][z-2] != AttackResponse.MISS &&
                    arena[x-2][y+1][z] != AttackResponse.MISS &&
                    arena[x-2][y+2][z] != AttackResponse.MISS &&
                    arena[x-2][y-1][z] != AttackResponse.MISS &&
                    arena[x-2][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x-1][y][z]++;
            }

        /* The cell is 1 space forward (+y) of the middle.                     */
        /* Need +1y, -3y, -2z, +2z, -2x, +2x.                                  */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 2 && y < (arena[x].length - 1) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-3][z] != AttackResponse.MISS &&
                    arena[x][y-1][z+1] != AttackResponse.MISS &&
                    arena[x][y-1][z+2] != AttackResponse.MISS &&
                    arena[x][y-1][z-1] != AttackResponse.MISS &&
                    arena[x][y-1][z-2] != AttackResponse.MISS &&
                    arena[x+1][y-1][z] != AttackResponse.MISS &&
                    arena[x+2][y-1][z] != AttackResponse.MISS &&
                    arena[x-1][y-1][z] != AttackResponse.MISS &&
                    arena[x-2][y-1][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y+1][z]++;
                shipSinkProb[x][y-1][z]++;
            }

        /* The cell is 2 spaces forward (+y) of the middle.                     */
        /* Need -4y, -2z, +2z, -2x, +2x.                                        */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 3 && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y-2][z] != AttackResponse.MISS &&
                    arena[x][y-3][z] != AttackResponse.MISS &&
                    arena[x][y-4][z] != AttackResponse.MISS &&
                    arena[x][y-2][z+1] != AttackResponse.MISS &&
                    arena[x][y-2][z+2] != AttackResponse.MISS &&
                    arena[x][y-2][z-1] != AttackResponse.MISS &&
                    arena[x][y-2][z-2] != AttackResponse.MISS &&
                    arena[x+1][y-2][z] != AttackResponse.MISS &&
                    arena[x+2][y-2][z] != AttackResponse.MISS &&
                    arena[x-1][y-2][z] != AttackResponse.MISS &&
                    arena[x-2][y-2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y-1][z]++;
            }

        /* The cell is 1 space back (-y) of the middle.                         */
        /* Need -1y, +3y, -2z, +2z, -2x, +2x.                                   */
        if( z > 1 && z < (arena[x][y].length - 2) && y > 0 && y < (arena[x].length - 3) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y-1][z] != AttackResponse.MISS &&
                    arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+3][z] != AttackResponse.MISS &&
                    arena[x][y+1][z+1] != AttackResponse.MISS &&
                    arena[x][y+1][z+2] != AttackResponse.MISS &&
                    arena[x][y+1][z-1] != AttackResponse.MISS &&
                    arena[x][y+1][z-2] != AttackResponse.MISS &&
                    arena[x+1][y+1][z] != AttackResponse.MISS &&
                    arena[x+2][y+1][z] != AttackResponse.MISS &&
                    arena[x-1][y+1][z] != AttackResponse.MISS &&
                    arena[x-2][y+1][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y-1][z]++;
                shipSinkProb[x][y+1][z]++;
            }

        /* The cell is 2 spaces back (-y) of the middle.                        */
        /* Need +4y, -2z, +2z, -2x, +2x.                                        */
        if( z > 1 && z < (arena[x][y].length - 2) && y < (arena[x].length - 4) && x > 1 && x < (arena.length - 2) )
            if(     arena[x][y+1][z] != AttackResponse.MISS &&
                    arena[x][y+2][z] != AttackResponse.MISS &&
                    arena[x][y+3][z] != AttackResponse.MISS &&
                    arena[x][y+4][z] != AttackResponse.MISS &&
                    arena[x][y+2][z+1] != AttackResponse.MISS &&
                    arena[x][y+2][z+2] != AttackResponse.MISS &&
                    arena[x][y+2][z-1] != AttackResponse.MISS &&
                    arena[x][y+2][z-2] != AttackResponse.MISS &&
                    arena[x+1][y+2][z] != AttackResponse.MISS &&
                    arena[x+2][y+2][z] != AttackResponse.MISS &&
                    arena[x-1][y+2][z] != AttackResponse.MISS &&
                    arena[x-2][y+2][z] != AttackResponse.MISS ) {
                searchProbability++;
                shipSinkProb[x][y+1][z]++;
            }

        return searchProbability;
    }
}
