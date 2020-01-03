
package com.aimxcel.abclearn.common.abclearncommon.simsharing.logs;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.Log;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingMessage;

/**
 * @author Sam Reid
 */
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
