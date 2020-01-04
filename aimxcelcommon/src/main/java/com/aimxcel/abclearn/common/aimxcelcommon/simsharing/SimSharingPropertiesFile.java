
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing;

import java.io.File;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelPersistenceDir;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.util.AbstractPropertiesFile;

/**
 * Properties file for sim-sharing ($HOME/.phet/sim-sharing.properties).
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SimSharingPropertiesFile extends AbstractPropertiesFile {

    private static final String KEY_MACHINE_COOKIE = "machine.cookie";

    public SimSharingPropertiesFile() {
        super( new File( new AimxcelPersistenceDir(), "sim-sharing.properties" ) );
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
