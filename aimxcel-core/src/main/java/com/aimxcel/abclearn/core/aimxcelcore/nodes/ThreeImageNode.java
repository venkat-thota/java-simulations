

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class ThreeImageNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final PImage leftPatch;
    public final PImage centerPatch;
    public final PImage rightPatch;
    private final Property<Double> centerWidth;

    /*
     * Constructor that uses the leaves the center image size unchanged.
     */
    public ThreeImageNode( Image left, Image center, Image right ) {
        this( left, center, right, center.getWidth( null ) );
    }

    /*
     * Constructs a ThreeImageNode with the specified images, and the specified width for the center component.
     */
    public ThreeImageNode( Image left, Image center, Image right, double initialCenterWidth ) {
        this.centerWidth = new Property<Double>( initialCenterWidth );
        leftPatch = new PImage( left );
        addChild( leftPatch );

        centerPatch = new PImage( center );
        addChild( centerPatch );

        rightPatch = new PImage( right );
        addChild( rightPatch );

        setCenterWidth( initialCenterWidth );
    }

    public void setCenterWidth( double centerWidth ) {
        //Handling internal resizing before sending out notifications prevents an order dependency in listeners
        if ( centerWidth != this.centerWidth.get() ) {
            //Stretch center piece to fit the text, it is always an exact fit and there is no minimum.
            centerPatch.setTransform( new AffineTransform() );//reset the centerPatch so that its bounds can be used to compute the right scale sx
            double sx = centerWidth / centerPatch.getFullBounds().getWidth();//how much to scale the centerPatch
            centerPatch.setTransform( AffineTransform.getScaleInstance( sx, 1 ) );
            centerPatch.translate( leftPatch.getFullBounds().getMaxX() / sx, 0 );

            //Position the right patch to the side of the stretched center patch so they don't overlap
            rightPatch.setOffset( leftPatch.getFullBounds().getWidth() + centerWidth, 0 );
            this.centerWidth.set( centerWidth );
        }
    }

    public void addCenterWidthObserver( SimpleObserver simpleObserver ) {
        centerWidth.addObserver( simpleObserver );
    }
}
