/*
 * Author: Thomas Hewton-Waters
 * Created on: January 29, 2012
 */

import java.io.*;

public class Log {
    public static void WriteLog( String s )
    {
        try {
            File file = new File( "log.txt" );

            if( !file.exists() ) {
                file.createNewFile();
            }

            FileWriter fstream = new FileWriter( "log.txt", true );
            BufferedWriter out = new BufferedWriter( fstream );
            
            String logText = "\n" + s;
            out.write( logText );
            out.close();
        } catch ( IOException e ) {
            System.err.println( e );
        }
    }
}
