
package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.Cl;
import static com.aimxcel.abclearn.chemistry.model.Element.P;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class PCl5Node extends PComposite {

    public PCl5Node() {

        // atom nodes
        AtomNode centerNode = new AtomNode( P );
        AtomNode topNode = new AtomNode( Cl );
        AtomNode bottomNode = new AtomNode( Cl );
        AtomNode rightNode = new AtomNode( Cl );
        AtomNode topLeftNode = new AtomNode( Cl );
        AtomNode bottomLeftNode = new AtomNode( Cl );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( rightNode );
        parentNode.addChild( bottomNode );
        parentNode.addChild( topLeftNode );
        parentNode.addChild( centerNode );
        parentNode.addChild( topNode );
        parentNode.addChild( bottomLeftNode );

        // layout
        double x = 0;
        double y = 0;
        centerNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getCenterX();
        y = centerNode.getFullBoundsReference().getMinY();
        topNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getCenterX();
        y = centerNode.getFullBoundsReference().getMaxY();
        bottomNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getMaxX();
        y = centerNode.getFullBoundsReference().getCenterY();
        rightNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getMinX() + ( 0.25 * centerNode.getFullBoundsReference().getWidth() );
        y = centerNode.getFullBoundsReference().getMinY() + ( 0.25 * centerNode.getFullBoundsReference().getHeight() );
        topLeftNode.setOffset( x, y );
        x = centerNode.getFullBoundsReference().getMinX() + ( 0.1 * centerNode.getFullBoundsReference().getWidth() );
        y = centerNode.getFullBoundsReference().getMaxY() - ( 0.1 * centerNode.getFullBoundsReference().getHeight() );
        bottomLeftNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
