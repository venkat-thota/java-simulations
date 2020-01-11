// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.photonabsorption.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

/**
 * A node that looks like a vertical rod that is shaded.  This is generally
 * used to connect things in the view, or so make something look like it is on
 * a pole.
 *
 * @author John Blanco
 */
public class VerticalRodNode extends PNode {

    public VerticalRodNode( double width, double height, Color baseColor ) {
        Rectangle2D connectingRodShape = new Rectangle2D.Double( 0, 0, width, height );
        PNode connectingRod = new AimxcelPPath( connectingRodShape );
        connectingRod.setPaint( new GradientPaint( 0f, 0f, ColorUtils.brighterColor( baseColor, 0.5 ),
                                                   (float) connectingRodShape.getWidth(), 0f, ColorUtils.darkerColor( baseColor, 0.5 ) ) );
        addChild( connectingRod );
    }
}
