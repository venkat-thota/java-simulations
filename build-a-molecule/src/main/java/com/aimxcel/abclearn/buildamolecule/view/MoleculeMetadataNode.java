//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.view;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeResources;
import com.aimxcel.abclearn.buildamolecule.model.Atom2D;
import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;
import com.aimxcel.abclearn.buildamolecule.view.view3d.JmolDialogProperty;
import com.aimxcel.abclearn.buildamolecule.view.view3d.ShowMolecule3DButtonNode;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants.MODEL_VIEW_TRANSFORM;
import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent.breakApartButton;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager.sendButtonPressed;

/**
 * Displays the molecule name and 'X' to break apart the molecule
 */
public class MoleculeMetadataNode extends PNode {
    private Molecule molecule;
    private final JmolDialogProperty dialog = new JmolDialogProperty();

    private Map<Atom2D, SimpleObserver> observerMap = new HashMap<Atom2D, SimpleObserver>();

    public MoleculeMetadataNode( final Kit kit, final Molecule molecule ) {
        // SwingLayoutNode was doing some funky stuff (and wasn't centering), so we're rolling back to manual positioning
        this.molecule = molecule;

        if ( molecule.getAtoms().size() < 2 ) {
            // we don't need anything at all if it is not a "molecule"
            return;
        }

        final CompleteMolecule completeMolecule = molecule.getMatchingCompleteMolecule();

        final Property<Double> currentX = new Property<Double>( 0.0 );

        if ( completeMolecule != null ) {
            /*---------------------------------------------------------------------------*
            * label with chemical formula and common name
            *----------------------------------------------------------------------------*/
            addChild( new HTMLNode( completeMolecule.getDisplayName() ) {{
                setFont( new AimxcelFont( 14, true ) );
                currentX.set( currentX.get() + getFullBounds().getWidth() + 10 );
            }} );

            /*---------------------------------------------------------------------------*
            * show 3d button
            *----------------------------------------------------------------------------*/
            addChild( new ShowMolecule3DButtonNode( dialog, completeMolecule ) {{
                setOffset( currentX.get(), 0 );
                currentX.set( currentX.get() + getFullBounds().getWidth() + 5 );
            }} );
        }

        /*---------------------------------------------------------------------------*
        * break-up button
        *----------------------------------------------------------------------------*/
        addChild( new PNode() {{
            addChild( new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_SPLIT_ICON ) ) );
            addInputEventListener( new CursorHandler() {
                @Override
                public void mouseClicked( PInputEvent event ) {
                    sendButtonPressed( breakApartButton );
                    kit.breakMolecule( molecule );
                }
            } );
            setOffset( currentX.get(), 0 );
        }} );

        for ( final Atom2D atom : molecule.getAtoms() ) {
            atom.addPositionListener( new SimpleObserver() {
                {
                    observerMap.put( atom, this );
                }

                public void update() {
                    updatePosition();
                }
            } );
        }

        updatePosition(); // sanity check. should update (unfortunately) a number of times above

        // hide 3D dialogs when the kit is hidden
        kit.visible.addObserver( new SimpleObserver() {
            public void update() {
                if ( !kit.visible.get() ) {
                    dialog.hideDialogIfShown();
                }
            }
        } );
    }

    public void destruct() {
        for ( Atom2D atomModel : observerMap.keySet() ) {
            atomModel.removePositionListener( observerMap.get( atomModel ) );
        }
        dialog.hideDialogIfShown();
    }

    public void updatePosition() {
        PBounds modelPositionBounds = molecule.getPositionBounds();
        Rectangle2D moleculeViewBounds = MODEL_VIEW_TRANSFORM.modelToView( modelPositionBounds ).getBounds2D();

        setOffset( moleculeViewBounds.getCenterX() - getFullBounds().getWidth() / 2, // horizontally center
                   moleculeViewBounds.getY() - getFullBounds().getHeight() - BuildAMoleculeConstants.METADATA_PADDING_BETWEEN_NODE_AND_MOLECULE ); // offset from top of molecule
    }
}
