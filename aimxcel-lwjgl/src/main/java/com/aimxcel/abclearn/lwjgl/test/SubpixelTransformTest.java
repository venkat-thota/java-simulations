package com.aimxcel.abclearn.lwjgl.test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Core3DCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class SubpixelTransformTest {

    public static AffineTransform transform = new AffineTransform();

    public static void main( String[] args ) {
        final JFrame frame = new JFrame( "SubpixelTransformText" );
        frame.setContentPane( new JPanel( new GridBagLayout() ) {{
            setBackground( Color.RED );
            final Core3DCanvas canvas = new Core3DCanvas( new PNode() {{
                addChild( new AimxcelPPath( new java.awt.geom.Ellipse2D.Double( 75, 75, 50, 50 ), Color.BLUE, new BasicStroke( 1 ), Color.BLACK ) );
            }} ) {
                @Override public void paint( Graphics g ) {
                    Graphics2D gg = (Graphics2D) g;

                    AffineTransform oldTransform = gg.getTransform();
                    gg.transform( transform );

                    super.paint( g );

                    gg.setTransform( oldTransform );
                }

                {
                    setPreferredSize( new Dimension( 200, 200 ) );
                    addMouseMotionListener( new MouseMotionListener() {
                        public void mouseDragged( MouseEvent e ) {
                        }

                        public void mouseMoved( final MouseEvent e ) {
                            transform = new AffineTransform() {{
                                final double tx = ( (double) e.getX() ) / 200;
                                final double ty = ( (double) e.getY() ) / 200;
//                                System.out.println( tx + ", " + ty );
                                translate( tx, ty );
                                scale( 13, 13 );
                            }};
                            invalidate();
                            repaint();
                            System.out.println( transform );
                        }
                    } );
                }
            };
            add( canvas, new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }} );
        frame.pack();
        frame.setSize( new Dimension( 1200, 600 ) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
