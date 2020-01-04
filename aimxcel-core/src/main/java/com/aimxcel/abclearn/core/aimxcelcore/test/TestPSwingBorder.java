
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import edu.umd.cs.piccolox.pswing.PSwing;
import edu.umd.cs.piccolox.pswing.PSwingCanvas;


public class TestPSwingBorder extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean APPLY_WORKAROUND = true;

    public TestPSwingBorder() {
        super( "TestPSwingBorder" );

        // PSwing
        PSwingCanvas canvas = new PSwingCanvas();
        PSwing pswing = new PSwing( new BorderedLabel( "PSwing" ) );
        pswing.scale( 1.5 );
        final double margin = 20;
        pswing.setOffset( margin, margin );
        canvas.getLayer().addChild( pswing );
        canvas.setPreferredSize( new Dimension( (int) ( pswing.getFullBounds().getWidth() + ( 2 * margin ) ), (int) ( pswing.getFullBounds().getHeight() + ( 2 * margin ) ) ) );

        // pure Swing
        JPanel swingPanel = new JPanel();
        swingPanel.add( new BorderedLabel( "Swing" ) );

        // layout
        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( canvas, BorderLayout.CENTER );
        mainPanel.add( swingPanel, BorderLayout.SOUTH );
        setContentPane( mainPanel );

        pack();
    }

    private static class BorderedLabel extends JLabel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BorderedLabel( String text ) {
            super( text );
            setFont( new Font( "Default", Font.PLAIN, 24 ) );
            if ( APPLY_WORKAROUND ) {
                setBorder( new MatteBorder( 10, 10, 10, 10, Color.BLUE ) );
            }
            else {
                setBorder( new LineBorder( Color.BLUE, 10 ) );
            }
        }
    }

    public static void main( String[] args ) {
        JFrame frame = new TestPSwingBorder();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
