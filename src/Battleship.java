/*
 * Author: Thomas Hewton-Waters
 * Created on: January 30, 2012
 */

import java.io.IOException;

public class Battleship {
    public PasonComm pComm;

    public Battleship()
    {
        this.pComm = new PasonComm();
        Log.WriteLog( "Connecting to Pason server." );
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

        /* Print the connection banner */
        try {
            System.out.println( bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }

        /* Login to the game */
        bShip.pComm.login();

        /* Print the login response */
        try {
            System.out.println( bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }

        /* Send ship layout */
        bShip.pComm.sendShipLayout();

        /* Print ship layout response */
        try {
            System.out.println( bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }

        /* Fire on the enemy */
        bShip.pComm.fire(0,0,0);

        try {
            System.out.println( bShip.pComm.readLine() );
        } catch( IOException e ) {
            System.err.println( e );
        }
    }
}
