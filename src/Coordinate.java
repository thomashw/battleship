/**
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

public class Coordinate {
    private int x;
    private int y;
    private int z;
    
    public Coordinate( int x, int y, int z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate()
    {
        this( 0,0,0 );
    }
    
    public void setX( int x )
    {
        this.x = x;
    }
    
    public void setY( int y )
    {
        this.y = y;
    }
    
    public void setZ( int z )
    {
        this.z = z;
    }
    
    public int getX()
    {
        return this.x;                
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public int getZ()
    {
        return this.z;
    }
        
}                       
