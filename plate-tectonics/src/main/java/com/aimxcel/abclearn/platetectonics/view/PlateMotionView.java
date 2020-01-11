// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.platetectonics.view;

import java.util.ArrayList;

import com.aimxcel.abclearn.platetectonics.model.PlateMotionModel;
import com.aimxcel.abclearn.platetectonics.model.labels.BoundaryLabel;
import com.aimxcel.abclearn.platetectonics.tabs.PlateMotionTab;
import com.aimxcel.abclearn.platetectonics.util.MortalSimpleObserver;
import com.aimxcel.abclearn.platetectonics.util.Side;
import com.aimxcel.abclearn.platetectonics.view.labels.BoundaryLabelNode;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.lwjgl.nodes.GLNode;

/**
 * Specialized view with additional behavior for the Plate Motion tab.
 */
public class PlateMotionView extends PlateTectonicsView {
    public PlateMotionView( final PlateMotionModel model, final PlateMotionTab tab, final Property<Boolean> showWater ) {
        super( model, tab, showWater );

        // add smoke handling in
        final SmokeNode smokeNode = new SmokeNode( tab.getModelViewTransform(), ( (PlateMotionTab) tab ).getPlateMotionModel().smokePuffs );
        addChild( smokeNode );

        // hack way to keep this in front for now
        model.modelChanged.addListener( new VoidFunction1<Void>() {
            public void apply( Void aVoid ) {
                removeChild( smokeNode );
                addChild( smokeNode );
            }
        } );

        /*---------------------------------------------------------------------------*
        * boundary labels
        *----------------------------------------------------------------------------*/
        PlateMotionModel plateMotionModel = (PlateMotionModel) model;
        plateMotionModel.boundaryLabels.addElementAddedObserver( new VoidFunction1<BoundaryLabel>() {
            public void apply( final BoundaryLabel boundaryLabel ) {
                final BoundaryLabelNode boundaryLabelNode = new BoundaryLabelNode( boundaryLabel, tab.getModelViewTransform(), tab.colorMode ) {{
                    final PlateMotionTab plateMotionTab = (PlateMotionTab) tab;
                    plateMotionTab.showLabels.addObserver( new MortalSimpleObserver( plateMotionTab.showLabels, boundaryLabel.disposed ) {
                        public void update() {
                            setVisible( plateMotionTab.showLabels.get() );
                        }
                    } );
                }};
                addChild( boundaryLabelNode );
                nodeMap.put( boundaryLabel, boundaryLabelNode );
            }
        } );

        plateMotionModel.boundaryLabels.addElementRemovedObserver( new VoidFunction1<BoundaryLabel>() {
            public void apply( BoundaryLabel boundaryLabel ) {
                removeChild( nodeMap.get( boundaryLabel ) );
                nodeMap.remove( boundaryLabel );
            }
        } );

        // respond to events that tell the view to put a certain side in front
        plateMotionModel.frontBoundarySideNotifier.addListener( new VoidFunction1<Side>() {
            public void apply( Side side ) {
                // move all boundary nodes on top of this afterwards
                for ( GLNode boundaryNode : new ArrayList<GLNode>( getChildren() ) ) {
                    if ( boundaryNode instanceof BoundaryLabelNode && ( (BoundaryLabelNode) boundaryNode ).getBoundaryLabel().side == side ) {
                        removeChild( boundaryNode );
                        addChild( boundaryNode );
                    }
                }
            }
        } );
    }
}
