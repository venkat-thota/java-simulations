
package com.aimxcel.abclearn.core.aimxcelcore.test;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;

public class TestRenderingSize {
    private JFrame frame;
    private TestPPath centeredOnRight;
    private AimxcelPCanvas aimxcelPCanvas;

    public TestRenderingSize() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        aimxcelPCanvas = new AimxcelPCanvas( new Dimension( 1000, 1000 ) );
        aimxcelPCanvas.addWorldChild( new TestPPath( 0, 0, 100, 100, Color.green ) );
        frame.setContentPane( aimxcelPCanvas );

        centeredOnRight = new TestPPath( 0, 0, 100, 100, Color.blue );
        aimxcelPCanvas.addWorldChild( centeredOnRight );
        centeredOnRight.addPropertyChangeListener( PNode.PROPERTY_TRANSFORM, new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                relayoutNode();
            }
        } );
        aimxcelPCanvas.addComponentListener( new ComponentListener() {
            public void componentHidden( ComponentEvent e ) {
            }

            public void componentMoved( ComponentEvent e ) {
            }

            public void componentResized( ComponentEvent e ) {
                relayoutNode();
            }

            public void componentShown( ComponentEvent e ) {
                relayoutNode();
            }
        } );
        relayoutNode();
    }

    private void relayoutNode() {
        Rectangle viewBounds = new Rectangle( aimxcelPCanvas.getWidth(), aimxcelPCanvas.getHeight() );
        Dimension2D dim = new PDimension( viewBounds.width, viewBounds.height );
        aimxcelPCanvas.getAimxcelRootNode().screenToWorld( dim );
        centeredOnRight.setPathTo( new Rectangle2D.Double( dim.getWidth() - 100, dim.getHeight() / 2 - 50, 100, 100 ) );
    }

    static class TestPPath extends PPath {
        public TestPPath( int x, int y, int w, int h, Color color ) {
            super( new Rectangle( x, y, w, h ) );
            setPaint( color );
        }
    }

    public static void main( String[] args ) {
        new TestRenderingSize().start();
    }

    private void start() {
        frame.setVisible( true );
    }
}
