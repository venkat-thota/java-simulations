
package com.aimxcel.abclearn.chemistry.molecules;

import static com.aimxcel.abclearn.chemistry.model.Element.C;
import static com.aimxcel.abclearn.chemistry.model.Element.H;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
public class C2H2Node extends PComposite {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public C2H2Node() {

        AtomNode bigLeftNode = new AtomNode( C );
        AtomNode bigRightNode = new AtomNode( C );
        AtomNode smallLeftNode = new AtomNode( H );
        AtomNode smallRightNode = new AtomNode( H );

        // rendering order
        PComposite parentNode = new PComposite();
        addChild( parentNode );
        parentNode.addChild( smallLeftNode );
        parentNode.addChild( bigLeftNode );
        parentNode.addChild( bigRightNode );
        parentNode.addChild( smallRightNode );

        // layout
        double x = 0;
        double y = 0;
        bigLeftNode.setOffset( x, y );
        x = bigLeftNode.getFullBoundsReference().getMaxX() + ( 0.25 * bigRightNode.getFullBoundsReference().getWidth() );
        bigRightNode.setOffset( x, y );
        x = bigLeftNode.getFullBoundsReference().getMinX();
        smallLeftNode.setOffset( x, y );
        x = bigRightNode.getFullBoundsReference().getMaxX();
        smallRightNode.setOffset( x, y );

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }
}
