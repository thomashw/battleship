/**
 * Created by: Thomas Hewton-Waters
 * Date: February 1, 2012
 */

public class Outcome {
    public static boolean isGameOver( String response )
    {
        if( response.contains( Const.kGame ) && ( response.contains( Const.kLose ) || response.contains( Const.kWin ) ) )
            return true;

        return false;
    }

    public static boolean isMatchOver( String response )
    {
        if( response.contains( Const.kMatch ) && ( response.contains( Const.kLose ) || response.contains( Const.kWin ) ) )
            return true;

        return false;
    }
    
    public static boolean didFail( String response )
    {
        if( response.contains( Const.kFail ) )
            return true;

        return false;
    }
}
