package com.aimxcel.abclearn.sound;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class SoundResources {

    private static AimxcelResources aimxcelResources = new AimxcelResources( SoundConfig.PROJECT_NAME );

    public static AimxcelResources getResourceLoader() {
        return aimxcelResources;
    }

    public static String getString( String key ) {
        return getResourceLoader().getLocalizedString( key );
    }
}
