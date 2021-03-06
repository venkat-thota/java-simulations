
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.io.File;
import java.security.AccessControlException;

public class AimxcelPersistenceDir extends File {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @throws AccessControlException if called without permissions to access the user.home system property
     */
    public AimxcelPersistenceDir() throws AccessControlException {
        super( System.getProperty( "user.home" ) + System.getProperty( "file.separator" ) + ".aimxcel" );
    }
}
