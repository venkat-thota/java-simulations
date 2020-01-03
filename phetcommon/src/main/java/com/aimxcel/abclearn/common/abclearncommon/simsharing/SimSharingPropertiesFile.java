
package com.aimxcel.abclearn.common.abclearncommon.simsharing;

import java.io.File;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnPersistenceDir;
import com.aimxcel.abclearn.common.abclearncommon.util.AbstractPropertiesFile;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;

/**
 * Properties file for sim-sharing ($HOME/.phet/sim-sharing.properties).
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SimSharingPropertiesFile extends AbstractPropertiesFile {

    private static final String KEY_MACHINE_COOKIE = "machine.cookie";

    public SimSharingPropertiesFile() {
        super( new File( new AbcLearnPersistenceDir(), "sim-sharing.properties" ) );
    }

    public String getMachineCookie() {
        String machineCookie = getProperty( KEY_MACHINE_COOKIE );
        if ( machineCookie == null ) {
            machineCookie = SimSharingManager.generateMachineCookie();
            setMachineCookie( machineCookie );
        }
        return machineCookie;
    }

    public void setMachineCookie( String machineCookie ) {
        setProperty( KEY_MACHINE_COOKIE, machineCookie );
    }
}
