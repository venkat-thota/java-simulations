
package com.aimxcel.abclearn.core.aimxcelcore.nodes.layout;

import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PNode;

public interface PositionStrategy {
    Point2D getRelativePosition( PNode node, double maxSize, double location /* x or y coordinate, depending on orientation of box */ );
}
