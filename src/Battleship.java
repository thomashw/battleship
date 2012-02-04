/*
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.io.IOException;

public class Battleship {
    public PasonComm pComm;
    public Attack attack;
    public MyShips myShips;

    public Battleship()
    {
        Log.WriteLog( "Initializing PasonComm object." );
        this.pComm = new PasonComm();

        Log.WriteLog( "Initializing Attack object." );
        this.attack = new Attack();

        Log.WriteLog( "Initializing MyShips object." );
        myShips = new MyShips();
    }

    public static void main( String[] args )
    {
        String serverResponse = new String();

        Log.WriteLog( "\nStarting new match." );
        while( !Outcome.isMatchOver( serverResponse ) ) {

            /* Clear the string so we don't continue exiting the inner while-loop */
            serverResponse = null;
            serverResponse = new String();

            Log.WriteLog( "\nStarting new game." );
            Battleship bShip = new Battleship();

            /* Connect to the game server */
            try {
                bShip.pComm.connect();
            } catch ( IOException e ) {
                System.err.println( e );
            }

            /* Log the connection banner */
            try {
                Log.WriteLog( "Connection banner: " + bShip.pComm.readLine() );
            } catch( IOException e ) {
                System.err.println( e );
            }

            /* Login to the game */
            bShip.pComm.login();

            /* Print the login response */
            try {
                serverResponse = bShip.pComm.readLine();
                Log.WriteLog( "Login response: " + serverResponse );
            } catch( IOException e ) {
                System.err.println( e );
            }

            if( Outcome.didFail( serverResponse ) )
                return;

            /* Send ship layout */
            bShip.pComm.sendShipLayout( bShip.myShips.generateLayout() );

            /* Print ship layout response */
            try {
                serverResponse = bShip.pComm.readLine();
                Log.WriteLog( "Ship layout response: " + serverResponse );
            } catch( IOException e ) {
                System.err.println( e );
            }

            /* Begin the game & wait until it's over */
            while( !Outcome.isGameOver( serverResponse ) && !Outcome.isMatchOver( serverResponse ) ) {

                Coordinate attackCoordinate = bShip.attack.generateAttack();
                bShip.pComm.fire( attackCoordinate );

                /* Increment the current turn */
                bShip.attack.incrementTurn();

                /* Process the response (HIT/MISS) */
                try {
                    serverResponse = bShip.pComm.readLine();
                    Log.WriteLog( "Turn " + bShip.attack.getCurrentTurn() + " attack response: " + serverResponse );
                    bShip.attack.processAttackResponse(serverResponse, attackCoordinate);
                } catch( IOException e ) {
                    System.err.println( e );
                }
            }
        }
    }
}
