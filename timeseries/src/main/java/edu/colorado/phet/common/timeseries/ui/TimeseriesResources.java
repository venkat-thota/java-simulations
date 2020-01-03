
package edu.colorado.phet.common.timeseries.ui;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

/**
 * Author: Sam Reid
 * May 15, 2007, 8:12:31 PM
 */
public class TimeseriesResources {
    private static AbcLearnResources timeseriesresources = new AbcLearnResources( "timeseries" );

    public static String getString( String s ) {
        return AbcLearnCommonResources.getString( s );
    }

    public static BufferedImage loadBufferedImage( String s ) {
        return timeseriesresources.getImage( s );
    }
}
