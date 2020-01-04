
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.toolbox;

import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.ToolNode;

import edu.umd.cs.piccolo.event.PInputEvent;

/**
 * Convenience subclass so that the ToolNode.dragAll is called on dragNode.
 *
 * @author Sam Reid
 */
public class BoundedToolDragHandler extends CanvasBoundedDragHandler {
    private ToolNode node;

    public BoundedToolDragHandler( ToolNode node ) {
        super( node );
        this.node = node;
    }

    public BoundedToolDragHandler( ToolNode node, PInputEvent event ) {
        super( node, event );
        this.node = node;
    }

    //Drags the node according to the definition in ToolNode.dragAll
    @Override protected void dragNode( DragEvent event ) {
        node.dragAll( event.delta );
    }
}
