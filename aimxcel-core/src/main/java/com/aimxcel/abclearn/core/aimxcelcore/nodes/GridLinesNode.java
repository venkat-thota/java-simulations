

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class GridLinesNode extends PComposite {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridLinesNode( int rows, int columns, double width, double height, Stroke stroke, Paint strokePaint, Paint fillPaint ) {
        super();

        final PDimension cellSize = new PDimension( width / columns, height / rows );

        // outside edge
        PBounds edgeBounds = new PBounds( 0, 0, width, height );
        PPath edgeNode = new PPath( edgeBounds );
        edgeNode.setStroke( stroke );
        edgeNode.setStrokePaint( strokePaint );
        edgeNode.setPaint( fillPaint );
        addChild( edgeNode );

        // horizontal lines
        for ( int row = 0; row < rows; row++ ) {
            GeneralPath path = new GeneralPath();
            path.moveTo( (float) edgeBounds.getMinX(), (float) ( edgeBounds.getMinY() + ( row * cellSize.getHeight() ) ) );
            path.lineTo( (float) edgeBounds.getMaxX(), (float) ( edgeBounds.getMinY() + ( row * cellSize.getHeight() ) ) );
            PPath lineNode = new PPath( path );
            lineNode.setStroke( stroke );
            lineNode.setStrokePaint( strokePaint );
            addChild( lineNode );
        }

        // vertical lines
        for ( int column = 0; column < columns; column++ ) {
            GeneralPath path = new GeneralPath();
            path.moveTo( (float) ( edgeBounds.getMinX() + ( column * cellSize.getWidth() ) ), (float) edgeBounds.getMinY() );
            path.lineTo( (float) ( edgeBounds.getMinX() + ( column * cellSize.getWidth() ) ), (float) edgeBounds.getMaxY() );
            PPath lineNode = new PPath( path );
            lineNode.setStroke( stroke );
            lineNode.setStrokePaint( strokePaint );
            addChild( lineNode );
        }
    }
}