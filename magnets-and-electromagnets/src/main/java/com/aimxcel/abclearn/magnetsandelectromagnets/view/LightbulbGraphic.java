

package com.aimxcel.abclearn.magnetsandelectromagnets.view;

import java.awt.Component;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsConstants;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsResources;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Lightbulb;
import com.aimxcel.abclearn.magnetsandelectromagnets.util.VariableAlphaImageGraphic;


public class LightbulbGraphic extends CompositeAimxcelGraphic implements SimpleObserver {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    // These parameters affect drawing order.
    private static final double BULB_LAYER = 1;
    private static final double RAYS_LAYER = 2;
    
    private static final double BULB_RADIUS = 30.0; // Radius of the glass, must be aligned with image by trial & error.
    private static final int DISTANCE_BULB_IS_SCREWED_INTO_BASE = 10; // must be aligned with rays via trial & error
    
    private static final float GLASS_MIN_ALPHA = 0.35f; // alpha when the bulb is off
    private static final double DEFAULT_GLASS_GLOW_SCALE = 15;

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private Lightbulb _lightbulbModel;
    private double _previousIntensity;
    private VariableAlphaImageGraphic _glassGraphic;
    private LightRaysGraphic _raysGraphic;
    private double _glassGlowScale;  // scales the modulation of alpha, used to make the bulb glow
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Sole constructor.
     * 
     * @param component the parent Component
     * @param lightbulbModel
     */
    public LightbulbGraphic( Component component, Lightbulb lightbulbModel ) {
        super( component );
        
        assert( component != null );
        assert( lightbulbModel !=  null );
        
        _lightbulbModel = lightbulbModel;
        _lightbulbModel.addObserver( this );

        // Glass
        BufferedImage glassImage = MagnetsAndElectromagnetsResources.getImage( MagnetsAndElectromagnetsConstants.LIGHTBULB_GLASS_IMAGE );
        _glassGraphic = new VariableAlphaImageGraphic( component, glassImage );
        addGraphic( _glassGraphic, BULB_LAYER );
        
        // Cap
        BufferedImage capImage = MagnetsAndElectromagnetsResources.getImage( MagnetsAndElectromagnetsConstants.LIGHTBULB_CAP_IMAGE );
        AimxcelImageGraphic capGraphic = new AimxcelImageGraphic( component, capImage );
        addGraphic( capGraphic, BULB_LAYER );
        
        // Base
        BufferedImage baseImage = MagnetsAndElectromagnetsResources.getImage( MagnetsAndElectromagnetsConstants.LIGHTBULB_BASE_IMAGE );
        AimxcelImageGraphic baseGraphic = new AimxcelImageGraphic( component, baseImage );
        addGraphic( baseGraphic, BULB_LAYER );
        
        // Locations
        int rx = baseGraphic.getImage().getWidth() / 2;
        int ry = baseGraphic.getImage().getHeight();
        baseGraphic.setRegistrationPoint( rx, ry );
        rx = capGraphic.getImage().getWidth() / 2;
        ry = capGraphic.getImage().getHeight();
        capGraphic.setRegistrationPoint( rx, ry );
        capGraphic.setLocation( 0, -( baseGraphic.getImage().getHeight() - DISTANCE_BULB_IS_SCREWED_INTO_BASE ) );
        rx = _glassGraphic.getImage().getWidth() / 2;
        ry = _glassGraphic.getImage().getHeight();
        _glassGraphic.setRegistrationPoint( rx, ry );
        _glassGraphic.setLocation( 0, -( baseGraphic.getImage().getHeight() + capGraphic.getImage().getHeight() - DISTANCE_BULB_IS_SCREWED_INTO_BASE ) );
        
        // Rays
        _raysGraphic = new LightRaysGraphic( component, BULB_RADIUS );
        addGraphic( _raysGraphic, RAYS_LAYER );
        _raysGraphic.setRegistrationPoint( 0, 90 );
        
        _glassGlowScale = DEFAULT_GLASS_GLOW_SCALE;
        update();
    }
    
    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _lightbulbModel.removeObserver( this );
        _lightbulbModel = null;
    }
    
    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------
    
    /**
     * Sets the scaling factor that determines how much the bulb glows.
     * Larger values will cause the bulb to reach it's maximum glow sooner.
     * 
     * @param scale
     */
    public void setGlassGlowScale( double scale ) {
        if ( scale < 0 ) {
            throw new IllegalArgumentException( "scale must be >= 0: " + scale );
        }
        if ( scale != _glassGlowScale ) {
            _glassGlowScale = scale;
            forceUpdate();
        }
    }
    
    /**
     * Gets the scaling factor that determines how much the bulb glows.
     * Larger values will cause the bulb to reach it's maximum glow sooner.
     * 
     * @return
     */
    public double getGlassGlowScale() {
        return _glassGlowScale;
    }
    
    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    private void forceUpdate() {
        _previousIntensity = -1;
        update();
    }
    
    /**
     * Synchronize the view with the model.
     * 
     * @see com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver#update()
     */
    public void update() {
        
        setVisible( _lightbulbModel.isEnabled() );
        
        if ( isVisible() ) {
            
            // Get the light intensity, a value in the range 0...+1.
            double intensity = _lightbulbModel.getIntensity();

            if ( intensity != _previousIntensity ) {
                
                _raysGraphic.setIntensity( intensity );
                
                // modulate alpha channel of the glass to make it appear to glow
                float alpha = (float)( GLASS_MIN_ALPHA + ( _glassGlowScale * ( 1f - GLASS_MIN_ALPHA ) * (float)intensity ) );
                if ( alpha > 1f ) {
                    alpha = 1f;
                }
                _glassGraphic.setAlpha( alpha );
                
                _previousIntensity = intensity;
                setBoundsDirty();
                repaint(); 
            }
        }
        
    }
}
