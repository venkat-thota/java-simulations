// Copyright 2002-2011, University of Colorado
package edu.colorado.phet.theramp.v2.view;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDebug;

public class RampSimPanel extends AimxcelPCanvas {
    public RampSimPanel( TestRampModule module ) {
        addScreenChild( new PText( "test" ) );

        PDebug.debugRegionManagement = true;
        RampModelView rampModelView = new RampModelView( module );
        addScreenChild( rampModelView );
    }
}
