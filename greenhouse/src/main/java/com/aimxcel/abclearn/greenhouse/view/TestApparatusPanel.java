// Copyright 2002-2011, University of Colorado

/**
 * Class: TestApparatusPanel
 * Package: edu.colorado.phet.greenhouse
 * Author: Another Guy
 * Date: Dec 8, 2003
 */
package com.aimxcel.abclearn.greenhouse.view;

import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.greenhouse.common.graphics.ApparatusPanel;

public class TestApparatusPanel extends ApparatusPanel {

    public TestApparatusPanel( double aspectRatio, FlipperAffineTransformFactory flipperAffineTransformFactory ) {
        super( flipperAffineTransformFactory );
    }

    public void setModelBounds( Rectangle2D.Double bounds ) {
        super.setAffineTransformFactory( new FlipperAffineTransformFactory( bounds ) );
        super.updateTransform();
    }
}
