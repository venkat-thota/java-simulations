
package com.aimxcel.abclearn.motion.graphs;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ZoomControlNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;



public class ZoomSuiteNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZoomControlNode verticalZoomControlNode;
    private ZoomControlNode horizontalZoomControlNode;

    public ZoomSuiteNode() {
        verticalZoomControlNode = new ZoomControlNode( ZoomControlNode.VERTICAL );
        horizontalZoomControlNode = new ZoomControlNode( ZoomControlNode.HORIZONTAL );

        addChild( verticalZoomControlNode );
        addChild( horizontalZoomControlNode );

        relayout();
    }

    public void addVerticalZoomListener( ZoomControlNode.ZoomListener zoomListener ) {
        verticalZoomControlNode.addZoomListener( zoomListener );
    }

    public void addHorizontalZoomListener( ZoomControlNode.ZoomListener zoomListener ) {
        horizontalZoomControlNode.addZoomListener( zoomListener );
    }

    private void relayout() {
        horizontalZoomControlNode.setOffset( 0, verticalZoomControlNode.getFullBounds().getMaxY() + 0 );
    }

    public void setVerticalZoomInEnabled( boolean enabled ) {
        verticalZoomControlNode.setZoomInEnabled( enabled );
    }

    public void setVerticalZoomOutEnabled( boolean enabled ) {
        verticalZoomControlNode.setZoomOutEnabled( enabled );
    }

    public void setHorizontalZoomInEnabled( boolean enabled ) {
        horizontalZoomControlNode.setZoomInEnabled( enabled );
    }

    public void setHorizontalZoomOutEnabled( boolean enabled ) {
        horizontalZoomControlNode.setZoomOutEnabled( enabled );
    }
}