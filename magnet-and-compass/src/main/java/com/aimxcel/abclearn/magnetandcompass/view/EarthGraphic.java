

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics2D;

import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;
import com.aimxcel.abclearn.magnetandcompass.model.BarMagnet;


public class EarthGraphic extends AimxcelImageGraphic implements SimpleObserver {

    // the image is opaque, this operator is used to make it transparent
    private static final Composite COMPOSITE = 
        AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.75f /* alpha */ );
    
    private static final double IMAGE_ROTATION = -( Math.PI / 2 );  // Earth image was created with north up, bar magnet has north to the right
    private static final double IMAGE_SCALE = 0.60; // scales the Earth image
    
    private BarMagnet _barMagnetModel;
    
    /**
     * Constructor.
     * 
     * @param component
     * @param barMagnetModel
     */
    public EarthGraphic( Component component, BarMagnet barMagnetModel ) {
        super( component, MagnetAndCompassResources.getImage( MagnetAndCompassConstants.EARTH_IMAGE ) );
        
        setIgnoreMouse( true ); // not draggable
        
        // Save a reference to the model.
        _barMagnetModel = barMagnetModel;
        _barMagnetModel.addObserver( this );
        
        // origin is at the center of the image
        centerRegistrationPoint();
        
        update();
    }
    
    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _barMagnetModel.removeObserver( this );
    }
    
    /**
     * Updates when we become visible.
     * 
     * @param visible true for visible, false for invisible
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        update();
    }
    
    /**
     * Updates the view to match the model.
     */
    public void update() {
        if ( isVisible() ) {
            
            clearTransform();
            
            // Scale
            scale( IMAGE_SCALE );

            // Rotation
            rotate( _barMagnetModel.getDirection() + IMAGE_ROTATION );
            
            // Location
            setLocation( (int) _barMagnetModel.getX(), (int) _barMagnetModel.getY() );
            
            repaint();
        }
    }
    
    /**
     * Draws Earth with transparency.  
     * 
     * @param g2 the graphics context
     */
    public void paint( Graphics2D g2 ) {
        if ( isVisible() ) {
            Composite oldComposite = g2.getComposite(); // save
            g2.setComposite( COMPOSITE );
            super.paint( g2 );
            g2.setComposite( oldComposite ); // restore
        }
    }
}
