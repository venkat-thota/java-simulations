
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.client;

import java.io.IOException;


public class ObjectActor<T, U> extends ObjectStreamActor<T, U> {

    public ObjectActor() throws ClassNotFoundException, IOException {
        super();
    }

    public ObjectActor( String host, int port ) throws IOException, ClassNotFoundException {
        super( host, port );
    }
    public synchronized U ask( T question ) throws IOException, ClassNotFoundException {
        writeToServer.writeObject( question );
        writeToServer.flush();

        //Prevent multiple threads from using the read object simultaneously.  This was a problem before we created a new Client for that thread in SimView
        synchronized ( readFromServer ) {
            Object result = readFromServer.readObject();
            return (U) result;
        }
    }

      public synchronized void tell( T statement ) throws IOException {
        writeToServer.writeObject( statement );
        writeToServer.flush();
    }
}