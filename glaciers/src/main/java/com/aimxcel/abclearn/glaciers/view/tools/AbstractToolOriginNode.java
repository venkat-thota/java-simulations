
package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;

public abstract class AbstractToolOriginNode extends PComposite {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final float SIZE = 10f;
    private static final Color FILL_COLOR = Color.WHITE;
    private static final Color STROKE_COLOR = Color.BLACK;
    private static final Stroke STROKE = new BasicStroke( 1f );
    
    protected AbstractToolOriginNode( double rotation ) {
        // arrow pointing to the left
        GeneralPath trianglePath = new GeneralPath();
        trianglePath.moveTo( 0f, 0f );
        trianglePath.lineTo( SIZE, -SIZE / 2 );
        trianglePath.lineTo( SIZE, SIZE / 2 );
        trianglePath.closePath();
        PPath pathNode = new PPath( trianglePath );
        pathNode.setPaint( FILL_COLOR );
        pathNode.setStroke( STROKE );
        pathNode.setStrokePaint( STROKE_COLOR );
        addChild( pathNode );
        pathNode.setOffset( 0, 0 );
        // rotate
        rotate( rotation );
    }
    
    public static class LeftToolOriginNode extends AbstractToolOriginNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LeftToolOriginNode() {
            super( 0 );
        }
    }
    
    public static class RightToolOriginNode extends AbstractToolOriginNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RightToolOriginNode() {
            super( Math.PI );
        }
    }
    
    public static class UpToolOriginNode extends AbstractToolOriginNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UpToolOriginNode() {
            super( Math.PI / 2 );
        }
    }
    
    public static class DownToolOriginNode extends AbstractToolOriginNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DownToolOriginNode() {
           super( Math.PI / 2 );
        }
    }
}
