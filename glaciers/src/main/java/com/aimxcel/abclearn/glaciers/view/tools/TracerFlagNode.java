
package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.Image;

import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.model.TracerFlag;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class TracerFlagNode extends AbstractToolNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TracerFlag _tracerFlag;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public TracerFlagNode( TracerFlag tracerFlag, GlaciersModelViewTransform mvt, TrashCanDelegate trashCan ) {
        super( tracerFlag, mvt, trashCan );
        _tracerFlag = tracerFlag;
        PImage imageNode = new PImage( GlaciersImages.TRACER_FLAG );
        addChild( imageNode );
        imageNode.setOffset( 0, -imageNode.getFullBoundsReference().getHeight() ); // lower left corner
    }
    
    //----------------------------------------------------------------------------
    // AbstractToolNode overrides
    //----------------------------------------------------------------------------
    
    protected void startDrag() {
        _tracerFlag.startDrag();
        _tracerFlag.setOrientation( 0 );
    }
    
    protected void updateOrientation() {
        setRotation( _tracerFlag.getOrientation() );
    }
    
    //----------------------------------------------------------------------------
    // Utilities
    //----------------------------------------------------------------------------
    
    public static Image createImage() {
        return GlaciersImages.TRACER_FLAG;
    }
}
