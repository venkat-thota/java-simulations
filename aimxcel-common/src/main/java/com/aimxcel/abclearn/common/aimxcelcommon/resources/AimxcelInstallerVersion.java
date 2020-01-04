
package com.aimxcel.abclearn.common.aimxcelcommon.resources;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AimxcelInstallerVersion {

    private static SimpleDateFormat FORMAT_TIMESTAMP = new SimpleDateFormat( "MMM d, yyyy" );

    private final long timestamp; // seconds since Epoch

    public AimxcelInstallerVersion( long timestamp ) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String formatTimestamp() {
        Date date = new Date( timestamp * 1000L ); // seconds to milliseconds 
        return FORMAT_TIMESTAMP.format( date );
    }
}
