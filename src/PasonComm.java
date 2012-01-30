/*
 * Author: Thomas Hewton-Waters
 * Created on: January 29, 2012
 * Last modified: January 29, 2012
 */

import java.net.Socket;
import java.io.*;

public class PasonComm {
	private Socket pSocket;
	private BufferedReader pReader;
	private PrintWriter pWriter;
	
	public boolean connect() throws IOException
	{
		/* Connect to the game server */
		this.pSocket = new Socket( Const.kServerUrl, Const.kServerPort );
		
		if( this.pSocket.isConnected() ) {
			/* If connected, create the reader and writer */
			pReader = new BufferedReader( new InputStreamReader( pSocket.getInputStream() ) );
			pWriter = new PrintWriter( pSocket.getOutputStream() );
			
			return true;
		}
		
		/* Connection failed */
		return false;
	}
	
	public boolean isConnected()
	{
		return pSocket.isConnected();
	}
	
	public void login()
	{
		this.writeLine( Const.kLoginRequest );
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
	
	public static void main( String[] args )
	{
		/* Connect to the game server */
		PasonComm pComm = new PasonComm();
		try {
			pComm.connect();
		} catch( IOException e ) {
			System.err.println( e );
		}
		
		/* Print the connection banner */
		try {
			System.out.println( pComm.readLine() );
		} catch( IOException e ) {
			System.err.println( e );
		}
		
		/* Send login request */
		pComm.login();
		
		/* Print the login response */
		try {
			System.out.println( pComm.readLine() );
		} catch( IOException e ) {
			System.err.println( e );
		}
	}
}
