package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import fj.data.List;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.util.Iterator;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

class CaretNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaretNode() {
        addChild( new AimxcelPPath( createUnclosedShapeFromPoints( List.list( Vector2D.v( -10, 10 ), Vector2D.v( 0, 0 ), Vector2D.v( 10, 10 ) ) ), new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL ), Color.black ) );
    }

    private static Shape createUnclosedShapeFromPoints( Iterable<Vector2D> points ) {
        Iterator<Vector2D> iterator = points.iterator();
        DoubleGeneralPath path = new DoubleGeneralPath( iterator.next() );
        while ( iterator.hasNext() ) {
            path.lineTo( iterator.next() );
        }
        return path.getGeneralPath();
    }
}