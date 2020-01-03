
package com.aimxcel.abclearn.common.abclearncommon.resources;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The PhET Installer version is identified by a timestamp (in seconds since Epoch)
 * when the installer was created.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AbcLearnInstallerVersion {

    private static SimpleDateFormat FORMAT_TIMESTAMP = new SimpleDateFormat( "MMM d, yyyy" );

    private final long timestamp; // seconds since Epoch

    public AbcLearnInstallerVersion( long timestamp ) {
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
