
package com.aimxcel.abclearn.core.aimxcelcore.test.debug_piccolo2d_1_3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;


public class DebugAddPSwing extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DebugAddPSwing() {
        setResizable( false );
        setSize( new Dimension( 400, 400 ) );

        // canvas
        final PCanvas canvas = new PSwingCanvas();
        canvas.setBorder( new LineBorder( Color.BLACK ) );
        canvas.removeInputEventListener( canvas.getZoomEventHandler() );
        canvas.removeInputEventListener( canvas.getPanEventHandler() );

        // JTextField
        JTextField textField = new JTextField( "edit me" );
        textField.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                System.out.println( "mousePressed in JTextField" );
            }
        } );
        textField.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e ) {
                System.out.println( "keyPressed in JTextField" );
            }
        } );

        // PSwing wrapper for the JTextField
        final PSwing textFieldNode = new PSwing( textField );
        textFieldNode.addInputEventListener( new PBasicInputEventHandler() {
            @Override
            public void mousePressed( final PInputEvent event ) {
                System.out.println( "mousePressed in PSwing" );
            }

            @Override
            public void keyPressed( final PInputEvent event ) {
                System.out.println( "keyPressed in PSwing" );
            }
        } );
        textFieldNode.setOffset( 100, 200 );

        // JCheckBox for setting whether text field is in the scenegraph.
        final JCheckBox checkBox = new JCheckBox( "add JTextField to scenegraph" );
        checkBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( checkBox.isSelected() ) {
                    canvas.getLayer().addChild( textFieldNode );
                }
                else {
                    canvas.getLayer().removeChild( textFieldNode );
                }
            }
        } );

        // PSwing wrapper for the JCheckBox
        PSwing checkBoxNode = new PSwing( checkBox );
        checkBoxNode.setOffset( 100, 100 );
        canvas.getLayer().addChild( checkBoxNode );

        setContentPane( canvas );
    }

    public static void main( String[] args ) {
        JFrame frame = new DebugAddPSwing();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        // center on the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        int x = (int) ( tk.getScreenSize().getWidth() / 2 - frame.getWidth() / 2 );
        int y = (int) ( tk.getScreenSize().getHeight() / 2 - frame.getHeight() / 2 );
        frame.setLocation( x, y );
        frame.setVisible( true );
    }
}
