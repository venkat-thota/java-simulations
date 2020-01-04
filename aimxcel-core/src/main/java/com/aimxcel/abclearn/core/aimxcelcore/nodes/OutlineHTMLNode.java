
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;


public class OutlineHTMLNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String html;
    private Font font;

    public OutlineHTMLNode( String html, Font font, Color fill, Color outline ) {
        this( html, font, fill, outline, Math.PI * 2 / 4 );
    }

    public OutlineHTMLNode( String html, Font font, Color fill, Color outline, double dTheta ) {
        this.html = html;
        this.font = font;

        double r = 1.5;
        for ( double theta = 0; theta < Math.PI * 2; theta += dTheta ) {
            addChild( outline, r * Math.sin( theta ), r * Math.cos( theta ) );
        }
        addChild( fill, 0, 0 );
    }

    private void addChild( Color color, double dx, double dy ) {
        HTMLNode node = new HTMLNode( html, color, font );
        node.setOffset( dx, dy );
        addChild( node );
    }

    public static void main( String[] args ) {
        JFrame frame = new JFrame();
        PCanvas contentPane = new PCanvas();
        contentPane.getLayer().addChild( new OutlineHTMLNode( "<html>Testing html<br></br> H<sub>2</sub>O", new AimxcelFont( 30, true ), Color.yellow, Color.blue ) );
        frame.setContentPane( contentPane );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 700, 700 );
        frame.setVisible( true );
    }
}
