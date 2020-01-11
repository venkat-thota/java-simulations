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

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent.makeMoleculesTab;
import static com.aimxcel.abclearn.chemistry.model.Element.*;

/**
 * Module for the 1st tab: collection boxes only take 1 molecule, and our 1st kit collection is always the same
 */
public class MakeMoleculeModule extends AbstractBuildAMoleculeModule {

    public MakeMoleculeModule( Frame parentFrame ) {
        super( makeMoleculesTab, parentFrame, BuildAMoleculeStrings.TITLE_MAKE_MOLECULE, new LayoutBounds( false, CollectionPanel.getCollectionPanelModelWidth( true ) ) );

        /*---------------------------------------------------------------------------*
        * initial model
        *----------------------------------------------------------------------------*/

        final KitCollection initialCollection = new KitCollection() {{
            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 400, 200 ), getClock(), H, 2 ),
                             new Bucket( new PDimension( 350, 200 ), getClock(), O, 1 )
            ) );
            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 400, 200 ), getClock(), H, 2 ),
                             new Bucket( new PDimension( 450, 200 ), getClock(), O, 2 )
            ) );
            addKit( new Kit( bounds,
                             new Bucket( new PDimension( 350, 200 ), getClock(), C, 1 ),
                             new Bucket( new PDimension( 450, 200 ), getClock(), O, 2 ),
                             new Bucket( new PDimension( 500, 200 ), getClock(), N, 2 )
            ) );
            addCollectionBox( new CollectionBox( MoleculeList.H2O, 1 ) );
            addCollectionBox( new CollectionBox( MoleculeList.O2, 1 ) );
            addCollectionBox( new CollectionBox( MoleculeList.H2, 1 ) );
            addCollectionBox( new CollectionBox( MoleculeList.CO2, 1 ) );
            addCollectionBox( new CollectionBox( MoleculeList.N2, 1 ) );
        }};

        setInitialCollection( initialCollection );
    }

    @Override protected BuildAMoleculeCanvas buildCanvas( CollectionList collectionList ) {
        return new MoleculeCollectingCanvas( collectionList, true, new VoidFunction0() {
            public void apply() {
                addGeneratedCollection();
            }
        } );
    }

    @Override protected KitCollection generateModel() {
        return generateModel( false, 5 );
    }
}
