
package com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelcomponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;

public class AimxcelButton extends CompositeAimxcelGraphic {
    private String text;
    private AimxcelTextGraphic textGraphic;
    private AimxcelShapeGraphic backgroundGraphic;
    private ArrayList listeners = new ArrayList();
    private Color backgroundColor = Color.lightGray;
    private Color textColor = Color.black;
    private Color clickColor = Color.gray;
    private Color borderColor = Color.black;
    private Stroke borderStroke = new BasicStroke( 2.0f );
    private Font font = new AimxcelFont( Font.BOLD, 18 );

    public AimxcelButton( Component component, String text ) {
        super( component );
        this.text = text;
        this.textGraphic = new AimxcelTextGraphic( component, font, text, textColor, 0, 0 );
        this.backgroundGraphic = new AimxcelShapeGraphic( component, null, backgroundColor, borderStroke, borderColor );
        addMouseInputListener( new MouseInputAdapter() {
            public void mousePressed( MouseEvent e ) {
                backgroundGraphic.setColor( clickColor );
            }

            public void mouseReleased( MouseEvent e ) {
                fireEvent();
                backgroundGraphic.setColor( backgroundColor );
            }

            public void mouseEntered( MouseEvent e ) {
                backgroundGraphic.setBorderColor( Color.white );
            }

            public void mouseExited( MouseEvent e ) {
                backgroundGraphic.setBorderColor( borderColor );
            }
        } );
        addGraphic( backgroundGraphic );
        addGraphic( textGraphic );
        setCursorHand();
        update();
    }

    static int eventID = 0;

    private void fireEvent() {
        ActionEvent event = new ActionEvent( this, eventID++, "AimxcelButtonPress" );
        for ( int i = 0; i < listeners.size(); i++ ) {
            ActionListener actionListener = (ActionListener) listeners.get( i );
            actionListener.actionPerformed( event );
        }
    }

    public void setText( String text ) {
        this.text = text;
        update();
    }

    private void update() {
        textGraphic.setText( text );
        textGraphic.setFont( font );
        textGraphic.setColor( textColor );

        backgroundGraphic.setBorderPaint( borderColor );
        backgroundGraphic.setColor( backgroundColor );
        backgroundGraphic.setStroke( borderStroke );

        Rectangle bounds = textGraphic.getLocalBounds();
        bounds = RectangleUtils.expand( bounds, 5, 5 );

        backgroundGraphic.setShape( bounds );
        setBoundsDirty();
        autorepaint();
    }

    public void setBackgroundColor( Color backgroundColor ) {
        this.backgroundColor = backgroundColor;
        update();
    }

    public void setTextColor( Color textColor ) {
        this.textColor = textColor;
        update();
    }

    public void setClickColor( Color clickColor ) {
        this.clickColor = clickColor;
        update();
    }

    public void setBorderColor( Color borderColor ) {
        this.borderColor = borderColor;
        update();
    }

    public void setBorderStroke( Stroke borderStroke ) {
        this.borderStroke = borderStroke;
        update();
    }

    public void setFont( Font font ) {
        this.font = font;
        update();
    }

    public void addActionListener( ActionListener actionListener ) {
        listeners.add( actionListener );
    }
}
