/*
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.io.IOException;

public class Battleship {
    public PasonComm pComm;
    public Attack attack;

    public Battleship()
    {
        Log.WriteLog( "Initializing PasonComm object." );
        this.pComm = new PasonComm();

        Log.WriteLog( "Initializing Attack object." );
        this.attack = new Attack();
    }

    public static void main( String[] args )
    {
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
            Log.WriteLog( "Login response: " + bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }

        /* Send ship layout */
        bShip.pComm.sendShipLayout();

        /* Print ship layout response */
        try {
            Log.WriteLog( "Ship layout response: " + bShip.pComm.readLine());
        } catch( IOException e ) {
            System.err.println( e );
        }

        /* Fire on the enemy */
        String attackResponse = new String();

        while( !attackResponse.contains( Const.kGameOverLose ) && !attackResponse.contains( Const.kGameOverWin ) ) {

            Coordinate attackCoordinate = bShip.attack.generateAttack();
            bShip.pComm.fire( attackCoordinate );

            /* Process the response (HIT/MISS) */
            try {
                attackResponse = bShip.pComm.readLine();
                Log.WriteLog( "Attack response: " + attackResponse );
                bShip.attack.processAttackResponse(attackResponse, attackCoordinate);
            } catch( IOException e ) {
                System.err.println( e );
            }
        }
    }
}
