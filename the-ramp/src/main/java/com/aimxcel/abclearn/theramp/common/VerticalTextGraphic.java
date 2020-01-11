
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
        ShadowHTMLNode phetTextNode = new ShadowHTMLNode( text );//, font, color, 1, 1, outline );
        phetTextNode.setColor( color );
        phetTextNode.setShadowColor( outline );
        phetTextNode.setFont( font );

        phetTextNode.translate( -3, -10 );
        phetTextNode.rotate( -Math.PI / 2 );

        addChild( phetTextNode );
    }

    public String getText() {
        return text;
    }
}