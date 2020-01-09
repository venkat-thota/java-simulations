// Copyright 2002-2011, University of Colorado

/**
 * Class: VerticalGuideline
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 6, 2004
 */
package com.aimxcel.abclearn.sound.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;

public class VerticalGuideline extends CompositeAimxcelGraphic {
    private int xLocation;

    public VerticalGuideline( final Component component, Color color, int position ) {
        super( null );
        final VerticalLine verticalLine = new VerticalLine( component, color, position );
        addGraphic( verticalLine );
        this.setCursor( Cursor.getPredefinedCursor( Cursor.W_RESIZE_CURSOR ) );
        this.xLocation = position;
        this.addTranslationListener( new TranslationListener() {
            public void translationOccurred( TranslationEvent event ) {
                xLocation += event.getDx();
                verticalLine.setLocation( xLocation );
                component.repaint();
            }
        } );
    }

    private static class VerticalLine extends AimxcelShapeGraphic {

        private Rectangle2D.Double line = new Rectangle2D.Double();
        private Color color;

        public VerticalLine( Component component, Color color, int position ) {
            super( component, null, color );
            setShape( line );
            setLocation( position );
            this.color = color;
        }

        public void paint( Graphics2D g ) {
            Color oldColor = g.getColor();
            g.setColor( color );
            g.draw( line );
            g.setColor( oldColor );
        }

        public void setLocation( int xLocation ) {
            line.setRect( xLocation, 0, 1, 800 );
            setBoundsDirty();
            super.repaint();
        }
    }
}