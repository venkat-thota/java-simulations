
package com.aimxcel.abclearn.core.aimxcelcore.nodes.layout;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public interface PositionStrategy {
    Point2D getRelativePosition( PNode node, double maxSize, double location /* x or y coordinate, depending on orientation of box */ );
}
