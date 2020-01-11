
package com.aimxcel.abclearn.motion.graphs;

import java.awt.Color;
import java.awt.Font;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowPText;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class GraphControlSeriesNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShadowPText shadowPText;
    private PSwing textBox;
    private GraphControlTextBox boxGraphControl;
    public static final Font LABEL_FONT = new AimxcelFont( Font.BOLD, 18 );

    public GraphControlSeriesNode( ControlGraphSeries series ) {
        shadowPText = new ShadowPText( series.getTitle() );
        shadowPText.setFont( LABEL_FONT );
        shadowPText.setTextPaint( series.getColor() );
        shadowPText.setShadowColor( Color.black );
        addChild( shadowPText );

        boxGraphControl = createGraphControlTextBox( series );
        textBox = new PSwing( boxGraphControl );
        addChild( textBox );
    }

    public void addListener( GraphControlTextBox.Listener listener ) {
        boxGraphControl.addListener( listener );
    }

    protected GraphControlTextBox createGraphControlTextBox( ControlGraphSeries series ) {
        return new GraphControlTextBox( series );
    }

    public void relayout( double dy ) {
        shadowPText.setOffset( 0, 0 );
        textBox.setOffset( 0, shadowPText.getFullBounds().getMaxY() + dy );
    }

    public void setEditable( boolean editable ) {
        boxGraphControl.setEditable( editable );
    }

    public GraphControlTextBox getTextBox() {
        return boxGraphControl;
    }
}
