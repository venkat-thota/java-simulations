

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestPSwingCursors extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Cursor HAND_CURSOR = Cursor.getPredefinedCursor( Cursor.HAND_CURSOR );

    public TestPSwingCursors() {

        // JButton off the canvas.
        // This demonstrates typical Swing cursor behavior, when Core isn't involved.
        JButton button1 = new JButton( "button1 with hand cursor" );
        button1.setCursor( HAND_CURSOR );
        JPanel controlPanel = new JPanel();
        controlPanel.add( button1 );

        // JButton directly on canvas.
        // We could workaround by adding a CursorHandler to button1Wrapper.
        JButton button2 = new JButton( "button2 with hand cursor" );
        button2.setCursor( HAND_CURSOR );
        PSwing button1Wrapper = new PSwing( button2 );
        button1Wrapper.setOffset( 50, 50 );

        // JButton inside a JPanel on the canvas.
        // No workaround, adding a CursorHandler to panelWrapper affects the entire panel.
        JButton button3 = new JButton( "button3 with hand cursor" );
        button3.setCursor( HAND_CURSOR );
        JLabel noCursorLabel = new JLabel( "no cursor on this label" );
        JPanel panel = new VerticalLayoutPanel();
        panel.setOpaque( false );
        panel.setBorder( new TitledBorder( "Panel" ) );
        panel.add( button3 );
        panel.add( noCursorLabel );
        PSwing panelWrapper = new PSwing( panel );
        panelWrapper.setOffset( 50, button1Wrapper.getFullBoundsReference().getMaxY() + 20 );

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.getLayer().addChild( button1Wrapper );
        canvas.getLayer().addChild( panelWrapper );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( canvas, BorderLayout.CENTER );
        mainPanel.add( controlPanel, BorderLayout.SOUTH );

        getContentPane().add( mainPanel );
    }

    public static void main( String args[] ) {
        TestPSwingCursors frame = new TestPSwingCursors();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( new Dimension( 400, 400 ) );
        frame.setVisible( true );
    }
}
