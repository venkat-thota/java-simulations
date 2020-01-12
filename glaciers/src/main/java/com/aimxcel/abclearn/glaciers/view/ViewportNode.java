
package com.aimxcel.abclearn.glaciers.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.glaciers.model.Viewport;
import com.aimxcel.abclearn.glaciers.model.Viewport.ViewportListener;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
public class ViewportNode extends PPath {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    private static final Color STROKE_COLOR = Color.BLACK;
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private final Viewport _viewport;
    private final GlaciersModelViewTransform _mvt;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public ViewportNode( Viewport viewport, float strokeWidth, GlaciersModelViewTransform mvt ) {
        super();
        
        _viewport = viewport;
        _viewport.addViewportListener( new ViewportListener() {
            public void boundsChanged() {
                updateRectangle();
            }
        });
        
        _mvt = mvt;
        
        setPaint( null );
        final float dashSpacing = 4 * strokeWidth;
        setStroke( new BasicStroke( strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { dashSpacing, dashSpacing }, 0 ) ); // dashed line
        setStrokePaint( STROKE_COLOR );
        
        updateRectangle();
    }
    
    public void cleanup() {}
    
    //----------------------------------------------------------------------------
    // Updaters
    //----------------------------------------------------------------------------
    
    /*
     * Updates the rectangle to match the viewport bounds.
     */
    private void updateRectangle() {
        Rectangle2D rModel = _viewport.getBoundsReference();
        Rectangle2D rView = _mvt.modelToView( rModel );
        setPathTo( rView );
    }

}