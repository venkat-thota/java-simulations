
package com.aimxcel.abclearn.glaciers.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierAdapter;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierListener;
public class IceThicknessTool extends AbstractTool {
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private final Glacier _glacier;
    private final GlacierListener _glacierListener;
    private double _thickness;
    private final ArrayList _listeners; // list of IceThicknessToolListener

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public IceThicknessTool( Point2D position, Glacier glacier ) {
        super( position );
        
        _glacier = glacier;
        _glacierListener = new GlacierAdapter() {
            public void iceThicknessChanged() {
                updateThickness();
            }
        };
        _glacier.addGlacierListener( _glacierListener );
        
        _listeners = new ArrayList();
    }
    
    public void cleanup() {
        _glacier.removeGlacierListener( _glacierListener );
        super.cleanup();
    }
    
    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------
    
    public double getThickness() {
        return _thickness;
    }
    
    private void setThickness( double thickness ) {
        if ( thickness != _thickness ) {
            _thickness = thickness;
            notifyThicknessChanged();
        }
    }
    
    //----------------------------------------------------------------------------
    // AbstractTool overrides
    //----------------------------------------------------------------------------
    
    protected void constrainDrop() {
        if ( _thickness > 0 ) {

            double x = getX();
            if ( GlaciersConstants.SNAP_TOOLS_TO_HEADWALL ) {
                // constrain x to >= headwall
                x = Math.max( getX(), _glacier.getHeadwallX() );
            }

            // snap y to valley floor
            double valleyElevation = _glacier.getValley().getElevation( x );

            setPosition( x, valleyElevation );
        }
    }
    
    protected void handlePositionChanged() {
        updateThickness();
    }
    
    private void updateThickness() {
        final double x = getX();
        final double thickness = _glacier.getIceThickness( x );
        setThickness( thickness );
    }
    
    //----------------------------------------------------------------------------
    // Listener interface
    //----------------------------------------------------------------------------
    
    public interface IceThicknessToolListener {
        public void thicknessChanged();
    }
    
    public void addIceThicknessToolListener( IceThicknessToolListener listener ) {
        _listeners.add( listener );
    }
    
    public void removeIceThicknessToolListener( IceThicknessToolListener listener ) {
        _listeners.remove( listener );
    }
    
    //----------------------------------------------------------------------------
    // Notification of changes
    //----------------------------------------------------------------------------
    
    private void notifyThicknessChanged() {
        Iterator i = _listeners.iterator();
        while ( i.hasNext() ) {
            ( (IceThicknessToolListener) i.next() ).thicknessChanged();    
        }
    }
}
