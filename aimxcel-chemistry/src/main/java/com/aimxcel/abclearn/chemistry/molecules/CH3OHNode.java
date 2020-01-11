package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.*;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class CH3OHNode extends PComposite {

    public CH3OHNode() {

        // atom nodes
        AtomNode leftNode = new AtomNode( C );
        AtomNode smallTopNode = new AtomNode( H );
        AtomNode smallBottomNode = new AtomNode( H );
        AtomNode smallLeftNode = new AtomNode( H );
        AtomNode rightNode = new AtomNode( O );
        AtomNode smallRightNode = new AtomNode( H );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallBottomNode );
        parentNode.addChild( smallTopNode );
        parentNode.addChild( leftNode );
        parentNode.addChild( smallLeftNode );
        parentNode.addChild( smallRightNode );
        parentNode.addChild( rightNode );

        // layout
        double x = 0;
        double y = 0;
        leftNode.setOffset( x, y );
        x = leftNode.getFullBoundsReference().getMaxX() + ( 0.25 * rightNode.getFullBoundsReference().getWidth() );
        y = leftNode.getYOffset();
        rightNode.setOffset( x, y );
        x = leftNode.getXOffset();
        y = leftNode.getFullBoundsReference().getMinY();
        smallTopNode.setOffset( x, y );
        x = smallTopNode.getXOffset();
        y = leftNode.getFullBoundsReference().getMaxY();
        smallBottomNode.setOffset( x, y );
        x = leftNode.getFullBoundsReference().getMinX();
        y = leftNode.getYOffset();
        smallLeftNode.setOffset( x, y );
        x = rightNode.getFullBoundsReference().getMaxX();
        y = rightNode.getYOffset();
        smallRightNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
