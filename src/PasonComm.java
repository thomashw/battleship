/*
 * Author: Thomas Hewton-Waters
 * Created on: January 29, 2012
 */

import java.net.Socket;
import java.io.*;

public class PasonComm {
	private Socket pSocket;
	private BufferedReader pReader;
	private PrintWriter pWriter;
	
	public boolean connect() throws IOException
	{
        Log.WriteLog( "Connecting to Pason server." );

		/* Connect to the game server */
		this.pSocket = new Socket( Const.kServerUrl, Const.kServerPort );
		
		if( this.pSocket.isConnected() ) {
            Log.WriteLog( "Connected to Pason server." );

			/* If connected, create the reader and writer */
			pReader = new BufferedReader( new InputStreamReader( pSocket.getInputStream() ) );
			pWriter = new PrintWriter( pSocket.getOutputStream() );
			
			return true;
		}

		/* Connection failed */
        Log.WriteLog( "Pason server connection failed." );
		return false;
	}
	
	public boolean isConnected()
	{
		return pSocket.isConnected();
	}
	
	public void login()
	{
        Log.WriteLog( "Logging in to the game." );
		this.writeLine( Const.kLoginRequest );
	}
	
	public void sendShipLayout()
	{
        Log.WriteLog( "Sending ship layout." );
		this.writeLine( Const.kShipLayout );
	}
	
	public void fire( Coordinate c )
	{
		/* Create request string */
		String fireRequest = 	Integer.toString( c.getX() ) + "," + 
								Integer.toString( c.getY() ) + "," + 
								Integer.toString( c.getZ() );
		
		/* Send the fire request */
		this.writeLine( fireRequest + Const.kLineSeparator );
        Log.WriteLog( "Firing on: " + fireRequest );
	}
	
	public boolean close() throws IOException
	{
		/* Close the socket, reader, and writer */
		this.pSocket.close();
		this.pReader.close();
		this.pWriter.close();
		
		return this.pSocket.isClosed();
	}
	
	public String readLine() throws IOException
	{
		return this.pReader.readLine();
	}
	
	public void writeLine( String s )
	{
		pWriter.write( s );
		pWriter.flush();
	}
}
