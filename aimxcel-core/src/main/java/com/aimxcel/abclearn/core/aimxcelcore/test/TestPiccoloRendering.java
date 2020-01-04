

/*  */
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PPaintContext;


public class TestPiccoloRendering {

    public static void main( String[] args ) {
        PCanvas pCanvas = new PCanvas();
        pCanvas.setPanEventHandler( null );

        final PText pText = new PText( "Testing Piccolo Rendering" );
        pText.setFont( new AimxcelFont( Font.BOLD, 32 ) );
        pText.setOffset( 22.96045684814453, 19.954608917236328 );
        pCanvas.getLayer().addChild( pText );

        PPath path = new PPath( new Rectangle( 50, 50 ) );
        path.setPaint( new Color( 0, 0, 0, 0 ) );
        path.addInputEventListener( new PDragEventHandler() );
        pCanvas.getLayer().addChild( path );

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( pCanvas );
        frame.setSize( 400, 600 );
        frame.setVisible( true );

        pCanvas.setDefaultRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        pCanvas.setInteractingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        pCanvas.setAnimatingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
    }
}
