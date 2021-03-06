
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestDisableHTMLLabels extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestDisableHTMLLabels() {
        super();
        setResizable( false );

        // panel with controls on a canvas
        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 300, 400 ) );
        final JPanel pswingPanel = new MyPanel( "PSwing" );
        PSwing pswingWrapper = new PSwing( pswingPanel );
        canvas.getLayer().addChild( pswingWrapper );
        pswingWrapper.setOffset( 50, 50 );

        // panel with controls outside the canvas
        final JPanel swingPanel = new MyPanel( "Swing" );
        final JCheckBox enableCheckBox = new JCheckBox( "enable components" );
        enableCheckBox.setForeground( Color.RED );
        enableCheckBox.setSelected( true );
        enableCheckBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                pswingPanel.setEnabled( enableCheckBox.isSelected() );
                swingPanel.setEnabled( enableCheckBox.isSelected() );
            }
        } );
        JPanel controlPanel = new VerticalLayoutPanel();
        controlPanel.add( swingPanel );
        controlPanel.add( Box.createVerticalStrut( 20 ) );
        controlPanel.add( enableCheckBox );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( canvas, BorderLayout.CENTER );
        mainPanel.add( controlPanel, BorderLayout.EAST );

        getContentPane().add( mainPanel );
        pack();
    }

    /*
     * A panel with various JComponents.
     * Disabling the panel disables all of its child components.
     */
    public static class MyPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel( String title ) {
            super();
            setBorder( new TitledBorder( title ) );
            setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
            add( new JLabel( "plain label" ) );
            add( new JLabel( "<html>HTML label</html>" ) );
            add( new JSeparator() );
            add( new JCheckBox( "plain check box" ) );
            add( new JCheckBox( "<html>HTML check box</html>" ) );
            add( new JSeparator() );
            add( new JButton( "plain button" ) );
            add( new JButton( "<html>HTML button</html>" ) );
            add( new JSeparator() );
            add( new JRadioButton( "plain radio button" ) );
            add( new JRadioButton( "<html>HTML radio button</html>" ) );
        }

        public void setEnabled( boolean enabled ) {
            super.setEnabled( enabled );
            Component[] children = getComponents();
            for ( int i = 0; i < children.length; i++ ) {
                children[i].setEnabled( enabled );
            }
        }
    }

    public static void main( String[] args ) {
        JFrame frame = new TestDisableHTMLLabels();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }

}
