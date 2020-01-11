
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestPSwingContainerResize extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean WORKAROUND_ENABLED = true;

    public TestPSwingContainerResize() {

        /*
        * Swing control panel embedded in a Core canvas using PSwing.
        * The red check box will have it's label changed dynamically.
        */
        final DynamicPanel playAreaPanel = new DynamicPanel();
        final PSwing wrapperNode = new PSwing( playAreaPanel );
        final AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 600, 400 ) );
        canvas.getLayer().addChild( wrapperNode );
        wrapperNode.setOffset( 100, 100 );

        /*
        * Swing control panel outside of Core.
        * The red check box will have it's label changed dynamically.
        */
        final DynamicPanel dynamicSwingPanel = new DynamicPanel();

        /*
        * Swing control panel outside of Core.
        * Selecting one of the radio buttons changes the label on the
        * red check box in the play area.
        */
        JPanel controlPanel = new JPanel();
        {
            controlPanel.setLayout( new BoxLayout( controlPanel, BoxLayout.Y_AXIS ) );
            TitledBorder controlPanelBorder = new TitledBorder( new LineBorder( Color.BLACK, 1 ), "Set the red check box label to:" );
            controlPanelBorder.setTitleColor( Color.RED );
            controlPanel.setBorder( controlPanelBorder );

            ActionListener listener = new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    if ( e.getSource() instanceof JRadioButton ) {
                        playAreaPanel.setDynamicLabel( ( (JRadioButton) e.getSource() ).getText() );
                        dynamicSwingPanel.setDynamicLabel( ( (JRadioButton) e.getSource() ).getText() );
                        if ( WORKAROUND_ENABLED ) {
                            wrapperNode.updateBounds();
                        }
                    }
                }
            };

            JRadioButton rb1 = new JRadioButton( "<html>short label</html>" );
            rb1.addActionListener( listener );
            controlPanel.add( rb1 );

            JRadioButton rb2 = new JRadioButton( "<html>a much much much much longer label</html>" );
            rb2.addActionListener( listener );
            controlPanel.add( rb2 );

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add( rb1 );
            buttonGroup.add( rb2 );
            rb1.setSelected( true );
            playAreaPanel.setDynamicLabel( rb1.getText() );
            dynamicSwingPanel.setDynamicLabel( rb1.getText() );
        }

        /*
        * Frame layout.
        */
        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( controlPanel, BorderLayout.EAST );
        mainPanel.add( dynamicSwingPanel, BorderLayout.WEST );
        mainPanel.add( canvas, BorderLayout.CENTER );

        setContentPane( mainPanel );
        pack();
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
    }

    /*
    * The top check box in this panel will have its label changed.
    */
    private static class DynamicPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final AbstractButton dynamicButton;

        public DynamicPanel() {
            setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
            TitledBorder border = new TitledBorder( new LineBorder( Color.BLACK, 1 ), "Dynamic panel" );
            setBorder( border );
            dynamicButton = new JCheckBox();
            dynamicButton.setForeground( Color.RED );
            add( dynamicButton );
            add( new JCheckBox( "red" ) );
            add( new JCheckBox( "blue" ) );
            add( new JCheckBox( "green" ) );


            // test ComponentListener
            this.addComponentListener( new ComponentAdapter() {
                public void componentResized( ComponentEvent e ) {
                    System.out.println( "DynamicPanel.componentResized" );
                }
            } );
            dynamicButton.addComponentListener( new ComponentAdapter() {
                public void componentResized( ComponentEvent e ) {
                    System.out.println( "JCheckBox.componentResized" );
                }
            } );
        }

        public void setDynamicLabel( String text ) {
            dynamicButton.setText( text );
        }
    }

    public static void main( String[] args ) {
        JFrame frame = new TestPSwingContainerResize();
        frame.setVisible( true );
    }

}
