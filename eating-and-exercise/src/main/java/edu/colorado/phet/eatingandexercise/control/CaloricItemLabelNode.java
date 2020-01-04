
package edu.colorado.phet.eatingandexercise.control;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;

import edu.umd.cs.piccolo.PNode;

public class CaloricItemLabelNode extends PNode {
    private HTMLNode htmlNode;

    public CaloricItemLabelNode( String s ) {
        htmlNode = new HTMLNode( s );
        htmlNode.setFont( new AimxcelFont( 16, true ) );

        Rectangle2D rectangle2D = RectangleUtils.expandRectangle2D( htmlNode.getFullBounds(), 5, 5 );
        AimxcelPPath background = new AimxcelPPath( new RoundRectangle2D.Double( rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight(), 10, 10 ), new Color( 246, 239, 169 ) );

        addChild( background );
        addChild( htmlNode );
    }

    public void setText( String labelText ) {
        htmlNode.setHTML( labelText );
    }
}
