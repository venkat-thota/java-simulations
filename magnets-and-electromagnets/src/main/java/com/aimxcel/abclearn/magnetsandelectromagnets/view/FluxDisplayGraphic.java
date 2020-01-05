

package com.aimxcel.abclearn.magnetsandelectromagnets.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelTextGraphic2;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.PickupCoil;

/**
 * FluxDisplayGraphic displays flux and emf information related to a pickup coil.
 * This is used for debugging and is not localized.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class FluxDisplayGraphic extends GraphicLayerSet implements SimpleObserver {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    private static final String AVERAGE_BX = "avg Bx";
    private static final String PHI = "\u03a6";
    private static final String DELTA = "\u0394";
    private static final Font FONT = new AimxcelFont( 15 );
    private static final DecimalFormat FORMAT = new DefaultDecimalFormat( "0" );
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private PickupCoil _pickupCoilModel;
    private AimxcelTextGraphic2 _averageBxValue, _fluxValue, _deltaFluxValue, _emfValue;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Constructor.
     * @param component
     * @param pickupCoilModel
     */
    public FluxDisplayGraphic( Component component, PickupCoil pickupCoilModel ) {
        super();
        
        _pickupCoilModel = pickupCoilModel;
        _pickupCoilModel.addObserver( this );
        
        _averageBxValue = new AimxcelTextGraphic2( component, FONT, "?", Color.YELLOW, 0, 0 );
        _fluxValue = new AimxcelTextGraphic2( component, FONT, "?", Color.YELLOW, 0, 25 );
        _deltaFluxValue = new AimxcelTextGraphic2( component, FONT, "?", Color.YELLOW, 0, 50 );
        _emfValue = new AimxcelTextGraphic2( component, FONT, "?", Color.YELLOW, 0, 75 );
        
        addGraphic( _averageBxValue );
        addGraphic( _fluxValue );
        addGraphic( _deltaFluxValue );
        addGraphic( _emfValue );
        
        update();
    }
    
    //----------------------------------------------------------------------------
    // Superclass overrides
    //----------------------------------------------------------------------------
    
    /**
     * Update the display when this graphic becomes visible.
     * 
     * @param visible
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( visible ) {
            update();
        }
    }
    
    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------
    
    /**
     * Update the display when the model changes.
     */
    public void update() {
        if ( isVisible() ) {
            double averageBx = _pickupCoilModel.getAverageBx();
            double flux = _pickupCoilModel.getFlux();
            double deltaFlux = _pickupCoilModel.getDeltaFlux();
            double emf = _pickupCoilModel.getEmf();

            _averageBxValue.setText( AVERAGE_BX + " = " + FORMAT.format( averageBx ) + "G" );
            _fluxValue.setText( PHI + " = " + FORMAT.format( flux ) + " W" );
            _deltaFluxValue.setText( DELTA + PHI + " = " + FORMAT.format( deltaFlux ) + " W" );
            _emfValue.setText( "EMF = " + FORMAT.format( emf ) + " V" );
        }
    }
}
