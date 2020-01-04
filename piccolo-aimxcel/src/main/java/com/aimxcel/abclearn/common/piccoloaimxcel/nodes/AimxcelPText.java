// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * Convenience class for creating a PText with the specified text and font
 *
 * @author Sam Reid
 */
public class AimxcelPText extends PText {
    public AimxcelPText( String text ) {
        super( text );
    }

    //Create a AimxcelPText with the specified text and font.
    public AimxcelPText( String text, Font font ) {

        //Set the font first so that it doesn't waste time computing the layout twice (once for the default font and once for the specified font)
        setFont( font );
        setText( text );
    }

    public AimxcelPText( Font font ) {
        setFont( font );
    }

    public AimxcelPText( String text, Font font, Paint paint ) {

        //Set the font first so that it doesn't waste time computing the layout twice (once for the default font and once for the specified font)
        setFont( font );
        setText( text );
        setTextPaint( paint );
    }

    //The following copied from AimxcelPNode, see comments there.  Copied because AimxcelPText extends PText instead of wrapping it.
    public double getFullWidth() {
        return getFullBoundsReference().getWidth();
    }

    public double getFullHeight() {
        return getFullBoundsReference().getHeight();
    }

    public double getMaxX() {
        return getFullBoundsReference().getMaxX();
    }

    public double getMaxY() {
        return getFullBoundsReference().getMaxY();
    }

    //Note the mismatch between getMinX and getX
    public double getMinX() {
        return getFullBoundsReference().getMinX();
    }

    //Note the mismatch between getMinY and getY
    public double getMinY() {
        return getFullBoundsReference().getMinY();
    }

    public double getCenterY() {
        return getFullBoundsReference().getCenterY();
    }

    public double getCenterX() {
        return getFullBoundsReference().getCenterX();
    }

    public void centerFullBoundsOnPoint( Point2D point ) {
        super.centerFullBoundsOnPoint( point.getX(), point.getY() );
    }

    public void centerFullBoundsOnPoint( Vector2D point ) {
        super.centerFullBoundsOnPoint( point.getX(), point.getY() );
    }
}