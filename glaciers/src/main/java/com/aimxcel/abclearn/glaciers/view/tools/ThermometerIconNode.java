// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.glaciers.model.AbstractTool;
import com.aimxcel.abclearn.glaciers.model.IToolProducer;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;
import com.aimxcel.abclearn.glaciers.view.tools.AbstractToolIconNode.InteractiveToolIconNode;

/**
 * ThermometerIconNode
 */
public class ThermometerIconNode extends InteractiveToolIconNode {
    
    public ThermometerIconNode( IToolProducer toolProducer, GlaciersModelViewTransform mvt ) {
        super( ThermometerNode.createImage(), toolProducer, mvt );
    }
    
    public AbstractTool createTool( Point2D position ) {
        return getToolProducer().addThermometer( position );
    }
}