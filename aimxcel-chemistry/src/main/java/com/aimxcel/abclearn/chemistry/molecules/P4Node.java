package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.P;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class P4Node extends PComposite {

    public P4Node() {

        // atom nodes
        AtomNode topNode = new AtomNode( P );
        AtomNode bottomLeftNode = new AtomNode( P );
        AtomNode bottomRightNode = new AtomNode( P );
        AtomNode bottomBackNode = new AtomNode( P );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( bottomBackNode );
        parentNode.addChild( bottomRightNode );
        parentNode.addChild( bottomLeftNode );
        parentNode.addChild( topNode );

        // layout
        double x = 0;
        double y = 0;
        topNode.setOffset( x, y );

        x = topNode.getFullBoundsReference().getMinX() + ( 0.3 * topNode.getFullBoundsReference().getWidth() );
        y = topNode.getFullBoundsReference().getMaxY() + ( 0.2 * topNode.getFullBoundsReference().getWidth() );
        bottomLeftNode.setOffset( x, y );

        x = topNode.getFullBoundsReference().getMaxX();
        y = topNode.getFullBoundsReference().getMaxY();
        bottomRightNode.setOffset( x, y );

        x = topNode.getFullBoundsReference().getMinX();
        y = topNode.getFullBoundsReference().getCenterY() + ( 0.2 * topNode.getFullBoundsReference().getHeight() );
        bottomBackNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
