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
        bShip.pComm.fire( bShip.attack.generateAttack() );

        try {
            Log.WriteLog( bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }
    }
}
