
package com.aimxcel.abclearn.photonabsorption.view;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.atoms.Atom;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.RoundGradientPaint;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class AtomNode extends PNode {

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final Atom atom;
    private final ModelViewTransform2D mvt;
    private AimxcelPPath highlightNode;

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public AtomNode( Atom atom, ModelViewTransform2D mvt ) {

        this.atom = atom;
        this.mvt = mvt;

        // Create a gradient for giving the sphere a 3D effect.
        double transformedRadius = mvt.modelToViewDifferentialXDouble( atom.getRadius() );
        Color lightColor = Color.WHITE;
        Color darkColor;
        if ( atom.getRepresentationColor() != Color.WHITE ) {
            darkColor = ColorUtils.darkerColor( atom.getRepresentationColor(), 0.1 );
        }
        else {
            darkColor = Color.LIGHT_GRAY;
        }

        int highlightWidth = 13;
        final RoundGradientPaint baseGradientPaint =
                new RoundGradientPaint( -transformedRadius / 2, -transformedRadius / 2, lightColor, new Point2D.Double( transformedRadius / 2, transformedRadius / 2 ), darkColor );
        final RoundGradientPaint haloGradientPaint =
                new RoundGradientPaint( 0, 0, Color.yellow, new Point2D.Double( transformedRadius + highlightWidth, transformedRadius + highlightWidth ), new Color( 0, 0, 0, 0 ) );
        highlightNode = new AimxcelPPath( new Ellipse2D.Double( -transformedRadius - highlightWidth, -transformedRadius - highlightWidth,
                                                             transformedRadius * 2 + highlightWidth * 2, transformedRadius * 2 + highlightWidth * 2 ),
                                       haloGradientPaint );
        AimxcelPPath atomNode = new AimxcelPPath( new Ellipse2D.Double( -transformedRadius, -transformedRadius,
                                                                  transformedRadius * 2, transformedRadius * 2 ), baseGradientPaint );
        addChild( highlightNode );
        addChild( atomNode );
        atom.addObserver( new SimpleObserver() {
            public void update() {
                updatePosition();
            }
        } );
        updatePosition();
    }

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    private void updatePosition() {
        setOffset( mvt.modelToViewDouble( atom.getPositionRef() ) );
    }

    // ------------------------------------------------------------------------
    // Inner Classes and Interfaces
    //------------------------------------------------------------------------

    public void setHighlighted( boolean highlighted ) {
        highlightNode.setVisible( highlighted );
    }
}
