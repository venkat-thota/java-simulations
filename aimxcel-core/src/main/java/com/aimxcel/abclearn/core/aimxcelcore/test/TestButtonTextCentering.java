
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PDimension;

public class TestButtonTextCentering {

    private static final Dimension2D STAGE_SIZE = new PDimension( 1008, 679 );

    public static void test1() {

        PDebug.debugBounds = false;
        ActionListener listener = new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                System.out.println( "actionPerformed event= " + event );
            }
        };

        HTMLImageButtonNode button1 = new HTMLImageButtonNode( "xxx", new AimxcelFont( Font.BOLD, 18 ), Color.ORANGE );
        button1.setOffset( 500, 500 );
        button1.addActionListener( listener );

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setWorldTransformStrategy( new AimxcelPCanvas.CenteredStage( canvas, STAGE_SIZE ) );
        canvas.addWorldChild( button1 );

        JFrame frame = new JFrame( HTMLImageButtonNode.class.getName() );
        frame.setContentPane( canvas );
        frame.setSize( (int) STAGE_SIZE.getWidth(), (int) STAGE_SIZE.getHeight() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }

    public static void test2() {
        JFrame frame = new JFrame( HTMLImageButtonNode.class.getName() );
        frame.setContentPane( new JPanel() {
            @Override protected void paintComponent( Graphics g ) {
                super.paintComponent( g );
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont( new AimxcelFont( Font.BOLD, 18 ) );
                g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
                String text = "Zorg Foury";
                Rectangle2D stringBounds = g2.getFontMetrics().getStringBounds( text, g2 );
                g2.draw( new Rectangle2D.Double( 100, 100 - stringBounds.getHeight() + g2.getFontMetrics().getDescent(), stringBounds.getWidth(), stringBounds.getHeight() ) );
                g2.drawString( text, 100, 100 );
            }
        } );
        frame.setSize( 250, 200 );
        frame.setVisible( true );
    }

    public static void test3() {
        PDebug.debugBounds = true;

        PCanvas canvas = new PCanvas();
        String text = "This is a <br>test.";
        final Font font = new AimxcelFont( 18, true );
        HTMLNode htmlNode = new HTMLNode( text ) {{
            setFont( font );
            setOffset( 20, 20 );
        }};
        canvas.getLayer().addChild( htmlNode );
        PText textNode = new PText( text ) {{
            setFont( font );
            setOffset( 20, 100 );
        }};
        canvas.getLayer().addChild( textNode );

        HTMLImageButtonNode button1 = new HTMLImageButtonNode( "xxx", new AimxcelFont( Font.BOLD, 18 ), Color.ORANGE );
        button1.setOffset( 100, 200 );
        canvas.getLayer().addChild( button1 );


        JFrame frame = new JFrame( HTMLImageButtonNode.class.getName() );
        frame.setContentPane( canvas );
        frame.setSize( (int) STAGE_SIZE.getWidth(), (int) STAGE_SIZE.getHeight() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );


    }

    public static void main( String[] args ) {
        test3();
    }
}
