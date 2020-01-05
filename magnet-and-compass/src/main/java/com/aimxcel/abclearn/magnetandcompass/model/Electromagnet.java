

package com.aimxcel.abclearn.magnetandcompass.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;


public class Electromagnet extends CoilMagnet implements SimpleObserver {
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private SourceCoil _sourceCoilModel;
    private AbstractCurrentSource _currentSource;
    private boolean _isFlipped;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Sole constructor.
     * 
     * @param sourceCoilModel the electromagnet's coil
     * @param currentSource the electromagnet's current source
     */
    public Electromagnet( SourceCoil sourceCoilModel, AbstractCurrentSource currentSource ) {
        super();
        assert( sourceCoilModel != null );
        assert( currentSource != null );
        
        _sourceCoilModel = sourceCoilModel;
        _sourceCoilModel.addObserver( this );
        
        _currentSource = currentSource;
        _currentSource.addObserver( this );
        
        _isFlipped = false;
        
        update();
    }
    
    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _sourceCoilModel.removeObserver( this );
        _sourceCoilModel = null;
        
        if ( _currentSource != null ) {
            _currentSource.removeObserver( this );
            _currentSource = null;
        }
    }
    
    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------
    
    /**
     * Sets the electromagnet's current source.
     * 
     * @param currentSource
     */
    public void setCurrentSource( AbstractCurrentSource currentSource ) {
        assert( currentSource != null );
        if ( currentSource != _currentSource ) {
            if ( _currentSource != null ) {
                _currentSource.removeObserver( this );
            }
            _currentSource = currentSource;
            _currentSource.addObserver( this );
            update();
        }
    }
    
    /**
     * Gets the eletromagnet's current source.
     * 
     * @return the current source
     */
    public AbstractCurrentSource getCurrentSource() {
        return _currentSource;
    }
    
    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------
    
    /*
     * Updates current in the coil and strength of the magnet.
     */
    public void update() {
     
        /* 
         * The magnet size is a circle that has the same radius as the coil.
         * Adding half the wire width makes it look a little better.
         */
        double diameter = ( 2 * _sourceCoilModel.getRadius() ) +  ( _sourceCoilModel.getWireWidth() / 2 );
        super.setSize( diameter, diameter );
        
        // Current amplitude is proportional to amplitude of the current source.
        _sourceCoilModel.setCurrentAmplitude( _currentSource.getAmplitude() );
        
        // Compute the electromagnet's emf amplitude.
        double amplitude = ( _sourceCoilModel.getNumberOfLoops() / (double) MagnetAndCompassConstants.ELECTROMAGNET_LOOPS_MAX ) * _currentSource.getAmplitude();
        amplitude = MathUtil.clamp( -1, amplitude, 1 );
        
        // Flip the polarity
        if ( amplitude >= 0 && _isFlipped ) {
            flipPolarity();
            _isFlipped = false;
        }
        else if ( amplitude < 0 && !_isFlipped ) {
            flipPolarity();
            _isFlipped = true;
        }
        
        /* 
         * Set the strength.
         * This is a bit of a "fudge". 
         * We set the strength of the magnet to be proportional to its emf.
         */
        double strength = Math.abs( amplitude ) * getMaxStrength();
        setStrength( strength );
    }
}