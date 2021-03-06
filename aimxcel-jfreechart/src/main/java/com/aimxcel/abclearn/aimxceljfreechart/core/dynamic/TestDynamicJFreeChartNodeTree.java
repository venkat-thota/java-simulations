
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class TestDynamicJFreeChartNodeTree extends TestDynamicJFreeChartNode {
    private PNode root;
    private boolean constructed = false;
    private PText text;

    public TestDynamicJFreeChartNodeTree() {
        getAimxcelPCanvas().removeScreenChild( getDynamicJFreeChartNode() );
        root = new AimxcelPPath( new Rectangle( 0, 0, 10, 10 ), Color.blue );

        root.addChild( getDynamicJFreeChartNode() );

        getAimxcelPCanvas().addScreenChild( root );
        getAimxcelPCanvas().addKeyListener( new KeyAdapter() {
            public void keyPressed( KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_PAGE_UP ) {
                    root.scale( 1.1 );
                }
                else if ( e.getKeyCode() == KeyEvent.VK_PAGE_DOWN ) {
                    root.scale( 1.0 / 1.1 );
                }
            }
        } );
        constructed = true;

        text = new PText( "Page Up/Down to scale" );
        getAimxcelPCanvas().addScreenChild( text );
    }

    protected void relayout() {
        super.relayout();
        if ( constructed ) {
            root.setOffset( 50, 50 );
            text.setOffset( 0, super.getPSwing().getFullBounds().getMaxY() );
            getDynamicJFreeChartNode().setOffset( 10, 10 );
            getDynamicJFreeChartNode().setBounds( 0, 0, 500, 500 );
        }
    }

    public static void main( String[] args ) {
        new TestDynamicJFreeChartNodeTree().start();
    }
}
