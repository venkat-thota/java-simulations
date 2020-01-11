// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.chemistry.molecules;

import static com.aimxcel.abclearn.chemistry.model.Element.P;

import java.awt.Color;

import com.aimxcel.abclearn.chemistry.model.Atom;
import com.aimxcel.abclearn.chemistry.model.Element;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadedSphereNode;

/**
 * Atoms look like shaded spheres.
 * Origin is at geometric center of bounding rectangle.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AtomNode extends ShadedSphereNode {

    private static final double RATE_OF_CHANGE = 0.75; // >0 and <1, increase this to make small atoms appear smaller
    private static final double MAX_RADIUS = P.getRadius();
    private static final double MODEL_TO_VIEW_SCALE = 0.11;

    /*
     * There is a large difference between the radii of the smallest and largest atoms.
     * This function adjusts scaling so that the difference is still noticeable, but not as large.
     */
    private static final Function1<Double, Double> RADIUS_SCALING_FUNCTION = new Function1<Double, Double>() {
        public Double apply( Double radius ) {
            final double adjustedRadius = ( MAX_RADIUS - RATE_OF_CHANGE * ( MAX_RADIUS - radius ) );
            return MODEL_TO_VIEW_SCALE * adjustedRadius;
        }
    };

    public AtomNode( Element element ) {
        this( element.getRadius(), element.getColor() );
    }

    public AtomNode( Atom atom ) {
        super( atom.getRadius(), atom.getColor() );
    }

    /**
     * Creates an atom node with the specified radius and color.  The radius is mapped through a scaling function to put all radii within a good range.
     *
     * @param radius the radius of the atom in picometers
     * @param color  the base color of the atom, will be used as the basis for a gradient
     */
    public AtomNode( double radius, Color color ) {
        super( 2 * RADIUS_SCALING_FUNCTION.apply( radius ), color );
    }
}