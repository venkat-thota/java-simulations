package com.aimxcel.abclearn.greenhouse.view;

import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.greenhouse.common.graphics.ApparatusPanel;

public class TestApparatusPanel extends ApparatusPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestApparatusPanel( double aspectRatio, FlipperAffineTransformFactory flipperAffineTransformFactory ) {
        super( flipperAffineTransformFactory );
    }

    public void setModelBounds( Rectangle2D.Double bounds ) {
        super.setAffineTransformFactory( new FlipperAffineTransformFactory( bounds ) );
        super.updateTransform();
    }
}
