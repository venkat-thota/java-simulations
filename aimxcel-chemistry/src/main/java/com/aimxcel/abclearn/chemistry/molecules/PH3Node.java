package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.H;
import static com.aimxcel.abclearn.chemistry.model.Element.P;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class PH3Node extends PComposite {

    public PH3Node() {

        // atom nodes
        AtomNode bigNode = new AtomNode( P );
        AtomNode smallLeftNode = new AtomNode( H );
        AtomNode smallRightNode = new AtomNode( H );
        AtomNode smallBottomNode = new AtomNode( H );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallLeftNode );
        parentNode.addChild( smallRightNode );
        parentNode.addChild( bigNode );
        parentNode.addChild( smallBottomNode );

        // layout
        double x = 0;
        double y = 0;
        bigNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMinX();
        y = bigNode.getFullBoundsReference().getMaxY() - ( 0.25 * bigNode.getFullBoundsReference().getHeight() );
        smallLeftNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMaxX();
        y = smallLeftNode.getYOffset();
        smallRightNode.setOffset( x, y );
        x = bigNode.getXOffset();
        y = bigNode.getFullBoundsReference().getMaxY();
        smallBottomNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
