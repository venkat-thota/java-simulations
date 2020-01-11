 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

public class PadBoundsNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double padding;

    public PadBoundsNode() {
        this( 1 );
    }

    public PadBoundsNode( double padding ) {
        if ( ! ( padding > 0 ) ) {
            throw new IllegalArgumentException( "padding must be > 0" );
        }
        this.padding = padding;
    }

    public PadBoundsNode( PNode node ) {
        this( 1, node );
    }

    public PadBoundsNode( double padding, PNode node ) {
        this( padding );
        addChild( node );
    }

    @Override public PBounds getFullBoundsReference() {
        PBounds b = super.getFullBoundsReference();
        return new PBounds( b.getX(), b.getY(), b.getWidth() + padding, b.getHeight() + padding );
    }
}