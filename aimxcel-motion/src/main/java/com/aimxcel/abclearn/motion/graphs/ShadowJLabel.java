
package com.aimxcel.abclearn.motion.graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowPText;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;

public class ShadowJLabel extends PCanvas {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShadowJLabel( String text, Color foreground, Font font ) {
        ShadowPText shadowPText = new ShadowPText( text );
        shadowPText.setTextPaint( foreground );
        shadowPText.setFont( font );
        getLayer().addChild( shadowPText );
        setPreferredSize( new Dimension( (int) shadowPText.getFullBounds().getWidth() + 1, (int) shadowPText.getFullBounds().getHeight() + 1 ) );
        setOpaque( false );
        setBackground( null );
        setBorder( null );
    }

    public static void main( String[] args ) {
        JFrame frame = new JFrame();
        ShadowJLabel contentPane = new ShadowJLabel( "" + '\u03B8', Color.blue, new AimxcelFont( Font.BOLD, 24 ) );
        frame.setContentPane( contentPane );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frame.getWidth() / 2,
                           Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frame.getHeight() / 2 );
        frame.show();
    }
}
