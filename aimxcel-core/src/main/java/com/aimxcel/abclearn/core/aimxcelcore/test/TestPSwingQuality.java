
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;


public class TestPSwingQuality extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Use opaque JComponents */
    private static int SET_OPAQUE_TRUE = 1;
    /* Use transparent JComponents using setOpaque(false) */
    private static int SET_OPAQUE_FALSE = 2;
    /* Use transparent JComponents using setBackground(new Color(0,0,0,0)) */
    private static int SET_BACKGROUND_TRANSPARENT = 3;

    public static void main( String[] args ) {
        JFrame frame = new TestPSwingQuality();
        frame.show();
    }

    public TestPSwingQuality() {
        super( "TestPSwingQuality" );

        JPanel opaquePanel = new TestPanel( "setOpaque(true)", SET_OPAQUE_TRUE );
        JPanel transparentPanel = new TestPanel( "setOpaque(false)", SET_OPAQUE_FALSE );
        JPanel backgroundPanel = new TestPanel( "setBackground(transparent)", SET_BACKGROUND_TRANSPARENT );

        PSwingCanvas canvas = new PSwingCanvas();

        PNode opaqueNode = new PSwing( opaquePanel );
        canvas.getLayer().addChild( opaqueNode );

        PNode transparentNode = new PSwing( transparentPanel );
        canvas.getLayer().addChild( transparentNode );

        PNode backgroundNode = new PSwing( backgroundPanel );
        canvas.getLayer().addChild( backgroundNode );

        opaqueNode.setOffset( 50, 50 );
        transparentNode.setOffset( opaqueNode.getFullBounds().getX(),
                                   opaqueNode.getFullBounds().getY() + opaqueNode.getFullBounds().getHeight() );
        backgroundNode.setOffset( transparentNode.getFullBounds().getX(),
                                  transparentNode.getFullBounds().getY() + transparentNode.getFullBounds().getHeight() );

        getContentPane().add( canvas );
        setSize( 300, 300 );
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
    }

    private static class TestPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TestPanel( String title, int type ) {

            JRadioButton rb1 = new JRadioButton( "ABC XYZ 123" );
            JRadioButton rb2 = new JRadioButton( "abc xyz 456" );
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add( rb1 );
            buttonGroup.add( rb2 );
            rb1.setSelected( true );

            setBorder( BorderFactory.createTitledBorder( title ) );
            add( rb1 );
            add( rb2 );

            if ( type == SET_OPAQUE_TRUE ) {
                // do nothing
            }
            else if ( type == SET_OPAQUE_FALSE ) {
                setOpaque( false );
                rb1.setOpaque( false );
                rb2.setOpaque( false );
            }
            else if ( type == SET_BACKGROUND_TRANSPARENT ) {
                Color transparentColor = new Color( 0f, 0f, 0f, 0f );
                setBackground( transparentColor );
                rb1.setBackground( transparentColor );
                rb2.setBackground( transparentColor );
            }
        }
    }
}
