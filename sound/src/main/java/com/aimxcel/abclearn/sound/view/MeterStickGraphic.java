// Copyright 2002-2011, University of Colorado

/**
 * Class: MeterStickGraphic
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 9, 2004
 */
package com.aimxcel.abclearn.sound.view;

//import edu.colorado.phet.common.view.graphics.DefaultInteractiveGraphic;
//import edu.colorado.phet.common.view.graphics.mousecontrols.Translatable;

import java.awt.Component;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;

public class MeterStickGraphic extends CompositeAimxcelGraphic {
    private Component component;

    public MeterStickGraphic( Component component, AimxcelImageGraphic meterStickImg, Point2D.Double location ) {
        this.component = component;
        addGraphic( meterStickImg );
        this.setCursorHand();
        this.addTranslationListener( new ImageTranslator( meterStickImg, location ) );
    }

    private class ImageTranslator implements TranslationListener {
        private AimxcelImageGraphic img;
        private Point2D.Double location;

        ImageTranslator( AimxcelImageGraphic img, Point2D.Double location ) {
            this.img = img;
            this.location = location;
        }

        public void translationOccurred( TranslationEvent event ) {
            location.setLocation( location.getX() + event.getDx(), location.getY() + event.getDy() );
            img.setLocation( (int)location.getX(), (int)location.getY() );
        }
    }
}
