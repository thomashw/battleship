/*
 * Author: Thomas Hewton-Waters
 * Created on: January 29, 2012
 */

public class Const {
	/* Server URL */
	public static final String kServerUrl = "pason1.enel.ucalgary.ca";
	
	/* Server Port */
	public static final int kServerPort = 6130;
	
	/* Login team name */
	public static final String kTeamName = "Thomas Hewton_Waters";
	
	/* Login game password */
	public static final String kGamePassword = "TEST_GAME";
	
	/* End all communications with a line separator */
	public static final String kLineSeparator = "\r\n";
	
	/* Login string for login request */
	public static final String kLoginRequest = kTeamName + "|" + kGamePassword + kLineSeparator;
	
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
    
    public static final int kGameDimensionX = 10;
    public static final int kGameDimensionY = 10;
    public static final int kGameDimensionZ = 10;
    public static final int kMaxCoord = 9;
    public static final int kMinCoord = 0;
    
    public static final String kAttackResponseStrHit = "HIT";
    public static final String kAttackResponseStrMiss = "MISS";
    
    public static final String kGameOverLose = "LOSE";
    public static final String kGameOverWin = "WIN";
}
