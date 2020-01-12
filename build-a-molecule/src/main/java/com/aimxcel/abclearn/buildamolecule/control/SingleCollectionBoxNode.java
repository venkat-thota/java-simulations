package com.aimxcel.abclearn.buildamolecule.control;

import java.awt.geom.Rectangle2D;
import java.text.MessageFormat;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

/**
 * Allows the collection of a single molecule
 */
public class SingleCollectionBoxNode extends CollectionBoxNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SingleCollectionBoxNode( final CollectionBox box, Function1<PNode, Rectangle2D> toModelBounds ) {
        super( box, toModelBounds );
        assert ( box.getCapacity() == 1 );

        addHeaderNode( new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final HTMLNode nameAndFormula = new HTMLNode( MessageFormat.format( BuildAMoleculeStrings.COLLECTION_SINGLE_FORMAT,
                                                                                box.getMoleculeType().getGeneralFormulaFragment(),
                                                                                box.getMoleculeType().getDisplayName() ) ) {/**
																					 * 
																					 */
																					private static final long serialVersionUID = 1L;

																				{
                setFont( new AimxcelFont( 15, true ) );
            }};
            addChild( nameAndFormula );
        }} );
    }

    /*---------------------------------------------------------------------------*
    * precomputation of largest single collection box size
    *----------------------------------------------------------------------------*/

    private static double maxWidth;
    private static double maxHeight;

    static {
        maxWidth = 0;
        maxHeight = 0;

        // compute maximum width and height for all different molecules
        for ( CompleteMolecule molecule : MoleculeList.COLLECTION_BOX_MOLECULES ) {

            // fake boxes
            PBounds boxBounds = new SingleCollectionBoxNode( new CollectionBox( molecule, 1 ), new Function1<PNode, Rectangle2D>() {
                public Rectangle2D apply( PNode pNode ) {
                    return pNode.getFullBounds();
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
