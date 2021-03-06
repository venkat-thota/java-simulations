

package com.aimxcel.abclearn.magnetsandelectromagnets.module;

import java.awt.Color;
import java.awt.Dimension;



public interface ICompassGridModule {

    /**
     * Sets the grid spacing.
     * 
     * @param xSpacing space between needles in horizontal dimension, in pixels
     * @param ySpacing space between needles in the vertical dimension, in pixels
     */
    public void setGridSpacing( int xSpacing, int ySpacing );
    
    /**
     * Gets the horizontal spacing between needles.
     * 
     * @return the spacing, in pixels
     */
    public int getGridXSpacing();
    
    /**
     * Gets the vertical spacing between needles.
     * 
     * @return the spacing, in pixels
     */
    public int getGridYSpacing();
    
    /**
     * Sets the size used for all needles in the grid.
     * 
     * @param size the size, in pixels
     */
    public void setGridNeedleSize( Dimension size );
    
    /**
     * Gets the size of all needles in the grid.
     * 
     * @return the size, in pixels
     */
    public Dimension getGridNeedleSize();
    
    /**
     * Tells the grid its background color.
     * 
     * @param color
     */
    public void setGridBackground( Color color );
}
