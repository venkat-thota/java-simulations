
package com.aimxcel.abclearn.glaciers.view;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.model.Valley;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.util.PAffineTransform;
public class MountainsAndValleyNode extends PImage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// These are absolute x,y coordinates of the markers in the image file
    private static final Point2D F_0 = new Point2D.Double( 313, 138 );   // marker at x=0, y=F(0)
    private static final Point2D F_70000 = new Point2D.Double( F_0.getX() + 4940, F_0.getY() + 201 ); // marker at x=70000, y=F(70000)
    
    public MountainsAndValleyNode( Valley valley, GlaciersModelViewTransform mvt ) {
        super( GlaciersImages.MOUNTAINS );
        setPickable( false );
        setChildrenPickable( false );
        
        // x scale
        final double viewDistanceX = mvt.modelToView( 70000, 0 ).getX();
        final double imageDistanceX = ( F_70000.getX() - F_0.getX() );
        final double scaleX = viewDistanceX / imageDistanceX;
        
        // y scale
        final double viewDistanceY = mvt.modelToView( 0, valley.getElevation( 70000 ) - valley.getElevation( 0 ) ).getY();
        final double imageDistanceY = ( F_70000.getY() - F_0.getY() );
        final double scaleY = ( viewDistanceY / imageDistanceY ) / GlaciersConstants.Y_AXIS_SCALE_IN_IMAGE;
        
        // x & y offset
        final double offsetX = -F_0.getX();
        final double offsetY = ( mvt.modelToView( 0, valley.getElevation( 0 ) ).getY() / scaleY ) - F_0.getY();

        PAffineTransform transform = getTransformReference( true );
        transform.scale( scaleX, scaleY );
        transform.translate( offsetX, offsetY );
    }
}
