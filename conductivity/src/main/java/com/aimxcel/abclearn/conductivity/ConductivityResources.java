package com.aimxcel.abclearn.conductivity;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

public class ConductivityResources {
    private static AimxcelResources resources = new AimxcelResources( "conductivity" );

    public static String getString( String key ) {
        return resources.getLocalizedString( key );
    }

    public static AimxcelResources getResourceLoader() {
        return resources;
    }
}
