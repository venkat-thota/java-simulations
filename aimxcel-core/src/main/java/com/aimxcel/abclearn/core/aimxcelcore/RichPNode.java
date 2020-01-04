 
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import edu.umd.cs.piccolo.PNode;

public class RichPNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RichPNode( PNode... children ) {
        for ( PNode child : children ) {
            addChild( child );
        }
    }

    public RichPNode( final Collection<? extends PNode> children ) {
        for ( PNode child : children ) {
            addChild( child );
        }
    }

    
    public ArrayList<PNode> getChildren() {
        return new ArrayList<PNode>() {{
            for ( int i = 0; i < getChildrenCount(); i++ ) {
                add( getChild( i ) );
            }
        }};
    }

    public double getFullWidth() {
        return getFullBoundsReference().getWidth();
    }

    public double getFullHeight() {
        return getFullBoundsReference().getHeight();
    }

    public double getMaxX() {
        return getFullBoundsReference().getMaxX();
    }

    public double getMaxY() {
        return getFullBoundsReference().getMaxY();
    }

    //Note the mismatch between getMinX and getX
    public double getMinX() {
        return getFullBoundsReference().getMinX();
    }

    //Note the mismatch between getMinY and getY
    public double getMinY() {
        return getFullBoundsReference().getMinY();
    }

    public double getCenterY() {
        return getFullBoundsReference().getCenterY();
    }

    public double getCenterX() {
        return getFullBoundsReference().getCenterX();
    }

    public void centerFullBoundsOnPoint( Point2D point ) {
        super.centerFullBoundsOnPoint( point.getX(), point.getY() );
    }

    public void centerFullBoundsOnPoint( Vector2D point ) {
        super.centerFullBoundsOnPoint( point.getX(), point.getY() );
    }
}