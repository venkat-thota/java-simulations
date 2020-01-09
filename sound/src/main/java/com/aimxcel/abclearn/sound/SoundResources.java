// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.sound;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class SoundResources {

    private static AimxcelResources phetResources = new AimxcelResources( SoundConfig.PROJECT_NAME );

    public static AimxcelResources getResourceLoader() {
        return phetResources;
    }

    public static String getString( String key ) {
        return getResourceLoader().getLocalizedString( key );
    }
}
