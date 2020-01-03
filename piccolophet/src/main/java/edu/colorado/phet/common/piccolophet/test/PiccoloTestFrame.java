

package edu.colorado.phet.common.piccolophet.test;

import javax.swing.JFrame;

import edu.colorado.phet.common.piccolophet.BufferedAbcLearnPCanvas;
import edu.colorado.phet.common.piccolophet.AbcLearnPCanvas;
import edu.umd.cs.piccolo.PNode;

public class PiccoloTestFrame extends JFrame {

    private AbcLearnPCanvas canvas;

    public PiccoloTestFrame( String title ) {
        super( title );

        canvas = new BufferedAbcLearnPCanvas();
        setContentPane( canvas );
        setSize( 800, 600 );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public void addNode( PNode node ) {
        canvas.getLayer().addChild( node );
    }
}
