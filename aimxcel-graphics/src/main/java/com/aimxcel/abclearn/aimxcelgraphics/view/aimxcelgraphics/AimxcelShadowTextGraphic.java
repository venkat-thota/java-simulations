package com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;


public class AimxcelShadowTextGraphic extends AimxcelGraphic {
    private AimxcelTextGraphic foreground;
    private AimxcelTextGraphic background;

    public AimxcelShadowTextGraphic( Component component, Font font, String text, Color foregroundColor, int dx, int dy, Color backgroundColor ) {
        super( component );
        foreground = new AimxcelTextGraphic( component, font, text, foregroundColor );
        background = new AimxcelTextGraphic( component, font, text, backgroundColor, dx, dy );
    }

    /**
     * @deprecated
     */
    public AimxcelShadowTextGraphic( Component component, String text, Font font, int x, int y, Color foregroundColor, int dx, int dy, Color backgroundColor ) {
        super( component );
        foreground = new AimxcelTextGraphic( component, font, text, foregroundColor, 0, 0 );
        background = new AimxcelTextGraphic( component, font, text, backgroundColor, 0 + dx, 0 + dy );
        setLocation( x, y );
    }

    public void paint( Graphics2D g2 ) {
        if ( isVisible() ) {
            super.saveGraphicsState( g2 );
            super.updateGraphicsState( g2 );
            g2.transform( getNetTransform() );
            background.paint( g2 );
            foreground.paint( g2 );
            super.restoreGraphicsState();
        }
    }

    protected Rectangle determineBounds() {
        Rectangle fore = foreground.getBounds();
        Rectangle back = background.getBounds();
        if ( fore == null && back == null ) {
            return null;
        }
        else if ( fore == null ) {
            return back;
        }
        else if ( back == null ) {
            return fore;
        }
        else {
            Rectangle2D b = foreground.getBounds().createUnion( background.getBounds() );
            b = getNetTransform().createTransformedShape( b ).getBounds2D();
            return RectangleUtils.toRectangle( b );
        }
    }

    public void setText( String text ) {
        foreground.setText( text );
        background.setText( text );
        setBoundsDirty();
        autorepaint();
    }

    public void setColor( Color color ) {
        this.foreground.setColor( color );
    }

    public void setShadowColor( Color color ) {
        this.background.setColor( color );
    }

    public void setFont( Font font ) {
        foreground.setFont( font );
        background.setFont( font );
        setBoundsDirty();
        autorepaint();
    }

    //-----------------------------------------------------------
    // Provided for Java Beans conformance
    //-----------------------------------------------------------

    public AimxcelShadowTextGraphic() {
    }

    public AimxcelTextGraphic getForeground() {
        return foreground;
    }

    public void setForeground( AimxcelTextGraphic foreground ) {
        this.foreground = foreground;
    }

    public AimxcelTextGraphic getBackground() {
        return background;
    }

    public void setBackground( AimxcelTextGraphic background ) {
        this.background = background;
    }

    public String getText() {
        return foreground.getText();
    }
}
