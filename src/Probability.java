/**
 * Author: Thomas Hewton-Waters
 * Created on: January 31, 2012
 */

public class Probability {
    public static int probabilityBB( int x, int y, int z, AttackResponse[][][] arena )
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
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the top-middle cell in the x-y plane.                    */
        /* Going clockwise: 1 cell in +y dir, 2 cells in -x dir, 2 cells in     */
        /* -y dir, 2 cells in +x dir.                                           */
        if( y < (arena[x].length - 1) && y > 0 && x > 1 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the top-right cell in the x-y plane.                     */
        /* Going clockwise: 2 cells in -x dir, 2 cells in -y dir, 2 cells in    */
        /* +x dir, 2 cells in +y dir.                                           */
        if( y > 1 && x > 1 )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the right-middle cell in the x-y plane.                  */
        /* Going clockwise: 1 cell in -x dir, 2 cells in -y dir, 2 cells in     */
        /* +x dir, 2 cells in +y dir.                                           */
        if( y > 1 && x > 0 && x < (arena.length - 1) )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the right-bottom cell in the x-y plane.                  */
        /* Going clockwise: 2 cells in -y dir, 2 cells in +x dir, 2 cells in    */
        /* +y dir, 1 cell in -x dir.                                            */
        if( y > 1 && x < (arena.length - 2) )
            if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the bottom-middle cell in the x-y plane.                 */
        /* Clockwise: 1 cell in -y, 2 in +x, 2 in +y, 2 in -x, 1 in -y.         */
        if( y > 0 && y < (arena[x].length - 1) &&  x < (arena.length - 2) )
            if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the left-bottom cell in the x-y plane.                   */
        /* Clockwise: 2 cells +x, 2 cells +y, 2 cells -x, 1 cell -y.            */
        if( y < (arena[x].length - 2) && x < (arena.length - 2) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the left-middle cell in the x-y plane.                   */
        /* Clockwise: 1 cell +x, 2 cells +y, 2 cells -x, 2 cells -y.            */
        if( y < (arena[x].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /*************/
        /* x-z plane */
        /*************/

        /* The cell is the top-left cell in the x-z plane.                      */
        /* Going clockwise: 2 cells in +z direction, 2 cells in -x direction,   */
        /* 2 cells in -z direction, 2 cells in +x direction.                    */
        if( z < (arena[x][y].length - 2) && x > 1 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the top-middle cell in the x-z plane.                    */
        /* Going clockwise: 1 cell in +z dir, 2 cells in -x dir, 2 cells in     */
        /* -z dir, 2 cells in +x dir.                                           */
        if( z < (arena[x][y].length - 1) && z > 0 && x > 1 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the top-right cell in the x-z plane.                     */
        /* Going clockwise: 2 cells in -x dir, 2 cells in -z dir, 2 cells in    */
        /* +x dir, 2 cells in +z dir.                                           */
        if( z > 1 && x > 1 )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the right-middle cell in the x-z plane.                  */
        /* Going clockwise: 1 cell in -x dir, 2 cells in -z dir, 2 cells in     */
        /* +x dir, 2 cells in +z dir.                                           */
        if( z > 1 && x > 0 && x < (arena.length - 1) )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the right-bottom cell in the x-z plane.                  */
        /* Going clockwise: 2 cells in -z dir, 2 cells in +x dir, 2 cells in    */
        /* +z dir, 1 cell in -x dir.                                            */
        if( z > 1 && x < (arena.length - 2) )
            if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the bottom-middle cell in the x-z plane.                 */
        /* Clockwise: 1 cell in -z, 2 in +x, 2 in +z, 2 in -x.         */
        if( z > 0 && z < (arena[x][y].length - 1) &&  x < (arena.length - 2) )
            if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the left-bottom cell in the x-z plane.                   */
        /* Clockwise: 2 cells +x, 2 cells +z, 2 cells -x, 1 cell -z.            */
        if( z < (arena[x][y].length - 2) && x < (arena.length - 2) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is the left-middle cell in the x-z plane.                   */
        /* Clockwise: 1 cell +x, 2 cells +z, 2 cells -x, 2 cells -z.            */
        if( z < (arena[x][y].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
    }

    public static int probabilityDDH( int x, int y, int z, AttackResponse[][][] arena )
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
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a bottom cell in the x-y plane. It has 2 cells in the    */
        /* +x direction forming 3 straight cells. There's two cells in the      */
        /* y-plane beside its middle cell forming the +.                        */
        if( x < (arena.length - 2) && y > 0 && y < (arena[x].length - 1) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a left cell in the x-y plane. There's 2 cells in the     */
        /* +y direction forming 3 straight cells. There's two cells in the      */
        /* x-plane beside the middle cell forming the +.                        */
        if( y < (arena[x].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y+1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a right cell in the x-y plane. There's 2 cells in the    */
        /* -y direction forming 3 straight ells. There's two cells in the       */
        /* x-plane beside the middle cell forming the +.                        */
        if( y > 1 && x > 0 && x < (arena.length-1) )
            if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a middle cell in the x-y plane. There's 1 cell in the +y */
        /* direction, 1 cell in the -y direction, 1 cell in the +x direction, & */
        /* 1 cell in the -x direction.                                          */
        if( y < (arena[x].length - 1) && y > 0 && x < (arena.length - 1) && x > 0 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /*************/
        /* x-z plane */
        /*************/

        /* The cell is a top cell in the x-z plane. It has 2 cells in the       */
        /* -x direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( x > 1 && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a bottom cell in the x-z plane. It has 2 cells in the    */
        /* +x direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( x < (arena.length - 2) && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a left cell in the x-z plane. There's 2 cells in the     */
        /* +z direction forming 3 straight cells. There's two cells in the      */
        /* x-plane beside the middle cell forming the +.                        */
        if( z < (arena[x][y].length - 2) && x > 0 && x < (arena.length - 1) )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a right cell in the x-z plane. There's 2 cells in the    */
        /* -z direction forming 3 straight ells. There's two cells in the       */
        /* x-plane beside the middle cell forming the +.                        */
        if( z > 1 && x > 0 && x < (arena.length-1) )
            if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a middle cell in the x-z plane. There's 1 cell in the +z */
        /* direction, 1 cell in the -z direction, 1 cell in the +x direction, & */
        /* 1 cell in the -x direction.                                          */
        if( z < (arena[x][y].length - 1) && z > 0 && x < (arena.length - 1) && x > 0 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /*************/
        /* y-z plane */
        /*************/

        /* The cell is a top cell in the y-z plane. It has 2 cells in the       */
        /* -y direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( y > 1 && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a bottom cell in the y-z plane. It has 2 cells in the    */
        /* +y direction forming 3 straight cells. There's two cells in the      */
        /* z-plane beside its middle cell forming the +.                        */
        if( y < (arena[x].length - 2) && z > 0 && z < (arena[x][y].length - 1) )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a left cell in the y-z plane. There's 2 cells in the     */
        /* +z direction forming 3 straight cells. There's two cells in the      */
        /* y-plane beside the middle cell forming the +.                        */
        if( z < (arena[x][y].length - 2) && y > 0 && y < (arena[x].length - 1) )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z+1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a right cell in the y-z plane. There's 2 cells in the    */
        /* -z direction forming 3 straight ells. There's two cells in the       */
        /* y-plane beside the middle cell forming the +.                        */
        if( z > 1 && y > 0 && y < (arena[x].length-1) )
            if(     arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /* The cell is a middle cell in the y-z plane. There's 1 cell in the +z */
        /* direction, 1 cell in the -z direction, 1 cell in the +y direction, & */
        /* 1 cell in the -y direction.                                          */
        if( z < (arena[x][y].length - 1) && z > 0 && y < (arena[x].length - 1) && y > 0 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
    }

    public static int probabilitySSK( int x, int y, int z, AttackResponse[][][] arena )
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
        if( y < arena[x].length - 4 )
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
        if( z < arena[x][y].length - 4 )
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
        if( x < (arena.length - 1) && x > 2 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-3][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +x & 1 cell in -x directions */
        if( x < (arena.length - 3) && x > 0 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+3][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell in +y & 3 cells in -y directions */
        if( y < (arena[x].length - 1) && y > 2 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-3][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +y & 1 cell in -y directions */
        if( y < (arena[x].length - 3) && y > 0 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+3][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell in +z & 3 cells in -z directions */
        if( z < (arena[x][y].length - 1) && z > 2 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-3] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 3 cells in +z & 1 cell in -z directions */
        if( z < (arena[x][y].length - 3) && z > 0 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+3] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        /***************************/
        /* Cell is in the "middle" */
        /***************************/

        /* Check if 2 cells in the +x & 2 cells in the -x directions */
        if( x < (arena.length - 2) && x > 1 )
            if(     arena[x+1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x+2][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-1][y][z] == AttackResponse.UNKNOWN &&
                    arena[x-2][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +y & 2 cells in the -y directions */
        if( y < (arena.length - 2) && y > 1 )
            if(     arena[x][y+1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y+2][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-1][z] == AttackResponse.UNKNOWN &&
                    arena[x][y-2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +z & 2 cells in the -z directions */
        if( z < (arena.length - 2) && z > 1 )
            if(     arena[x][y][z+1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z+2] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-1] == AttackResponse.UNKNOWN &&
                    arena[x][y][z-2] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
    }

    public static int probabilityFF( int x, int y, int z, AttackResponse[][][] arena )
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
        if( x < (arena.length - 2) )
            if( arena[x+1][y][z] == AttackResponse.UNKNOWN && arena[x+2][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the -x direction */
        if( x > 1 )
            if( arena[x-1][y][z] == AttackResponse.UNKNOWN && arena[x-2][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +y direction */
        if( y < (arena[x].length - 2) )
            if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y+2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the -y direction */
        if( y > 1 )
            if( arena[x][y-1][z] == AttackResponse.UNKNOWN && arena[x][y-2][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the +z direction */
        if( z < (arena[x][y].length - 2) )
            if( arena[x][y][z+1] == AttackResponse.UNKNOWN && arena[x][y][z+2] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 2 cells in the -z direction */
        if( z > 1 )
            if( arena[x][y][z-1] == AttackResponse.UNKNOWN && arena[x][y][z-2] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell +x & 1 cell -x */
        if( x < arena.length - 1 && x > 0 )
            if( arena[x+1][y][z] == AttackResponse.UNKNOWN && arena[x-1][y][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell +y & 1 cell -y */
        if( y < arena[x].length - 1 && y > 0 )
            if( arena[x][y+1][z] == AttackResponse.UNKNOWN && arena[x][y-1][z] == AttackResponse.UNKNOWN )
                probability++;

        /* Check if 1 cell +z & 1 cell -z */
        if( z < arena[x][y].length - 1 && z > 0 )
            if( arena[x][y][z+1] == AttackResponse.UNKNOWN && arena[x][y][z-1] == AttackResponse.UNKNOWN )
                probability++;

        return probability;
    }
}
