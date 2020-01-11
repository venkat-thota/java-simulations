package com.aimxcel.abclearn.chemistry.molecules;

import static com.aimxcel.abclearn.chemistry.model.Element.C;
import static com.aimxcel.abclearn.chemistry.model.Element.H;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;

public class C2H4Node extends PComposite {

    public C2H4Node() {

        AtomNode bigLeftNode = new AtomNode( C );
        AtomNode bigRightNode = new AtomNode( C );
        AtomNode smallTopLeftNode = new AtomNode( H );
        AtomNode smallTopRightNode = new AtomNode( H );
        AtomNode smallBottomLeftNode = new AtomNode( H );
        AtomNode smallBottomRightNode = new AtomNode( H );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallTopRightNode );
        parentNode.addChild( smallTopLeftNode );
        parentNode.addChild( bigLeftNode );
        parentNode.addChild( bigRightNode );
        parentNode.addChild( smallBottomLeftNode );
        parentNode.addChild( smallBottomRightNode );

        // layout
        final double offsetSmall = smallTopLeftNode.getFullBoundsReference().getWidth() / 4;
        double x = 0;
        double y = 0;
        bigLeftNode.setOffset( x, y );
        x = bigLeftNode.getFullBoundsReference().getMaxX() + ( 0.25 * bigRightNode.getFullBoundsReference().getWidth() );
        y = bigLeftNode.getYOffset();
        bigRightNode.setOffset( x, y );
        x = bigLeftNode.getFullBoundsReference().getMinX() + offsetSmall;
        y = bigLeftNode.getFullBoundsReference().getMinY() + offsetSmall;
        smallTopLeftNode.setOffset( x, y );
        x = bigRightNode.getFullBoundsReference().getMaxX() - offsetSmall;
        y = bigRightNode.getFullBoundsReference().getMinY() + offsetSmall;
        smallTopRightNode.setOffset( x, y );
        x = bigLeftNode.getFullBoundsReference().getMinX() + offsetSmall;
        y = bigLeftNode.getFullBoundsReference().getMaxY() - offsetSmall;
        smallBottomLeftNode.setOffset( x, y );
        x = bigRightNode.getFullBoundsReference().getMaxX() - offsetSmall;
        y = bigRightNode.getFullBoundsReference().getMaxY() - offsetSmall;
        smallBottomRightNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
