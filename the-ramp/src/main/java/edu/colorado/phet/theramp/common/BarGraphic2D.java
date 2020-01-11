
package edu.colorado.phet.theramp.common;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ModelViewTransform1D;
import edu.colorado.phet.theramp.view.RampFontSet;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;



public class BarGraphic2D extends PNode {
    private ModelViewTransform1D transform1D;
    private double value;
    private int x;
    private int width;
    private int y;
    private VerticalTextGraphic label;
    private PPath rectangle3DGraphic;
    public double labelHeight;
    private double labelWidth;

    public BarGraphic2D( String text, ModelViewTransform1D transform1D, double value, int x, int width, int y, int dx, int dy, Paint paint ) {
        super();
        this.transform1D = transform1D;
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = width;

        rectangle3DGraphic = new PPath( null );
        rectangle3DGraphic.setPaint( paint );
        rectangle3DGraphic.setStroke( new BasicStroke( 1 ) );
        rectangle3DGraphic.setStrokePaint( Color.black );

        Color textColor = new Color( 240, 225, 255 );
        label = new VerticalTextGraphic( RampFontSet.getFontSet().getBarFont(), text, Color.black, textColor );
        addChild( rectangle3DGraphic );

        addChild( label );
        labelHeight = label.getHeight();
        labelWidth = label.getWidth();
        updateBar();
    }

    private void updateBar() {
        int height = computeHeight();
        Rectangle rect = new Rectangle( x, y - height, width, height );

        label.setOffset( rect.x + 2 - labelWidth, (int) ( 5 + y + labelHeight ) );

        rectangle3DGraphic.setPathTo( rect );
    }

    public void setValue( double value ) {
        if ( value != this.value && Math.abs( value ) != Math.abs( this.value ) ) {
            this.value = value;
            if ( value < 0 ) {
                rectangle3DGraphic.setOffset( 0, -computeHeight() );
            }
            else {
                rectangle3DGraphic.setOffset( 0, 0 );
            }
            this.value = Math.abs( value );
            updateBar();
        }
    }

    private int computeHeight() {
        return transform1D.modelToView( value );
    }

    public void setBarHeight( double baselineY ) {
        this.y = (int) baselineY;
        updateBar();
    }
}
