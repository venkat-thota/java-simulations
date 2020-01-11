
package com.aimxcel.abclearn.eatingandexercise.control.valuenode;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class BorderNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AimxcelPPath borderPath;
    private PNode child;
    private double dw;
    private double dh;

    public BorderNode( PNode child, double dw, double dh ) {
        this.child = child;
        this.dw = dw;
        this.dh = dh;
        addChild( child );
        borderPath = new AimxcelPPath( new BasicStroke( 1 ), Color.black );
        addChild( borderPath );
        relayout();
    }

    private void relayout() {
        borderPath.setPathTo( RectangleUtils.expand( child.getFullBounds(), dw, dh ) );
    }

}
