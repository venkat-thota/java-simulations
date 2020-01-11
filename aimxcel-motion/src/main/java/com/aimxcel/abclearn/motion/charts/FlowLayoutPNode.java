
package com.aimxcel.abclearn.motion.charts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class FlowLayoutPNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlowLayoutPNode() {
        addPropertyChangeListener( PNode.PROPERTY_CHILDREN, new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                relayout();
            }
        } );
        relayout();
    }

    private void relayout() {
        for ( int i = 0; i < getChildrenCount(); i++ ) {
            getChild( i ).setOffset( 0, 0 );
        }
        //never changed offset of first child, so assume it is still at 0,0
        for ( int i = 1; i < getChildrenCount(); i++ ) {
            PNode previousChild = getChild( i - 1 );
            getChild( i ).setOffset( previousChild.getFullBounds().getMaxX() - getChild( i ).getFullBounds().getMinX(), 0 );
        }
    }
}
