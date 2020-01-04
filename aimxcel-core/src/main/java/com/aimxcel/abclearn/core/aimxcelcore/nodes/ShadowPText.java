
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;


public class ShadowPText extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PText foreground;
    private PText background;

    public ShadowPText() {
        this( "" );
    }

    public ShadowPText( String text ) {
        foreground = new PText( text );
        background = new PText( text );
        addChild( background );
        addChild( foreground );
        background.setOffset( 1, 1 );
    }

    public ShadowPText( String text, Color foreground ) {
        this( text );
        setTextPaint( foreground );
    }

    public ShadowPText( String text, Color foreground, Font font ) {
        this( text, foreground );
        setFont( font );
    }

    public void setTextPaint( Paint paint ) {
        foreground.setTextPaint( paint );
    }

    public void setFont( Font font ) {
        foreground.setFont( font );
        background.setFont( font );
    }

    public void setText( String s ) {
        if ( !s.equals( foreground.getText() ) ) {
            foreground.setText( s );
            background.setText( s );
        }
    }

    public void setShadowOffset( double dx, double dy ) {
        background.setOffset( dx, dy );
    }

    public void setShadowColor( Color color ) {
        background.setTextPaint( color );
    }
}