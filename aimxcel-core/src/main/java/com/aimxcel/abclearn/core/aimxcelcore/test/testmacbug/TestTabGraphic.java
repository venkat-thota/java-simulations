
package com.aimxcel.abclearn.core.aimxcelcore.test.testmacbug;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JFrame;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PPaintContext;


public class TestTabGraphic {

    private static final Object VALUE_RENDER = RenderingHints.VALUE_RENDER_QUALITY; 
    public static void main( String[] args ) {
        JFrame frame = new JFrame();
        PCanvas pCanvas = new PCanvas();

        PPath tabNode = new PPath( new Rectangle( 20, 20 ) ) {
            protected void paint( PPaintContext paintContext ) {
                Object orig = paintContext.getGraphics().getRenderingHint( RenderingHints.KEY_RENDERING );
                paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_RENDERING, VALUE_RENDER );
                super.paint( paintContext );
                if ( orig != null ) {
                    paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_RENDERING, orig );
                }
            }
        };
        tabNode.setPaint( new GradientPaint( 0, 0, Color.blue, 0, 10, Color.blue ) );

        pCanvas.getLayer().addChild( tabNode );
        frame.setContentPane( pCanvas );
        frame.setSize( 200, 200 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.setVisible( true );
    }

}
