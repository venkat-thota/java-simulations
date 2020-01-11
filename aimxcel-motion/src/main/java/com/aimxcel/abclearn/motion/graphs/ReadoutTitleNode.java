
package com.aimxcel.abclearn.motion.graphs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowHTMLNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowPText;
import com.aimxcel.abclearn.motion.model.IVariable;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class ReadoutTitleNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShadowHTMLNode titleNode;
    private ShadowPText valueNode;
    private ShadowHTMLNode unitsNode;

    private ControlGraphSeries series;
    private DecimalFormat decimalFormat;
    private AimxcelPPath background;
    private double insetX = 2;
    private double insetY = 2;

    public static interface ITextNode {

        void setFont( Font titleFont );

        void setTextPaint( Color color );

        void setShadowColor( Color color );

        void setText( String valueText );
    }

    public static class OutlinePText extends PNode implements ITextNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String text;
        private Font font;
        private FontRenderContext fontRenderContext;
        private AimxcelPPath render;

        public OutlinePText( AimxcelPPath render, String text, Font font, FontRenderContext fontRenderContext ) {
            this.render = render;
            this.text = text;
            this.font = font;
            this.fontRenderContext = fontRenderContext;
            addChild( render );
            update();
        }

        void update() {
            TextLayout textLayout = new TextLayout( text, font, fontRenderContext );
            render.setPathTo( textLayout.getOutline( new AffineTransform() ) );
        }

        public void setFont( Font titleFont ) {
            this.font = titleFont;
            update();
        }

        public void setTextPaint( Color color ) {
            render.setPaint( color );
        }

        public void setShadowColor( Color color ) {
            render.setStrokePaint( color );
        }

        public void setText( String valueText ) {
            this.text = valueText;
            update();
        }
    }

    public ReadoutTitleNode( final ControlGraphSeries series ) {
        this.series = series;
        this.decimalFormat = series.getNumberFormat();

        titleNode = new ShadowHTMLNode();
        titleNode.setFont( getTitleFont() );
        titleNode.setColor( series.getColor() );

//        valueNode = new OutlinePText( new AimxcelPPath( series.getColor(), new BasicStroke( 1.2f ), Color.black ), "0.00", new AimxcelDefaultFont(), new FontRenderContext( new AffineTransform(), true, false ) );
        valueNode = new ShadowPText();
        valueNode.setFont( getTitleFont() );
        valueNode.setTextPaint( series.getColor() );

        unitsNode = new ShadowHTMLNode( series.getUnits() );
        unitsNode.setFont( getTitleFont() );
        unitsNode.setColor( series.getColor() );

        if ( isLowRes() ) {
            titleNode.setShadowColor( new Color( 255, 255, 255, 255 ) );
            valueNode.setShadowColor( new Color( 255, 255, 255, 255 ) );
            unitsNode.setShadowColor( new Color( 255, 255, 255, 255 ) );
        }

        background = new AimxcelPPath( Color.white );
        addChild( background );
        addChild( titleNode );
        addChild( valueNode );
        addChild( unitsNode );
        background.translate( insetX, insetY );
        titleNode.translate( insetX, insetY );
        series.getTemporalVariable().addListener( new IVariable.Listener() {
            public void valueChanged() {
                updateText();
            }
        } );
        series.addListener( new ControlGraphSeries.Adapter() {
            public void unitsChanged() {
                unitsNode.setHtml( series.getUnits() );
                updateText();

            }
        } );

        if ( series.getCharacterName() != null ) {
            titleNode.setHtml( "<html>" + series.getAbbr() + "<sub>" + series.getCharacterName() + "</sub>= " );
        }
        else {
            titleNode.setHtml( series.getAbbr() + "= " );
        }

        valueNode.setOffset( titleNode.getFullBounds().getWidth() + 3, 3 );

//        ((PNode)valueNode).setOffset( titleNode.getFullBounds().getWidth() + 3, ( (PNode) valueNode ).getFullBounds().getHeight()*1.5);
        updateText();
    }

    private Font getTitleFont() {
        return new Font( AimxcelFont.getDefaultFontName(), Font.BOLD, isLowRes() ? 12 : 14 );
    }

    private boolean isLowRes() {
        return Toolkit.getDefaultToolkit().getScreenSize().width <= 1024;
    }

    public ControlGraphSeries getSeries() {
        return series;
    }

    protected void updateText() {
        setValueText( decimalFormat.format( getValueToDisplay() ) );
    }

    private void setValueText( String valueText ) {
        valueNode.setText( valueText );
        double maxY = valueNode.getFullBounds().getMaxY();
        unitsNode.setOffset( valueNode.getFullBounds().getMaxX() + 3, maxY - unitsNode.getFullBounds().getHeight() );
        background.setPathTo( RectangleUtils.expand( titleNode.getFullBounds().createUnion( unitsNode.getFullBounds() ), insetX, insetY ) );//todo: avoid setting identical shapes here for performance considerations
    }

    public double getPreferredWidth() {
        setValueText( "MMM.MM" );
        double width = getFullBounds().getWidth();
        updateText();
        return width;
    }

    protected double getValueToDisplay() {
        return series.getTemporalVariable().getValue();
    }

}
