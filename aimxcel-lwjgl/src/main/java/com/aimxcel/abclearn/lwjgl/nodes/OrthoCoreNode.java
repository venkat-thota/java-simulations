package com.aimxcel.abclearn.lwjgl.nodes;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.VoidNotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Core3DCanvas;
import com.aimxcel.abclearn.lwjgl.CanvasTransform;
import com.aimxcel.abclearn.lwjgl.LWJGLTab;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class OrthoCoreNode extends OrthoSwingNode {
    private final PNode node;

    public OrthoCoreNode( final PNode node, final LWJGLTab tab, CanvasTransform canvasTransform, Property<Vector2D> position, final VoidNotifier mouseEventNotifier ) {
        // use a wrapper panel that takes up no extra room
        super( new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) ) {{
            add( new Core3DCanvas( node ) );
        }}, tab, canvasTransform, position, mouseEventNotifier );
        this.node = node;
    }

    public Core3DCanvas getCanvas() {
        return (Core3DCanvas) ( getComponent().getComponent( 0 ) );
    }

    public PNode getNode() {
        return node;
    }
}
