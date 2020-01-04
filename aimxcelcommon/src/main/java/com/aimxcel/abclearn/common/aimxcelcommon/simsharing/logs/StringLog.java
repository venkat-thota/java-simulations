
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.logs;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.Log;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;

/**
 * @author Sam Reid
 */
public class StringLog implements Log {
    public final Property<String> log = new Property<String>( "" ); // log events locally, as a fallback plan //TODO StringBuffer would be more efficient

    // Sends a message to the sim-sharing log.
    public void addMessage( SimSharingMessage message ) {
        if ( log.get().length() != 0 ) {
            log.set( log.get() + "\n" );
        }
        log.set( log.get() + message );
    }

    public String getName() {
        return "Internal buffer";
    }

    public void shutdown() {
    }
}