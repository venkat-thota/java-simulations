// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.lwjgl.nodes;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.Core3DCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

/**
 * Renders an arbitrary Core node into a quadrilateral. Does not have to be orthographic, and can have arbitrary transformations applied to it.
 * <p/>
 * NOTE: Any updates to the Core node should be done exclusively within the Swing Event Dispatch Thread, since this is backed by a PCanvas
 * See lwjgl-implementation-notes.txt
 */
public class PlanarCoreNode extends PlanarSwingNode {
    private final PNode node;

    public PlanarCoreNode( final PNode node ) {
        // use a wrapper panel that takes up no extra room
        super( new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) ) {{
            add( new Core3DCanvas( node ) );
        }} );
        this.node = node;
    }

    public Core3DCanvas getCanvas() {
        return (Core3DCanvas) ( getComponent().getComponent( 0 ) );
    }

    public PNode getNode() {
        return node;
    }
}
