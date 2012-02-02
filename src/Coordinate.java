/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

public class Coordinate {
    public int x;
    public int y;
    public int z;
    
    public Coordinate( int x, int y, int z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Coordinate( Coordinate c )
    {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }

    public Coordinate()
    {
        this( 0,0,0 );
    }
}                       
