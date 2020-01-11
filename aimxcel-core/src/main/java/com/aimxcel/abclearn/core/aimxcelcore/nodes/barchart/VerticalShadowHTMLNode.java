
package com.aimxcel.abclearn.core.aimxcelcore.nodes.barchart;

import java.awt.Color;
import java.awt.Font;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowHTMLNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;



public class VerticalShadowHTMLNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShadowHTMLNode shadowHTMLNode;

    public VerticalShadowHTMLNode( Font font, String text, Color color, Color shadowColor ) {
        shadowHTMLNode = new ShadowHTMLNode( text );
        shadowHTMLNode.setColor( color );
        shadowHTMLNode.setShadowColor( shadowColor );
        shadowHTMLNode.setFont( font );

        shadowHTMLNode.translate( -3, -10 );//todo: remove the need for these magic numbers
        shadowHTMLNode.rotate( -Math.PI / 2 );

        addChild( shadowHTMLNode );
    }

    public void setText( String text ) {
        this.shadowHTMLNode.setHtml( text );
    }

    public void setShadowVisible( boolean shadowVisible ) {
        this.shadowHTMLNode.setShadowVisible( shadowVisible );
    }

    public void setForeground( Color color ) {
        this.shadowHTMLNode.setColor( color );
    }
}
