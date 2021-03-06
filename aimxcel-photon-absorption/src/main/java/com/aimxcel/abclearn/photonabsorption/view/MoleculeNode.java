
package com.aimxcel.abclearn.photonabsorption.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.photonabsorption.model.atoms.Atom;
import com.aimxcel.abclearn.photonabsorption.model.atoms.AtomicBond;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class MoleculeNode extends PNode {

    //------------------------------------------------------------------------
    // Class Data
    //------------------------------------------------------------------------

    // This flag is used to turn on/off the appearance of the center of
    // gravity, which is useful for debugging.
    private static final boolean SHOW_COG = false;

    //------------------------------------------------------------------------
    // Instance Data
    //------------------------------------------------------------------------

    private final PNode atomLayer;
    private final PNode bondLayer;

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------

    public MoleculeNode( final Molecule molecule, ModelViewTransform2D mvt ) {
        bondLayer = new PNode();
        addChild( bondLayer );
        atomLayer = new PNode();
        addChild( atomLayer );

        for ( Atom atom : molecule.getAtoms() ) {
            atomLayer.addChild( new AtomNode( atom, mvt ) );
        }

        for ( AtomicBond atomicBond : molecule.getAtomicBonds() ) {
            bondLayer.addChild( new AtomicBondNode( atomicBond, mvt ) );
        }
        final Molecule.Adapter listener = new Molecule.Adapter() {
            @Override
            public void electronicEnergyStateChanged( Molecule molecule ) {
                super.electronicEnergyStateChanged( molecule );
                for ( int i = 0; i < atomLayer.getChildrenCount(); i++ ) {
                    AtomNode atomNode = (AtomNode) atomLayer.getChild( i );
                    atomNode.setHighlighted( molecule.isHighElectronicEnergyState() );
                }
            }
        };
        molecule.addListener( listener );
        listener.electronicEnergyStateChanged( molecule );

        // If enabled, show the center of gravity of the molecule.
        if ( SHOW_COG ) {
            double cogMarkerRadius = 5;
            Shape cogMarkerShape = new Ellipse2D.Double( -cogMarkerRadius, -cogMarkerRadius, cogMarkerRadius * 2, cogMarkerRadius * 2 );
            PNode cogMarkerNode = new AimxcelPPath( cogMarkerShape, Color.pink );
            cogMarkerNode.setOffset( mvt.modelToViewDouble( molecule.getCenterOfGravityPos() ) );
            atomLayer.addChild( cogMarkerNode );
        }
    }

    //------------------------------------------------------------------------
    // Methods
    //------------------------------------------------------------------------

    /**
     * Retrieve an image representation of this node.  This was created in
     * order to support putting molecule images on control panels, but may
     * have other usages.
     */
    public BufferedImage getImage() {
        Image image = this.toImage();
        assert image instanceof BufferedImage;
        if ( image instanceof BufferedImage ) {
            return (BufferedImage) this.toImage();
        }
        else {
            return null;
        }
    }
}
