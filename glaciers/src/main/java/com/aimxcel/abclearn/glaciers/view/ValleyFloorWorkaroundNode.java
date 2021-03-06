
package com.aimxcel.abclearn.glaciers.view;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.model.Valley;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

public class ValleyFloorWorkaroundNode extends PPath {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final double MIN_X = -4500;
    
    public ValleyFloorWorkaroundNode( Valley valley, GlaciersModelViewTransform mvt ) {
        
        GeneralPath path = new GeneralPath();
        Point2D pModel = new Point2D.Double();
        Point2D pView = new Point2D.Double();
        final double dx = IceNode.getDx();
        for ( double x = 0; x >= MIN_X; x -= dx ) {
            double elevation = valley.getElevation( x );
            pModel.setLocation( x, elevation );
            mvt.modelToView( pModel, pView );
            if ( x == 0 ) {
                path.moveTo( (float)pView.getX(), (float)pView.getY() );
            }
            else {
                path.lineTo( (float)pView.getX(), (float)pView.getY() );      
            }
        }
        path.closePath();
        
        setPathTo( path );
        setPaint( GlaciersConstants.UNDERGROUND_COLOR );
        setStroke( null );
    }
}
