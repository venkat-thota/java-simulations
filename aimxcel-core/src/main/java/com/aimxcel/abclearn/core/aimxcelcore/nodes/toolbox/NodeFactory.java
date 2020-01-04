
package com.aimxcel.abclearn.core.aimxcelcore.nodes.toolbox;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ToolNode;


public interface NodeFactory {
    ToolNode createNode( ModelViewTransform transform, Property<Boolean> visible, Point2D location );
}
