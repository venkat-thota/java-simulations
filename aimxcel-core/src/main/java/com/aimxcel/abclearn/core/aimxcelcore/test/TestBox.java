
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PText;


public class TestBox extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestBox() {
        final VBox boxNode = new VBox( 1, VBox.LEFT_ALIGNED ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            // create some components, to be initialized (for example) by observing properties
            PText one = new PText();
            PText two = new PText();
            PText three = new PText();
            // add the nodes in their desired top-to-bottom order in the VBox
            addChild( one );
            addChild( two );
            addChild( three );
            // simulate what would happen when (for example) text is initialized when registering property observers
            one.setText( "one" );
            two.setText( "two" );
            three.setText( "three" );
            setOffset( 50, 50 );
        }};
        PCanvas canvas = new PCanvas() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPreferredSize( new Dimension( 200, 200 ) );
            getLayer().addChild( boxNode );
        }};
        setContentPane( canvas );
        pack();
    }

    public static void main( String[] args ) {
        JFrame frame = new TestBox() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        }};
        frame.setVisible( true );
    }
}
