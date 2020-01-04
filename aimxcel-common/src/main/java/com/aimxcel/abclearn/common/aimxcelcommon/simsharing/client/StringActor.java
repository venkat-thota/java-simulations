
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.client;

import java.io.IOException;
import java.util.logging.Logger;


public class StringActor extends ObjectStreamActor<String, String> {

    private static final Logger LOGGER = Logger.getLogger( StringActor.class.getCanonicalName() );

    public StringActor() throws ClassNotFoundException, IOException {
        super();
    }

    public StringActor( String host, int port ) throws IOException, ClassNotFoundException {
        super( host, port );
    }

      public synchronized String ask( String question ) throws IOException, ClassNotFoundException {
        checkSize( question );
        writeToServer.writeUTF( question );
        writeToServer.flush();

        //Prevent multiple threads from using the read object simultaneously.  This was a problem before we created a new Client for that thread in SimView
        synchronized ( readFromServer ) {
            String result = readFromServer.readUTF();
            return result;
        }
    }
    public synchronized void tell( String statement ) throws IOException {
        checkSize( statement );
        writeToServer.writeUTF( statement );
        writeToServer.flush();
    }

     public static void checkSize( String question ) {
        if ( question.length() > 20000 ) {
            LOGGER.warning( "String probably too long to send over writeUTF, length = " + question.length() );
        }
    }
}