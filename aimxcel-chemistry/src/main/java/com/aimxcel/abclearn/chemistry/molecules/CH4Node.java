
package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.C;
import static com.aimxcel.abclearn.chemistry.model.Element.H;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class CH4Node extends PComposite {

    public CH4Node() {

        // atom nodes
        AtomNode bigNode = new AtomNode( C );
        AtomNode smallTopLeftNode = new AtomNode( H );
        AtomNode smallTopRightNode = new AtomNode( H );
        AtomNode smallBottomLeftNode = new AtomNode( H );
        AtomNode smallBottomRightNode = new AtomNode( H );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallTopRightNode );
        parentNode.addChild( smallBottomLeftNode );
        parentNode.addChild( bigNode );
        parentNode.addChild( smallTopLeftNode );
        parentNode.addChild( smallBottomRightNode );

        // layout
        final double offsetSmall = smallTopLeftNode.getFullBoundsReference().getWidth() / 4;
        double x = 0;
        double y = 0;
        bigNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMinX() + offsetSmall;
        y = bigNode.getFullBoundsReference().getMinY() + offsetSmall;
        smallTopLeftNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMaxX() - offsetSmall;
        y = bigNode.getFullBoundsReference().getMinY() + offsetSmall;
        smallTopRightNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMinX() + offsetSmall;
        y = bigNode.getFullBoundsReference().getMaxY() - offsetSmall;
        smallBottomLeftNode.setOffset( x, y );
        x = bigNode.getFullBoundsReference().getMaxX() - offsetSmall;
        y = bigNode.getFullBoundsReference().getMaxY() - offsetSmall;
        smallBottomRightNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
