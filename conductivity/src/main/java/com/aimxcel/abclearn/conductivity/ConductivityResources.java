// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.conductivity;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

/**
 * Created by IntelliJ IDEA.
 * User: Sam
 * Date: Sep 25, 2008
 * Time: 9:20:14 AM
 */
public class ConductivityResources {
    private static AimxcelResources resources = new AimxcelResources( "conductivity" );

    public static String getString( String key ) {
        return resources.getLocalizedString( key );
    }

    public static AimxcelResources getResourceLoader() {
        return resources;
    }
}
