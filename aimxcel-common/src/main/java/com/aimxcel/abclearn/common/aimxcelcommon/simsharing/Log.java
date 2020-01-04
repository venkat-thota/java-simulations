
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing;

import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;

public interface Log {
    public void addMessage( SimSharingMessage message ) throws IOException;

    //Name of the log which is shown in the list of logs.
    String getName();

    //When the sim is closing, perform any final events such as sending messages to the server
    void shutdown();
}