
package com.aimxcel.abclearn.core.aimxcelcore.nodes.toolbox;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ToolNode;

import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

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
