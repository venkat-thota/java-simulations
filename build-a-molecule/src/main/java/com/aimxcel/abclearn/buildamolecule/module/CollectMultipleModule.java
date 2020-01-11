//  Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.buildamolecule.module;

import java.awt.Frame;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.control.CollectionPanel;
import com.aimxcel.abclearn.buildamolecule.model.Bucket;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.CollectionList;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.KitCollection;
import com.aimxcel.abclearn.buildamolecule.model.LayoutBounds;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;
import com.aimxcel.abclearn.buildamolecule.view.BuildAMoleculeCanvas;
import com.aimxcel.abclearn.buildamolecule.view.MoleculeCollectingCanvas;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent.collectMultipleTab;
import static com.aimxcel.abclearn.chemistry.model.Element.*;

/**
 * Module for 2nd tab. Collection boxes take multiple molecules of the same type, and start off with a different kit collection each time
 */
public class CollectMultipleModule extends AbstractBuildAMoleculeModule {

    public CollectMultipleModule( Frame parentFrame ) {
        super( collectMultipleTab, parentFrame, BuildAMoleculeStrings.TITLE_COLLECT_MULTIPLE, new LayoutBounds( false, CollectionPanel.getCollectionPanelModelWidth( false ) ) );

        /*---------------------------------------------------------------------------*
        * initial model
        *----------------------------------------------------------------------------*/

        final KitCollection initialCollection = new KitCollection() {{
            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 400, 200 ), getClock(), H, 2 ),
                             new Bucket( new PDimension( 450, 200 ), getClock(), O, 2 )
            ) );

            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 500, 200 ), getClock(), C, 2 ),
                             new Bucket( new PDimension( 600, 200 ), getClock(), O, 4 ),
                             new Bucket( new PDimension( 500, 200 ), getClock(), N, 2 )
            ) );
            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 600, 200 ), getClock(), H, 12 ),
                             new Bucket( new PDimension( 600, 200 ), getClock(), O, 4 ),
                             new Bucket( new PDimension( 500, 200 ), getClock(), N, 2 )
            ) );
            addCollectionBox( new CollectionBox( MoleculeList.CO2, 2 ) );
            addCollectionBox( new CollectionBox( MoleculeList.O2, 2 ) );
            addCollectionBox( new CollectionBox( MoleculeList.H2, 4 ) );
            addCollectionBox( new CollectionBox( MoleculeList.NH3, 2 ) );
        }};

        setInitialCollection( initialCollection );
    }

    @Override protected BuildAMoleculeCanvas buildCanvas( CollectionList collectionList ) {
        return new MoleculeCollectingCanvas( collectionList, false, new VoidFunction0() {
            public void apply() {
                addGeneratedCollection();
            }
        } );
    }

    @Override protected KitCollection generateModel() {
        return generateModel( true, 4 );
    }
}
