package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

import javax.swing.Timer;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPText;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

class FlagNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Timer timer;

    public FlagNode( Color color, final String text ) {
        final AimxcelPPath p = new AimxcelPPath( createPath(), color, new BasicStroke( 2 ), Color.black );
        addChild( p );
        final AimxcelPText textNode = new AimxcelPText( text, new AimxcelFont( 32, true ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setTextPaint( Color.white );
        }};

        //Shrink to fit within the flag
        double width = textNode.getFullWidth();
        if ( width > 165 ) { textNode.scale( 165.0 / width ); }
        textNode.centerFullBoundsOnPoint( p.getFullBounds().getCenterX(), p.getFullBounds().getCenterY() );
        addChild( textNode );

        timer = new Timer( 20, new ActionListener() {
            public void actionPerformed( final ActionEvent e ) {
                p.setPathTo( createPath() );
            }
        } );
        timer.start();
    }

    private GeneralPath createPath() {
        double time = System.currentTimeMillis() / 1000.0;
        GeneralPath path = new GeneralPath();
        final int maxX = 175;
        final float maxY = (float) ( 75 );
        final float dy = (float) ( 7 * Math.sin( time * 6 ) );
        final float dx = (float) ( 2 * Math.sin( time * 5 ) ) + 10;
        path.moveTo( 0, 0 );
        path.curveTo( maxX / 3 + dx, 25 + dy, 2 * maxX / 3 + dx, -25 - dy, maxX + dx, dy / 2 );
        path.lineTo( maxX + dx, maxY + dy / 2 );
        path.curveTo( 2 * maxX / 3 + dx, -25 + maxY - dy, maxX / 3 + dx, 25 + maxY + dy, 0, maxY );
        path.lineTo( 0, 0 );
        path.closePath();
        return path;
    }

    public void dispose() { timer.stop(); }
}