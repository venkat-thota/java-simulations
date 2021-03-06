 
package com.aimxcel.abclearn.core.aimxcelcore.nodes.photon;

import java.awt.Color;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.RoundGradientPaint;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.VisibleColor;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class PhotonNode extends AimxcelPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// UV and IR colors
    private static final Color UV_COLOR = new Color( 160, 160, 160 ); // gray
    private static final Color IR_COLOR = UV_COLOR;

    // Sparkle
    private static final double SPARKLE_ANGLE = Math.toRadians( 18 );
    private static final Color SPARKLE_COLOR = new Color( 255, 255, 255, 100 ); // sparkle color for visible wavelengths
    private static final Color UV_SPARKLE_COLOR = VisibleColor.wavelengthToColor( 400, UV_COLOR, UV_COLOR );
    private static final Color IR_SPARKLE_COLOR = VisibleColor.wavelengthToColor( 715, UV_COLOR, UV_COLOR );

   
    public PhotonNode( double wavelength, double diameter ) {
        this( wavelength, diameter, 0.5 * diameter, 0.575 * diameter );
    }

   
    public PhotonNode( double wavelength, double haloDiameter, double orbDiameter, double sparkleDiameter ) {

        // assemble the components of the node
        PNode parentNode = new PNode();
        parentNode.addChild( new CircleNode( getHaloPaint( wavelength, haloDiameter ), haloDiameter ) );
        parentNode.addChild( new CircleNode( getOrbPaint( wavelength, orbDiameter ), orbDiameter ) );
        parentNode.addChild( new SparkleNode( getSparkleColor( wavelength, sparkleDiameter ), sparkleDiameter, SPARKLE_ANGLE ) );

        // convert to image, center
        PImage imageNode = new PImage( parentNode.toImage() );
        addChild( imageNode );
        imageNode.setOffset( -imageNode.getFullBoundsReference().getWidth() / 2,
                             -imageNode.getFullBoundsReference().getHeight() / 2 );
    }

    // Used for the orb and halo. Origin at center.
    private static class CircleNode extends PPath {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CircleNode( Paint paint, double diameter ) {
            Shape shape = new Ellipse2D.Double( -diameter / 2, -diameter / 2, diameter, diameter );
            setPathTo( shape );
            setPaint( paint );
            setStroke( null );
        }
    }

    // Sparkle at the center of the photon. Origin at center.
    private static class SparkleNode extends PNode {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SparkleNode( Paint paint, double diameter, double angle ) {
            PNode bigCrosshairs = new CrosshairsNode( paint, diameter );
            PNode smallCrosshairs = new CrosshairsNode( paint, 0.7 * diameter );
            smallCrosshairs.rotate( Math.toRadians( 45 ) );
            addChild( smallCrosshairs );
            addChild( bigCrosshairs );
            rotate( angle );
        }
    }

    // Crosshairs used to create the sparkle. Origin at center.
    private static class CrosshairsNode extends PNode {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CrosshairsNode( Paint paint, double diameter ) {

            final double crosshairWidth = diameter;
            final double crosshairHeight = 0.15 * crosshairWidth;
            Shape crosshairShape = new Ellipse2D.Double( -crosshairWidth / 2, -crosshairHeight / 2, crosshairWidth, crosshairHeight );

            PPath horizontalPart = new PPath( crosshairShape );
            horizontalPart.setPaint( paint );
            horizontalPart.setStroke( null );

            PPath verticalPart = new PPath( crosshairShape );
            verticalPart.setPaint( paint );
            verticalPart.setStroke( null );
            verticalPart.rotate( Math.toRadians( 90 ) );

            addChild( horizontalPart );
            addChild( verticalPart );
        }
    }

    /*
     * Override this if you want a different mapping of wavelength to orb paint.
     * The default is a round gradient that is solid in the center and a bit transparent towards the outer edge.
     * Use caution when overriding: this method is called in the constructor.
     */
    protected Paint getOrbPaint( double wavelength, double orbDiameter ) {
        Color innerColor = new Color( 255, 255, 255, 180 );
        Color outerColor = ColorUtils.createColor( VisibleColor.wavelengthToColor( wavelength, UV_COLOR, IR_COLOR ), 130 );
        return new RoundGradientPaint( 0, 0, innerColor, new Point2D.Double( 0.25 * orbDiameter, 0.25 * orbDiameter ), outerColor );
    }

    /*
     * Override this if you want a different mapping of wavelength to halo paint.
     * The default is a round gradient that is solid in the center and fully transparent a the outer edge.
     * Use caution when overriding: this method is called in the constructor.
     */
    protected Paint getHaloPaint( double wavelength, double haloDiameter ) {
        Color innerColor = VisibleColor.wavelengthToColor( wavelength, UV_COLOR, IR_COLOR );
        Color outerColor = ColorUtils.createColor( innerColor, 0 );
        return new RoundGradientPaint( 0, 0, innerColor, new Point2D.Double( 0.4 * haloDiameter, 0.4 * haloDiameter ), outerColor );
    }

    /*
     * Override this if you want a different mapping of wavelength to sparkle paint.
     * Use caution when overriding: this method is called in the constructor.
     */
    protected Paint getSparkleColor( double wavelength, double sparkleDiameter ) {
        if ( wavelength < VisibleColor.MIN_WAVELENGTH ) {
            return UV_SPARKLE_COLOR;
        }
        else if ( wavelength > VisibleColor.MAX_WAVELENGTH ) {
            return IR_SPARKLE_COLOR;
        }
        else {
            return SPARKLE_COLOR;
        }
    }

    // Convenience method for creating photon images
    public static Image createImage( double wavelength, double diameter ) {
        return new PhotonNode( wavelength, diameter ).toImage();
    }
}
