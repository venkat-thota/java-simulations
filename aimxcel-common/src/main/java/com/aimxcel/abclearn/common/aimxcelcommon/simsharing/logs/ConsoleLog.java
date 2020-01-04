
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.logs;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.Log;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;


public class ConsoleLog implements Log {
    public void addMessage( SimSharingMessage message ) {
        System.out.println( message );
    }

    public String getName() {
        return "Java Console";
    }

    public void shutdown() {
    }
}
