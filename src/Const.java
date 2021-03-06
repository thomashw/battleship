/*
 * Author: Thomas Hewton-Waters
 * Created on: January 29, 2012
 */

public class Const {
	/* Server URL */
	public static final String kServerUrl = "pason2.enel.ucalgary.ca";
	
	/* Server Port */
	public static final int kServerPort = 6130;
	
	/* Login team name */
	public static final String kTeamName = "Thomas Hewton-Waters";
	
	/* Login game password */
	public static final String kGamePassword = "b6urwdy0;";
	
	/* End all communications with a line separator */
	public static final String kLineSeparator = "\r\n";
	
	/* Login string for login request */
	public static final String kLoginRequest = kTeamName + "|" + kGamePassword + kLineSeparator;
    
    /* Ship names */
    public static final String kShipNameFF = "FF";
    public static final String kShipNameSSK = "SSK";
    public static final String kShipNameDDH = "DDH";
    public static final String kShipNameBB = "BB";
    public static final String kShipNameCVL = "CVL";
	
	/* Ship layout */
	public static final String kLayoutFF = "9,0,0;9,0,1;9,0,2";
	public static final String kLayoutSSK = "9,0,3;9,0,4;9,0,5;9,0,6;9,0,7";
	public static final String kLayoutDDH = "0,1,0;1,0,0;1,1,0;1,2,0;2,1,0";
	public static final String kLayoutBB = "1,1,1;1,1,2;1,1,3;1,2,1;1,2,3;1,3,1;1,3,2;1,3,3";
	public static final String kLayoutCVL = "0,2,2;1,2,2;2,2,2;3,2,2;4,2,2;2,0,2;2,1,2;2,3,2;2,4,2;2,2,0;2,2,1;2,2,3;2,2,4";
	public static final String kShipLayout =	"FF:" + kLayoutFF + "|" + 
												"SSK:" + kLayoutSSK + "|" + 
												"DDH:" + kLayoutDDH + "|" + 
												"BB:" + kLayoutBB + "|" + 
												"CVL:" + kLayoutCVL + kLineSeparator;
    public static final int kNumShipLayouts = 12;
    
    /* Number of coordinates for each ship */
    public static final int kNumCoordinatesFF = 3;
    public static final int kNumCoordinatesSSK = 5;
    public static final int kNumCoordinatesDDH = 5;
    public static final int kNumCoordinatesBB = 8;
    public static final int kNumCoordinatesCVL = 13;

    /* Game dimensions */
    public static final int kGameDimensionX = 10;
    public static final int kGameDimensionY = 10;
    public static final int kGameDimensionZ = 10;
    public static final int kMaxCoord = 9;
    public static final int kMinCoord = 0;

    /* Attack responses */
    public static final String kAttackResponseStrHit = "HIT";
    public static final String kAttackResponseStrMiss = "MISS";

    /* Match/game responses */
    public static final String kMatch = "MATCH";
    public static final String kGame = "GAME";
    public static final String kLose = "LOSE";
    public static final String kWin = "WIN";
    public static final String kFail = "FAIL";

    /* Edge coordinates to check while searching. The algorithm initially ignores edges */
    /* so I want to intentionally check some edge points. There's 24 in all.            */
    public static Coordinate[] kEdgeSearchCoordinates = { new Coordinate(2,0,0),
                                            new Coordinate(5,0,0),
                                            new Coordinate(7,0,0),
                                            new Coordinate(0,2,0),
                                            new Coordinate(0,5,0),
                                            new Coordinate(0,7,0),
                                            new Coordinate(0,0,2),
                                            new Coordinate(0,0,5),
                                            new Coordinate(0,0,7),
                                            new Coordinate(0,2,9),
                                            new Coordinate(0,5,9),
                                            new Coordinate(0,7,9),
                                            new Coordinate(9,2,0),
                                            new Coordinate(9,5,0),
                                            new Coordinate(9,7,0),
                                            new Coordinate(9,0,2),
                                            new Coordinate(9,0,5),
                                            new Coordinate(9,0,7),
                                            new Coordinate(9,2,9),
                                            new Coordinate(9,5,9),
                                            new Coordinate(9,7,9),
                                            new Coordinate(9,9,2),
                                            new Coordinate(9,9,5),
                                            new Coordinate(9,9,7) };
}
