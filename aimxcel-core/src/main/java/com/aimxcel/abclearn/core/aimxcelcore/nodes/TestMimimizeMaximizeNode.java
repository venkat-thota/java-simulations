

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class TestMimimizeMaximizeNode {

    public static void main( String[] args ) {

        PCanvas canvas = new PCanvas();

        // Test 1 -- button on right

        final PPath chartNode1 = new PPath( new Rectangle2D.Double( 0, 0, 200, 400 ) );
        chartNode1.setPaint( null );
        chartNode1.setStrokePaint( Color.RED );
        chartNode1.setStroke( new BasicStroke( 3f ) );
        canvas.getLayer().addChild( chartNode1 );
        chartNode1.setOffset( 50, 50 );

        final MinimizeMaximizeNode minMaxNode1 = new MinimizeMaximizeNode( new IUserComponent() {
        }, new IUserComponent() {
        }, "Show chart 1", MinimizeMaximizeNode.BUTTON_RIGHT
        );
        canvas.getLayer().addChild( minMaxNode1 );
        double x1 = chartNode1.getFullBounds().getMaxX() - minMaxNode1.getFullBounds().getWidth() - 10;
        double y1 = chartNode1.getOffset().getY() + 10;
        minMaxNode1.setOffset( x1, y1 );

        minMaxNode1.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                chartNode1.setVisible( minMaxNode1.isMaximized() );
            }
        } );

        minMaxNode1.setMinimized( true );
        chartNode1.setVisible( minMaxNode1.isMaximized() );

        // Test 2 -- button on left

        final PPath chartNode2 = new PPath( new Rectangle2D.Double( 0, 0, 200, 400 ) );
        chartNode2.setPaint( null );
        chartNode2.setStrokePaint( Color.RED );
        chartNode2.setStroke( new BasicStroke( 3f ) );
        canvas.getLayer().addChild( chartNode2 );
        chartNode2.setOffset( chartNode1.getFullBounds().getMaxX() + 20, chartNode1.getOffset().getY() );

        Font font = new AimxcelFont( Font.BOLD, 18 );
        double spacing = 10;
        final MinimizeMaximizeNode minMaxNode2 = new MinimizeMaximizeNode( new IUserComponent() {
        }, new IUserComponent() {
        }, "Show chart 2", MinimizeMaximizeNode.BUTTON_LEFT, font, Color.BLACK, spacing
        );
        canvas.getLayer().addChild( minMaxNode2 );
        double x2 = chartNode2.getOffset().getX() + 10;
        double y2 = chartNode2.getOffset().getY() + 10;
        minMaxNode2.setOffset( x2, y2 );

        minMaxNode2.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                chartNode2.setVisible( minMaxNode2.isMaximized() );
            }
        } );

        minMaxNode2.setMinimized( true );
        chartNode2.setVisible( minMaxNode2.isMaximized() );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( canvas );
        frame.setSize( 800, 600 );
        frame.setVisible( true );
    }
}
