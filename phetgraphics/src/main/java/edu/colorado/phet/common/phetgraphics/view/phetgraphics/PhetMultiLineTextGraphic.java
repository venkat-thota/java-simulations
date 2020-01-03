

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author:samreid $
 * Revision : $Revision:14674 $
 * Date modified : $Date:2007-04-17 02:37:37 -0500 (Tue, 17 Apr 2007) $
 */
package edu.colorado.phet.common.phetgraphics.view.phetgraphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.util.Arrays;

import com.aimxcel.abclearn.common.abclearncommon.view.util.RectangleUtils;

/**
 * AbcLearnMultiLineTextGraphic
 *
 * @author ?
 * @version $Revision:14674 $
 */
public class AbcLearnMultiLineTextGraphic extends CompositeAbcLearnGraphic {
    private LineCreator lineCreator;
    private String[] text;
    private FontMetrics fontMetrics;

    public AbcLearnMultiLineTextGraphic( Component component, Font font, String[] text, Color color ) {
        this( component, font, text, new Basic( component, font, color ) );
    }

    public AbcLearnMultiLineTextGraphic( Component component, Font font, String[] text, Color color, int dx, int dy, Color backgroundColor ) {
        this( component, font, text, new Shadowed( component, font, color, dx, dy, backgroundColor ) );
    }

    public AbcLearnMultiLineTextGraphic( Component component, Font font, String[] text, LineCreator lineCreator ) {
        super( component );
        this.text = text;
        this.lineCreator = lineCreator;
        this.fontMetrics = component.getFontMetrics( font );
        init();
    }

    /**
     * @deprecated
     */
    public AbcLearnMultiLineTextGraphic( Component component, String[] text, Font font, int x, int y, Color color ) {
        this( component, text, font, x, y, new Basic( component, font, color ) );
    }

    /**
     * @deprecated
     */
    public AbcLearnMultiLineTextGraphic( Component component, String text, Font font, int x, int y, Color foreground, int dx, int dy, Color background ) {
        this( component, new String[] { text }, font, x, y, foreground, dx, dy, background );
    }

    /**
     * @deprecated
     */
    public AbcLearnMultiLineTextGraphic( Component component, String[] text, Font font, int x, int y, Color foreground, int dx, int dy, Color background ) {
        this( component, text, font, x, y, new Shadowed( component, font, foreground, dx, dy, background ) );
    }

    /**
     * @deprecated
     */
    public AbcLearnMultiLineTextGraphic( Component component, String[] text, Font font, int x, int y, LineCreator lineCreator ) {
        super( component );
        this.text = text;
        this.lineCreator = lineCreator;
        this.fontMetrics = component.getFontMetrics( font );
        init();
        setLocation( x, y );
    }

    private void init() {
        clear();
        int currentY = 0;
        for ( int i = 0; i < text.length; i++ ) {
            String s = text[i];
            AbcLearnGraphic g = lineCreator.createLine( s, 0, currentY );
            addGraphic( g );
            currentY += fontMetrics.getDescent() + fontMetrics.getLeading() + fontMetrics.getAscent();
        }
    }

    public Point getLeftCenter() {
        return RectangleUtils.getLeftCenter( getBounds() );
    }

    public Point getRightCenter() {
        return RectangleUtils.getRightCenter( getBounds() );
    }

    public void setText( String[] text ) {
        if ( Arrays.asList( this.text ).equals( Arrays.asList( text ) ) ) {
            return;
        }
        this.text = text;
        init();
        setBoundsDirty();
        autorepaint();
    }

    public void setText( String text ) {
        setText( new String[] { text } );
    }

    /**
     * An interface for creating lines of text.  Provide your own implementation if you want.
     */
    public interface LineCreator {
        AbcLearnGraphic createLine( String text, int x, int y );
    }

    static class Shadowed implements LineCreator {
        private Font font;
        private Color foregroundColor;
        private int dx;
        private int dy;
        private Color backgroundColor;
        private Component component;

        public Shadowed( Component component, Font font, Color foregroundColor, int dx, int dy, Color backgroundColor ) {
            this.component = component;
            this.font = font;
            this.foregroundColor = foregroundColor;
            this.dx = dx;
            this.dy = dy;
            this.backgroundColor = backgroundColor;
        }

        public AbcLearnGraphic createLine( String text, int x, int y ) {
            AbcLearnShadowTextGraphic pstg = new AbcLearnShadowTextGraphic( component, font, text, foregroundColor, dx, dy, backgroundColor );
            pstg.setLocation( x, y );
            return pstg;
        }
    }

    static class Basic implements LineCreator {
        private Font font;
        private Color color;
        private Component component;

        public Basic( Component component, Font font, Color color ) {
            this.component = component;
            this.font = font;
            this.color = color;
        }

        public AbcLearnGraphic createLine( String text, int x, int y ) {
            return new AbcLearnTextGraphic( component, font, text, color, x, y );
        }
    }

    ////////////////////////////////////////////
    // Persistence support
    //

    /**
     * Provided for Java Beans conformance
     */
    public AbcLearnMultiLineTextGraphic() {
    }

}