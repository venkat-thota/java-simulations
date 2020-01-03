
package edu.colorado.phet.eatingandexercise.view;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.RectangleUtils;

import edu.colorado.phet.common.piccolophet.nodes.HTMLNode;
import edu.colorado.phet.common.piccolophet.nodes.AbcLearnPPath;
import edu.umd.cs.piccolo.PNode;

/**
 * Created by: Sam
 * Jun 24, 2008 at 11:43:12 AM
 */
public class LabelNode extends PNode {
    private HTMLNode htmlNode;
    private AbcLearnPPath background;

    public LabelNode( String text ) {
        htmlNode = new HTMLNode( text );
        htmlNode.setFont( new AbcLearnFont( 16, true ) );

        Color color = new Color( 246, 239, 169, 200 );
        background = new AbcLearnPPath( color );

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
