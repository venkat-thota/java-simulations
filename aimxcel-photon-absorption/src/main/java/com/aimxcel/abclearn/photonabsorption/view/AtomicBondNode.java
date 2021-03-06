
package com.aimxcel.abclearn.photonabsorption.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.atoms.AtomicBond;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

public class AtomicBondNode extends PNode {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    // Constants that control the width of the bond representation with
    // with respect to the average atom radius.
    private static double BOND_WIDTH_PROPORTION_SINGLE = 0.45;
    private static double BOND_WIDTH_PROPORTION_DOUBLE = 0.28;
    private static double BOND_WIDTH_PROPORTION_TRIPLE = 0.24;

    private static Color BOND_COLOR = new Color( 0, 200, 0 );

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final AtomicBond atomicBond;
    private final ModelViewTransform2D mvt;
    private final double averageAtomRadius;

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public AtomicBondNode( AtomicBond atomicBond, ModelViewTransform2D mvt ) {
        assert atomicBond.getBondCount() > 0 && atomicBond.getBondCount() <= 3;  // Only single through triple bonds currently supported.
        this.atomicBond = atomicBond;
        this.mvt = mvt;

        // Listen to the bond for changes that may require an update of the
        // representation.
        atomicBond.addObserver( new SimpleObserver() {
            public void update() {
                updateRepresentation();
            }
        } );

        // Calculate the width to use for the bond representation(s).
        averageAtomRadius = mvt.modelToViewDifferentialXDouble( ( atomicBond.getAtom1().getRadius() + atomicBond.getAtom2().getRadius() ) / 2 );

        // Create the initial representation.
        updateRepresentation();
    }

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    private void updateRepresentation() {
        removeAllChildren();  // Clear out any previous representations.
        float bondWidth;
        switch( atomicBond.getBondCount() ) {
            case 1: {
                // Single bond, so connect it from the center of one atom to the
                // center of the other.
                Point2D transformedPt1 = mvt.modelToViewDouble( atomicBond.getAtom1().getPositionRef() );
                Point2D transformedPt2 = mvt.modelToViewDouble( atomicBond.getAtom2().getPositionRef() );
                bondWidth = (float) ( BOND_WIDTH_PROPORTION_SINGLE * averageAtomRadius );
                PPath bond = new AimxcelPPath( new BasicStroke( bondWidth ), BOND_COLOR );
                bond.setPathTo( new Line2D.Double( transformedPt1, transformedPt2 ) );
                addChild( bond );
                break;
            }

            case 2: {
                // Double bond.
                final double transformedRadius = mvt.modelToViewDifferentialXDouble( Math.min( atomicBond.getAtom1().getRadius(),
                                                                                               atomicBond.getAtom2().getRadius() ) );
                // Get the center points of the two atoms.
                Point2D p1 = mvt.modelToViewDouble( atomicBond.getAtom1().getPositionRef() );
                Point2D p2 = mvt.modelToViewDouble( atomicBond.getAtom2().getPositionRef() );
                final double angle = Math.atan2( p1.getX() - p2.getX(), p1.getY() - p2.getY() );
                // Create a vector that will act as the offset from the center
                // point to the origin of the bond line.
                MutableVector2D offsetVector = new MutableVector2D() {{
                    setMagnitude( transformedRadius / 3 );
                    setAngle( angle );
                }};
                // Draw the bonds.
                Stroke bondLineStroke = new BasicStroke( (float) ( BOND_WIDTH_PROPORTION_DOUBLE * averageAtomRadius ) );
                PPath bond1 = new AimxcelPPath( bondLineStroke, BOND_COLOR );
                bond1.setPathTo( new Line2D.Double( p1.getX() + offsetVector.getX(), p1.getY() - offsetVector.getY(), p2.getX() + offsetVector.getX(), p2.getY() - offsetVector.getY() ) );
                offsetVector.rotate( Math.PI );
                PPath bond2 = new AimxcelPPath( bondLineStroke, BOND_COLOR );
                bond2.setPathTo( new Line2D.Double( p1.getX() + offsetVector.getX(), p1.getY() - offsetVector.getY(), p2.getX() + offsetVector.getX(), p2.getY() - offsetVector.getY() ) );
                addChild( bond1 );
                addChild( bond2 );
                break;
            }

            case 3: {
                // Triple bond.
                // Double bond.
                final double transformedRadius = mvt.modelToViewDifferentialXDouble( Math.min( atomicBond.getAtom1().getRadius(),
                                                                                               atomicBond.getAtom2().getRadius() ) );
                // Get the center points of the two atoms.
                Point2D p1 = mvt.modelToViewDouble( atomicBond.getAtom1().getPositionRef() );
                Point2D p2 = mvt.modelToViewDouble( atomicBond.getAtom2().getPositionRef() );
                final double angle = Math.atan2( p1.getX() - p2.getX(), p1.getY() - p2.getY() );
                // Create a vector that will act as the offset from the center
                // point to the origin of the bond line.
                MutableVector2D offsetVector = new MutableVector2D() {{
                    setMagnitude( transformedRadius * 0.6 );
                    setAngle( angle );
                }};
                // Draw the bonds.
                Stroke bondLineStroke = new BasicStroke( (float) ( BOND_WIDTH_PROPORTION_TRIPLE * averageAtomRadius ) );
                PPath bond1 = new AimxcelPPath( bondLineStroke, BOND_COLOR );
                bond1.setPathTo( new Line2D.Double( p1, p2 ) );
                PPath bond2 = new AimxcelPPath( bondLineStroke, BOND_COLOR );
                bond2.setPathTo( new Line2D.Double( p1.getX() + offsetVector.getX(), p1.getY() - offsetVector.getY(), p2.getX() + offsetVector.getX(), p2.getY() - offsetVector.getY() ) );
                offsetVector.rotate( Math.PI );
                PPath bond3 = new AimxcelPPath( bondLineStroke, BOND_COLOR );
                bond3.setPathTo( new Line2D.Double( p1.getX() + offsetVector.getX(), p1.getY() - offsetVector.getY(), p2.getX() + offsetVector.getX(), p2.getY() - offsetVector.getY() ) );
                addChild( bond1 );
                addChild( bond2 );
                addChild( bond3 );
                break;
            }

            default:
                System.err.println( getClass().getName() + " - Error: Can't represent bond number, value = " + atomicBond.getBondCount() );
                assert false;
                break;
        }
    }
}
