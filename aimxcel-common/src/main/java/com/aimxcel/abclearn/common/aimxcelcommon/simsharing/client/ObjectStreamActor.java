
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.logs.MongoLog;


public abstract class ObjectStreamActor<T, U> implements IActor<T, U> {

    private static final Logger LOGGER = Logger.getLogger( ObjectStreamActor.class.getCanonicalName() );

    private final Socket socket;
    public final ObjectOutputStream writeToServer;
    public final ObjectInputStream readFromServer;

    public ObjectStreamActor() throws ClassNotFoundException, IOException {
        this( MongoLog.HOST_IP_ADDRESS, MongoLog.PORT );
    }

    public ObjectStreamActor( String host, int port ) throws IOException, ClassNotFoundException {
        socket = new Socket( host, port );

        //Create streams for communicating with the server
        writeToServer = new ObjectOutputStream( socket.getOutputStream() );
        readFromServer = new ObjectInputStream( socket.getInputStream() );

        //Read the initial message from the server to verify communication is working properly
        Object fromServer = readFromServer.readUTF();
        LOGGER.info( "MessageServer: " + fromServer );
    }
}