

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class FormattedNumberNode extends PComposite {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Font DEFAULT_FONT = new AimxcelFont();
    private static final Color DEFAULT_COLOR = Color.BLACK;

    private NumberFormat _format;
    private double _value;
    private final HTMLNode _htmlNode; // use HTMLNode to provide more formatting flexibility, eg, superscripts and subscripts

    public FormattedNumberNode( NumberFormat format ) {
        this( format, 0, DEFAULT_FONT, DEFAULT_COLOR );
    }

    public FormattedNumberNode( NumberFormat format, double value ) {
        this( format, value, DEFAULT_FONT, DEFAULT_COLOR );
    }

    public FormattedNumberNode( NumberFormat format, double value, Font font, Color textColor ) {
        _format = format;
        _value = value;
        _htmlNode = new HTMLNode( _format.format( value ) );
        _htmlNode.setFont( font );
        _htmlNode.setHTMLColor( textColor );
        addChild( _htmlNode );
    }

    public void setValue( double value ) {
        if ( value != _value ) {
            _value = value;
            _htmlNode.setHTML( _format.format( value ) );
        }
    }

    public double getValue() {
        return _value;
    }

    public void setFont( Font font ) {
        _htmlNode.setFont( font );
    }

    public Font getFont() {
        return _htmlNode.getFont();
    }

    public void setTextColor( Color color ) {
        _htmlNode.setHTMLColor( color );
    }

    public Color getTextColor() {
        return _htmlNode.getHTMLColor();
    }

    public void setFormat( NumberFormat format ) {
        _format = format;
        _htmlNode.setHTML( _format.format( _value ) );
    }
}
