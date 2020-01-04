

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;

import edu.umd.cs.piccolo.nodes.PPath;

public class TestAlignInside extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestAlignInside() {
        super( TestAlignInside.class.getName() );

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 1024, 768 ) );

        PPath pathNode1 = new PPath( new Rectangle2D.Double( -200, -400, 400, 200 ) );
        canvas.addWorldChild( pathNode1 );

        PPath pathNode2 = new PPath( new Rectangle2D.Double( 100, 200, 500, 300 ) );
        pathNode2.setStrokePaint( Color.RED );
        canvas.addWorldChild( pathNode2 );

        pathNode2.setOffset( 100, 100 );
        PNodeLayoutUtils.alignInside( pathNode1, pathNode2, SwingConstants.BOTTOM, SwingConstants.CENTER );

        setContentPane( canvas );
        pack();
    }

    public static void main( String[] args ) {
        JFrame frame = new TestAlignInside();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
