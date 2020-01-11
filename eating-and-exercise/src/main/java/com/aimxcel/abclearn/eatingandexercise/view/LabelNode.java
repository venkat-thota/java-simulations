
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class LabelNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HTMLNode htmlNode;
    private AimxcelPPath background;

    public LabelNode( String text ) {
        htmlNode = new HTMLNode( text );
        htmlNode.setFont( new AimxcelFont( 16, true ) );

        Color color = new Color( 246, 239, 169, 200 );
        background = new AimxcelPPath( color );

        addChild( background );
        addChild( htmlNode );
        updateBackgroundShape();
        setPickable( false );
        setChildrenPickable( false );
    }

    public void setFont( Font font ) {
        htmlNode.setFont( font );
        updateBackgroundShape();
    }

    private void updateBackgroundShape() {
        Rectangle2D rectangle2D = RectangleUtils.expandRectangle2D( htmlNode.getFullBounds(), 5, 5 );
        background.setPathTo( new RoundRectangle2D.Double( rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight(), 10, 10 ) );
    }

    public void setText( String text ) {
        this.htmlNode.setHTML( text );
        updateBackgroundShape();
    }
}
