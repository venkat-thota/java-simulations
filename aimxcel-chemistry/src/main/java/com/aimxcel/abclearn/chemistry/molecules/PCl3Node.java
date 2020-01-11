package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.Cl;
import static com.aimxcel.abclearn.chemistry.model.Element.P;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class PCl3Node extends PComposite {

    public PCl3Node() {

        // atom nodes
        AtomNode centerNode = new AtomNode( P );
        AtomNode leftNode = new AtomNode( Cl );
        AtomNode rightNode = new AtomNode( Cl );
        AtomNode bottomNode = new AtomNode( Cl );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( leftNode );
        parentNode.addChild( rightNode );
        parentNode.addChild( centerNode );
        parentNode.addChild( bottomNode );

        // layout
        double x = 0;
        double y = 0;
        centerNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getMinX();
        y = centerNode.getFullBoundsReference().getMaxY() - ( 0.25 * centerNode.getFullBoundsReference().getHeight() );
        leftNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getMaxX();
        y = leftNode.getYOffset();
        rightNode.setOffset( x, y );
        x = centerNode.getXOffset();
        y = centerNode.getFullBoundsReference().getMaxY();
        bottomNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
