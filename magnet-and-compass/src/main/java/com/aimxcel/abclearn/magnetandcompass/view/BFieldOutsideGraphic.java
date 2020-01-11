

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2.ChangeEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.model.AbstractMagnet;


public class BFieldOutsideGraphic extends AbstractBFieldGraphic implements SimpleObserver, ApparatusPanel2.ChangeListener{

    private AbstractMagnet _magnetModel;
    
    /**
     * Constructor.
     * 
     * @param component
     * @param magnetModel
     * @param xSpacing
     * @param ySpacing
     */
    public BFieldOutsideGraphic( Component component, AbstractMagnet magnetModel, int xSpacing, int ySpacing ) {
        super( component, magnetModel, xSpacing, ySpacing );
        
        _magnetModel = magnetModel;
        _magnetModel.addObserver( this );
    }
    
    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _magnetModel.removeObserver( this );
        _magnetModel = null;
    }
    
    /*
     * Creates the description of the needles (grid points) in the grid.
     * In this case, we fill the apparatus panel with grid points, based on
     * the bounds of the apparatus panel and the spacing of the points.
     */
    protected GridPoint[] createGridPoints() {
        
        ArrayList<GridPoint> gridPoints = new ArrayList<GridPoint>();
        
        Rectangle bounds = getGridBoundsReference();
        final int xSpacing = getXSpacing();
        final int ySpacing = getYSpacing();
        
        // Determine how many points are needed to fill the apparatus panel.
        final int xCount = (int) ( bounds.width / xSpacing ) + 1;
        final int yCount = (int) ( bounds.height / ySpacing ) + 1;
        
        // Create the grid points.
        for ( int i = 0; i < xCount; i++ ) {
            for ( int j = 0; j < yCount; j++ ) {
                double x = bounds.getX() + ( i * xSpacing );
                double y = bounds.getY() + ( j * ySpacing );
                gridPoints.add( new GridPoint( x, y ) );
            }
        }
        
        // convert to array
        return (GridPoint[]) gridPoints.toArray( new GridPoint[gridPoints.size()] );
    }
    
    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------
    
    /**
     * When the magnet changes, update the needle descriptors.
     */
    public void update() {
        updateStrengthAndOrientation();
        repaint();
    }
    
    //----------------------------------------------------------------------------
    // ApparatusPanel2.ChangeListener implementation
    //----------------------------------------------------------------------------
    
    /**
     * Resets the grid bounds whenever the apparatus panel's canvas size changes.
     */
    public void canvasSizeChanged( ChangeEvent event ) {
        Dimension parentSize = event.getCanvasSize();
        setGridBounds( 0, 0, parentSize.width, parentSize.height );
        super.setBoundsDirty();
    }
}
