

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestPSwingTextFieldFocus extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestPSwingTextFieldFocus() {

        // JTextField off the canvas.
        // This demonstrates typical Swing cursor behavior, when Core isn't involved.
        final JTextField textField1 = new JTextField( "textField1 outside of Core" );
        textField1.setColumns( 20 );
        textField1.addFocusListener( new FocusListener() {
            public void focusGained( FocusEvent e ) {
                System.out.println( "textField1 focusGained" );
                textField1.selectAll();
            }

            public void focusLost( FocusEvent e ) {
                System.out.println( "textField1 focusLost" );
            }
        } );
        textField1.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.print( "textField1 actionPerformed" );
            }
        } );
        JPanel controlPanel = new JPanel();
        controlPanel.add( textField1 );

        // JTextField on the canvas.
        final JTextField textField2 = new JTextField( "textField2 on a PCanvas" );
        textField2.setColumns( 20 );
        textField2.addFocusListener( new FocusListener() {
            public void focusGained( FocusEvent e ) {
                System.out.println( "textField2 focusGained" );
                textField2.selectAll();
            }

            public void focusLost( FocusEvent e ) {
                System.out.println( "textField2 focusLost" );
            }
        } );
        textField2.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.print( "textField2 actionPerformed" );
            }
        } );
        PSwing textField2Wrapper = new PSwing( textField2 );
        textField2Wrapper.setOffset( 10, 50 );

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.getLayer().addChild( textField2Wrapper );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( canvas, BorderLayout.CENTER );
        mainPanel.add( controlPanel, BorderLayout.SOUTH );

        getContentPane().add( mainPanel );
    }

    public static void main( String args[] ) {
        TestPSwingTextFieldFocus frame = new TestPSwingTextFieldFocus();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( new Dimension( 400, 400 ) );
        frame.setVisible( true );
    }
}
