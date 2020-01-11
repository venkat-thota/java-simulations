
package com.aimxcel.abclearn.core.aimxcelcore.test.debug_piccolo2d_1_3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;


public class DebugPSwingExtremeScaling extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// An extreme scale, similar to what's used in ScaleNode in eating-and-exercise.
    private static final double PSWING_SCALE = 0.02;

    public DebugPSwingExtremeScaling() {
        setSize( new Dimension( 1024, 768 ) );

        // canvas
        final PCanvas canvas = new PSwingCanvas();
        canvas.setBorder( new LineBorder( Color.BLACK ) );
        canvas.removeInputEventListener( canvas.getZoomEventHandler() );
        canvas.removeInputEventListener( canvas.getPanEventHandler() );
        setContentPane( canvas );

        // root node, uses the inverse of our extreme scale (PSWING_SCALE)
        PNode rootNode = new PNode();
        rootNode.setScale( 1.0 / PSWING_SCALE );
        canvas.getLayer().addChild( rootNode );

        // PSwing panel with some radio buttons
        PSwing panelNode = new PSwing( new UnitsPanel() );
        rootNode.addChild( panelNode );
        panelNode.setScale( PSWING_SCALE );
        panelNode.setOffset( 1, 1 );
    }

    /*
    * A JPanel with English/Metric radio buttons.
    */
    private static class UnitsPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnitsPanel() {
            setBorder( new LineBorder( Color.BLACK ) );

            final JRadioButton englishButton = new JRadioButton( "English" );
            englishButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    System.out.println( "actionPerformed " + englishButton.getText() );
                }
            } );

            final JRadioButton metricButton = new JRadioButton( "Metric" );
            metricButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    System.out.println( "actionPerformed " + metricButton.getText() );
                }
            } );

            ButtonGroup group = new ButtonGroup();
            group.add( englishButton );
            group.add( metricButton );

            add( englishButton );
            add( metricButton );

            // default state
            englishButton.setSelected( true );
        }
    }

    public static void main( String[] args ) {
        JFrame frame = new DebugPSwingExtremeScaling();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        // center on the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        int x = (int) ( tk.getScreenSize().getWidth() / 2 - frame.getWidth() / 2 );
        int y = (int) ( tk.getScreenSize().getHeight() / 2 - frame.getHeight() / 2 );
        frame.setLocation( x, y );
        frame.setVisible( true );
    }
}
