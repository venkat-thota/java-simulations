//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.control;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants.MODEL_VIEW_TRANSFORM;
import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.MessageFormat;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.KitCollection;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.NextPreviousNavigationNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

/**
 * Contains the kit background and controls for switching between kits
 */
public class KitPanel extends PNode {

    private static final double KIT_ARROW_Y_OFFSET = 5; // vertical offset of the kit arrows from the top of the kit

    public KitPanel( final KitCollection kitCollectionModel, PBounds availableKitBounds ) {

        assert ( MODEL_VIEW_TRANSFORM.getTransform().getScaleY() < 0 ); // we assume this and correct for it

        final Rectangle2D kitViewBounds = MODEL_VIEW_TRANSFORM.modelToViewRectangle( availableKitBounds );
        PPath background = PPath.createRectangle(
                (float) kitViewBounds.getX(),
                (float) kitViewBounds.getY(),
                (float) kitViewBounds.getWidth(),
                (float) kitViewBounds.getHeight() );

        background.setPaint( BuildAMoleculeConstants.KIT_BACKGROUND );
        background.setStrokePaint( BuildAMoleculeConstants.KIT_BORDER );
        addChild( background );

        /*---------------------------------------------------------------------------*
        * label and next/previous
        *----------------------------------------------------------------------------*/

        addChild( new NextPreviousNavigationNode( new PText() {{
            // this is our label
            setFont( new AimxcelFont( 18, true ) );
            kitCollectionModel.getCurrentKitProperty().addObserver( new SimpleObserver() {
                public void update() {
                    setText( MessageFormat.format( BuildAMoleculeStrings.KIT_LABEL, kitCollectionModel.getCurrentKitIndex() + 1 ) );
                }
            } );
        }}, BuildAMoleculeConstants.KIT_ARROW_BACKGROUND_ENABLED, BuildAMoleculeConstants.KIT_ARROW_BORDER_ENABLED, 17, 24 ) {
            {
                // hook up listeners
                kitCollectionModel.getCurrentKitProperty().addObserver( new SimpleObserver() {
                    public void update() {
                        hasNext.set( kitCollectionModel.hasNextKit() );
                        hasPrevious.set( kitCollectionModel.hasPreviousKit() );
                    }
                } );

                setOffset( kitViewBounds.getMaxX() - getFullBounds().getWidth() - 5, kitViewBounds.getY() + KIT_ARROW_Y_OFFSET );
            }

            @Override protected void next() {
                kitCollectionModel.goToNextKit();
            }

            @Override protected void previous() {
                kitCollectionModel.goToPreviousKit();
            }
        } );

        /*---------------------------------------------------------------------------*
        * refill kit
        *----------------------------------------------------------------------------*/

        addChild( new HTMLImageButtonNode( BuildAMoleculeStrings.KIT_REFILL, new AimxcelFont( Font.BOLD, 12 ), Color.ORANGE ) {
            private SimpleObserver observer; // makes sure that we are enabled or disabled whenever the current kit is

            {
                setUserComponent( UserComponent.refillKit );
                addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent e ) {
                        kitCollectionModel.getCurrentKit().resetKit();
                    }
                } );
                observer = new SimpleObserver() {
                    public void update() {
                        setEnabled( kitCollectionModel.getCurrentKit().hasAtomsOutsideOfBuckets() );
                    }
                };
                for ( Kit kit : kitCollectionModel.getKits() ) {
                    kit.addMoleculeListener( new Kit.MoleculeListener() {
                        public void addedMolecule( Molecule molecule ) {
                            observer.update();
                        }

                        public void removedMolecule( Molecule molecule ) {
                            observer.update();
                        }
                    } );
                }
                kitCollectionModel.getCurrentKitProperty().addObserver( observer );
                setOffset( kitViewBounds.getMinX() + 5, kitViewBounds.getMinY() + 5 );
            }
        } );
    }
}
