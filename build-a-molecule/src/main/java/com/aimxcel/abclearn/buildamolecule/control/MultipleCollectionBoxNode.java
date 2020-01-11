//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.control;

import java.awt.geom.Rectangle2D;
import java.text.MessageFormat;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

/**
 * Displays a collection box that can collect multiple molecules with two text labels above. One shows the "goal", and the other shows the current
 * quantity present in the box.
 */
public class MultipleCollectionBoxNode extends CollectionBoxNode {

    public MultipleCollectionBoxNode( final CollectionBox box, Function1<PNode, Rectangle2D> toModelBounds ) {
        super( box, toModelBounds );

        addHeaderNode( new PNode() {{
            HTMLNode goalNode = new HTMLNode( MessageFormat.format( BuildAMoleculeStrings.COLLECTION_MULTIPLE_GOAL_FORMAT, box.getCapacity(), box.getMoleculeType().getGeneralFormulaFragment() ) ) {{
                setFont( new AimxcelFont( 15, true ) );
            }};
            addChild( goalNode );
        }} );
        addHeaderNode( new HTMLNode() {{
            /*
             * Adding this text in will make text the same height (generally) whether or not they contain subscripts.
             * Before, the general positioning would be different on molecules which do not involve subscripts (HCN, NO, etc.)
             */
            final String subscriptFix = "<sub> </sub>";

            setFont( new AimxcelFont( 14 ) );

            // update when the quantity changes
            box.quantity.addObserver( new SimpleObserver() {
                public void update() {
                    if ( box.quantity.get() == 0 ) {
                        setHTML( subscriptFix + BuildAMoleculeStrings.COLLECTION_MULTIPLE_QUANTITY_EMPTY + subscriptFix );
                    }
                    else {
                        setHTML( MessageFormat.format( subscriptFix + BuildAMoleculeStrings.COLLECTION_MULTIPLE_QUANTITY_FORMAT + subscriptFix, box.quantity.get(), box.getMoleculeType().getGeneralFormulaFragment() ) );
                    }
                }
            } );
        }}
        );
    }

    /*---------------------------------------------------------------------------*
    * precomputation of largest multiple collection box size
    *----------------------------------------------------------------------------*/

    private static double maxWidth;
    private static double maxHeight;

    static {
        maxWidth = 0;
        maxHeight = 0;

        // compute maximum width and height for all different molecules
        for ( CompleteMolecule molecule : MoleculeList.COLLECTION_BOX_MOLECULES ) {
            PBounds boxBounds = new MultipleCollectionBoxNode( new CollectionBox( molecule, 1 ), new Function1<PNode, Rectangle2D>() {
                public Rectangle2D apply( PNode pNode ) {
                    return null;
                }
            } ).getFullBounds();

            maxWidth = Math.max( maxWidth, boxBounds.getWidth() );
            maxHeight = Math.max( maxHeight, boxBounds.getHeight() );
        }
    }

    public static double getMaxWidth() {
        return maxWidth;
    }

    public static double getMaxHeight() {
        return maxHeight;
    }
}