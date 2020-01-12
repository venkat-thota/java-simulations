
package com.aimxcel.abclearn.theramp.common;

import java.awt.*;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowHTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;



public class VerticalTextGraphic extends PNode {
    private Font font;
    private String text;

    public VerticalTextGraphic( Font font, String text, Color color, Color outline ) {
        super();
        this.font = font;
        this.text = text;
        ShadowHTMLNode aimxcelTextNode = new ShadowHTMLNode( text );//, font, color, 1, 1, outline );
        aimxcelTextNode.setColor( color );
        aimxcelTextNode.setShadowColor( outline );
        aimxcelTextNode.setFont( font );

        aimxcelTextNode.translate( -3, -10 );
        aimxcelTextNode.rotate( -Math.PI / 2 );

        addChild( aimxcelTextNode );
    }

    public String getText() {
        return text;
    }
}