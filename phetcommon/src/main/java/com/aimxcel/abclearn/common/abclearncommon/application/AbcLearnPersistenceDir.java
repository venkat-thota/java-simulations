
package com.aimxcel.abclearn.common.abclearncommon.application;

import java.io.File;
import java.security.AccessControlException;

public class AbcLearnPersistenceDir extends File {

    /**
     * @throws AccessControlException if called without permissions to access the user.home system property
     */
    public AbcLearnPersistenceDir() throws AccessControlException {
        super( System.getProperty( "user.home" ) + System.getProperty( "file.separator" ) + ".abcLearn" );
    }
}
