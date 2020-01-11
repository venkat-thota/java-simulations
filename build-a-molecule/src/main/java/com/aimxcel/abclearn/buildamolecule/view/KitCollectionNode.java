//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.view;

import com.aimxcel.abclearn.buildamolecule.control.KitPanel;
import com.aimxcel.abclearn.buildamolecule.model.CollectionList;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.KitCollection;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

/**
 * Contains the kits and atoms in the play area.
 */
public class KitCollectionNode extends PNode {

    public KitCollectionNode( final CollectionList collectionList, final KitCollection collection, BuildAMoleculeCanvas canvas ) {
        /*---------------------------------------------------------------------------*
        * layers
        *----------------------------------------------------------------------------*/
        final PNode bottomLayer = new PNode();
        final PNode metadataLayer = new PNode();
        final PNode atomLayer = new PNode();
        final PNode topLayer = new PNode();

        addChild( bottomLayer );
        addChild( atomLayer );
        addChild( metadataLayer );
        addChild( topLayer );

        bottomLayer.addChild( new KitPanel( collection, collectionList.getAvailableKitBounds() ) );

        for ( final Kit kit : collection.getKits() ) {
            KitView kitView = new KitView( kit, canvas );
            bottomLayer.addChild( kitView.getBottomLayer() );
            atomLayer.addChild( kitView.getAtomLayer() );
            metadataLayer.addChild( kitView.getMetadataLayer() );
            topLayer.addChild( kitView.getTopLayer() );
        }

        // set visibility based on whether our collection is the current one
        collectionList.currentCollection.addObserver( new SimpleObserver() {
            public void update() {
                setVisible( collectionList.currentCollection.get() == collection );
            }
        } );
    }
}
