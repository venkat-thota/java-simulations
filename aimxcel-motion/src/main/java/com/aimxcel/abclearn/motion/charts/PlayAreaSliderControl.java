
package com.aimxcel.abclearn.motion.charts;

import java.awt.Color;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowPText;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class PlayAreaSliderControl extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MotionSliderNode slider;
    private TextBox textBox;
    final DefaultDecimalFormat decimalFormat = new DefaultDecimalFormat( "0.00" );

    public PlayAreaSliderControl( double min, double max, double value, String title, String units, Color color, TextBox textBox ) {
        this.textBox = textBox;
        ShadowPText text = new ShadowPText( title );
        text.setTextPaint( color );
        text.setFont( new AimxcelFont( 20, true ) );
        addChild( text );
        addChild( textBox );
        textBox.setOffset( 200, text.getFullBounds().getCenterY() - textBox.getFullBounds().getHeight() / 2 );//todo: align with other controls but make sure doesn't overlap text

        try {
            slider = new MotionSliderNode.Horizontal( new Range( min, max ), 0.0, new Range( 0, 350 ), color );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        addChild( slider );
        slider.setOffset( 0, text.getFullBounds().getHeight() + 10 );

        HTMLNode unitsPText = new HTMLNode( units );//Use HTMLNode for support for provided translations, which use HTML
        unitsPText.setHTMLColor( color );

        unitsPText.setFont( new AimxcelFont( 20, true ) );
        addChild( unitsPText );
        unitsPText.setOffset( textBox.getFullBounds().getMaxX() + 2, 0 );

        setValue( value );
    }

    public void setValue( double value ) {
        slider.setValue( value );
        textBox.setText( decimalFormat.format( value ) );
    }

    public void addListener( MotionSliderNode.Listener listener ) {
        slider.addListener( listener );
    }

    public double getValue() {
        return slider.getValue();
    }

    public void setHighlighted( boolean positionDriven ) {
        slider.setHighlighted( positionDriven );
    }
}
