package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.*;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class CH2ONode extends PComposite {

    public CH2ONode() {

        // atom nodes
        AtomNode leftNode = new AtomNode( C );
        AtomNode smallTopNode = new AtomNode( H );
        AtomNode smallBottomNode = new AtomNode( H );
        AtomNode rightNode = new AtomNode( O );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallTopNode );
        parentNode.addChild( leftNode );
        parentNode.addChild( rightNode );
        parentNode.addChild( smallBottomNode );

        // layout
        final double offsetSmall = smallTopNode.getFullBoundsReference().getWidth() / 4;
        double x = 0;
        double y = 0;
        leftNode.setOffset( x, y );
        x = leftNode.getFullBoundsReference().getMaxX() + ( 0.25 * rightNode.getFullBoundsReference().getWidth() );
        y = leftNode.getYOffset();
        rightNode.setOffset( x, y );
        x = leftNode.getFullBoundsReference().getMinX() + offsetSmall;
        y = leftNode.getFullBoundsReference().getMinY() + offsetSmall;
        smallTopNode.setOffset( x, y );
        x = smallTopNode.getXOffset();
        y = leftNode.getFullBoundsReference().getMaxY() - offsetSmall;
        smallBottomNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
