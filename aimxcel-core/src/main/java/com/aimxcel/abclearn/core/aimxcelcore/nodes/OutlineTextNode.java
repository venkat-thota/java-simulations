
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.util.Locale;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;

public class OutlineTextNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FontRenderContext SWING_FRC = new FontRenderContext( null, true, false );
    private PPath textPPath;
    private Font font;

    /**
     * Constructor.
     *
     * @param text
     * @param font
     * @param fillColor
     * @param outlineColor
     * @param outlineStrokeWidth
     */
    public OutlineTextNode( String text, Font font, Color fillColor, Color outlineColor, double outlineStrokeWidth ) {
        this.font = font;
        textPPath = new AimxcelPPath( fillColor, new BasicStroke( (float) outlineStrokeWidth ), outlineColor );
        TextLayout textLayout = new TextLayout( text, font, SWING_FRC );
        textPPath.setPathTo( textLayout.getOutline( new AffineTransform() ) );
        addChild( new ZeroOffsetNode( textPPath ) ); // Make sure that this node's origin is in the upper left corner.
        textPPath.setPickable( false ); // #3313 temporary workaround; this class should be pickable, but not individual components
    }

    public void setText( String text ) {
        TextLayout textLayout = new TextLayout( text, font, SWING_FRC );
        textPPath.setPathTo( textLayout.getOutline( new AffineTransform() ) );
    }

    // Override of the fullPaint method to allow for different rendering hints, see #3337.
    @Override public void fullPaint( PPaintContext paintContext ) {

        // Save current rendering hints.
        RenderingHints oldRenderingHints = paintContext.getGraphics().getRenderingHints();

        // Change the rendering hints on the graphics for improved rendering.
        paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE );
        paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE );
        super.fullPaint( paintContext );

        // Restore previous hints.
        paintContext.getGraphics().setRenderingHints( oldRenderingHints );
    }

    /**
     * Test harness.
     *
     * @param args
     */
    public static void main( String[] args ) {

        final Dimension stageSize = new Dimension( 1024, 768 );
        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( stageSize );
        canvas.setWorldTransformStrategy( new AimxcelPCanvas.CenteredStage( canvas, stageSize ) );

        final double xMargin = 5;
        final double yMargin = 10;
        final double ySpacing = 5;
        final Color fillColor = Color.YELLOW;
        final Color strokeColor = Color.BLACK;
        final double strokeWidth = 1;
        final int smallestFontSize = 12; // display integer multiples of this font size

        // English, all letters (upper and lowercase) and numbers, in various font sizes
        PNode lastNode = null;
        for ( int i = 0; i < 5; i++ ) {

            AimxcelFont font = new AimxcelFont( Font.PLAIN, ( i + 1 ) * smallestFontSize );

            OutlineTextNode uppercaseNode = new OutlineTextNode( "ABCDEFGHIJKLMNOPQRSTUVWXYZ", font, fillColor, strokeColor, strokeWidth );
            uppercaseNode.setOffset( xMargin, ( lastNode == null ) ? yMargin : lastNode.getFullBoundsReference().getMaxY() + ySpacing );
            canvas.addWorldChild( uppercaseNode );

            OutlineTextNode lowercaseNode = new OutlineTextNode( "abcdefghijklmnopqrstuvwxyz", font, fillColor, strokeColor, strokeWidth );
            lowercaseNode.setOffset( xMargin, uppercaseNode.getFullBoundsReference().getMaxY() + ySpacing );
            canvas.addWorldChild( lowercaseNode );

            OutlineTextNode numbersNode = new OutlineTextNode( "0123456789", font, fillColor, strokeColor, strokeWidth );
            numbersNode.setOffset( xMargin, lowercaseNode.getFullBoundsReference().getMaxY() + ySpacing );
            canvas.addWorldChild( numbersNode );

            lastNode = numbersNode;
        }

        // Arabic
        OutlineTextNode outlineTextNode5 = new OutlineTextNode( "\uFE9D\u06B0\u06AA\uFEB5", AimxcelFont.getPreferredFont( new Locale( "ar" ), Font.PLAIN, 64 ), fillColor, strokeColor, strokeWidth );
        outlineTextNode5.setOffset( xMargin, lastNode.getFullBoundsReference().getMaxY() + ySpacing );
        canvas.addWorldChild( outlineTextNode5 );

        // Chinese
        OutlineTextNode outlineTextNode6 = new OutlineTextNode( "\u4e2d\u56fd\u8bdd\u4e0d", AimxcelFont.getPreferredFont( new Locale( "zh" ), Font.PLAIN, 64 ), fillColor, strokeColor, strokeWidth );
        outlineTextNode6.setOffset( xMargin, outlineTextNode5.getFullBoundsReference().getMaxY() + ySpacing );
        canvas.addWorldChild( outlineTextNode6 );

        // Boiler plate Core app stuff.
        JFrame frame = new JFrame( "OutlineTextNode.main" );
        frame.setContentPane( canvas );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null ); // Center.
        frame.setVisible( true );
    }
}
