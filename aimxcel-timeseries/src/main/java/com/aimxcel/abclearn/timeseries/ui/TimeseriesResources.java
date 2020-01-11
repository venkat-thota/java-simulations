
package com.aimxcel.abclearn.timeseries.ui;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class TimeseriesResources {
    private static AimxcelResources timeseriesresources = new AimxcelResources( "timeseries" );

    public static String getString( String s ) {
        return AimxcelCommonResources.getString( s );
    }

    public static BufferedImage loadBufferedImage( String s ) {
        return timeseriesresources.getImage( s );
    }
}
