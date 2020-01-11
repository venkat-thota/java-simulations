
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;

public class TreeOrderExample extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double SQUARE_SIZE = 100;

    public TreeOrderExample() {

        // red square
        PPath redSquare = new PPath( new Rectangle2D.Double( 100, 100, SQUARE_SIZE, SQUARE_SIZE ) ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
                setPaint( Color.RED );
            }

            // draws a green square after drawing children
            @Override protected void paintAfterChildren( final PPaintContext paintContext ) {
                System.out.println( "paintAfterChildren" );
                super.paintAfterChildren( paintContext );
                Rectangle2D rectangle = new Rectangle2D.Double( 125, 125, SQUARE_SIZE, SQUARE_SIZE );
                Graphics2D g2 = paintContext.getGraphics();
                g2.setPaint( Color.GREEN );
                g2.fill( rectangle );
                g2.setPaint( Color.BLACK );
                g2.draw( rectangle );
            }
        };

        // blue square
        PPath blueSquare = new PPath( new Rectangle2D.Double( 150, 150, SQUARE_SIZE, SQUARE_SIZE ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( Color.BLUE );
        }};

        // yellow square
        PPath yellowSquare = new PPath( new Rectangle2D.Double( 175, 175, SQUARE_SIZE, SQUARE_SIZE ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( Color.YELLOW );
        }};

        // canvas
        PCanvas canvas = new PCanvas() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPreferredSize( new Dimension( 400, 400 ) );
        }};

        // rendering order: red, blue, yellow, green
        canvas.getLayer().addChild( redSquare );
        redSquare.addChild( blueSquare );
        redSquare.addChild( yellowSquare );

        setContentPane( canvas );
        pack();
    }

    public static void main( String[] args ) {
        new TreeOrderExample() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        }}.setVisible( true );
    }
}

