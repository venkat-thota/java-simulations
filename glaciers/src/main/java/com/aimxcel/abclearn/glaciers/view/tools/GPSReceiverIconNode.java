// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.glaciers.model.AbstractTool;
import com.aimxcel.abclearn.glaciers.model.IToolProducer;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;
import com.aimxcel.abclearn.glaciers.view.tools.AbstractToolIconNode.InteractiveToolIconNode;

/**
 * GPSReceiverIconNode
 */
public class GPSReceiverIconNode extends InteractiveToolIconNode {
    
    public GPSReceiverIconNode( IToolProducer toolProducer, GlaciersModelViewTransform mvt  ) {
        super( GPSReceiverNode.createImage(), toolProducer, mvt );
    }
    
    public AbstractTool createTool( Point2D position ) {
        return getToolProducer().createGPSReceiver( position );
    }
}