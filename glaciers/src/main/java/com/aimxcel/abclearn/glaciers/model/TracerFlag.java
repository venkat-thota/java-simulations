
package com.aimxcel.abclearn.glaciers.model;

import java.awt.geom.Point2D;
import java.util.Random;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierAdapter;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierListener;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;

public class TracerFlag extends AbstractTool {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------    

    private static final double MIN_FALLOVER_ANGLE = Math.toRadians( 30 );
    private static final double MAX_FALLOVER_ANGLE = Math.toRadians( 80 );
    private static final Random RANDOM_FALLOVER = new Random();

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private final Glacier _glacier;
    private final GlacierListener _glacierListener;
    private boolean _onValleyFloor;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public TracerFlag( Point2D position, Glacier glacier ) {
        super( position );
        _glacier = glacier;
        _glacierListener = new GlacierAdapter() {
            public void iceThicknessChanged() {
                checkForDeletion();
            }
        };
        _glacier.addGlacierListener( _glacierListener );
        _onValleyFloor = false;
    }

    public void cleanup() {
        _glacier.removeGlacierListener( _glacierListener );
        super.cleanup();
    }

    public void startDrag() {
        _onValleyFloor = false;
    }

    //----------------------------------------------------------------------------
    // AbstractTool overrides
    //----------------------------------------------------------------------------

    /*
     * If the tool is above or below the ice, snap it to the ice.
     * If there is no ice, the tool will snap to the valley floor.
     */
    protected void constrainDrop() {

        double x = getX();
        if ( GlaciersConstants.SNAP_TOOLS_TO_HEADWALL ) {
            // constrain x to >= headwall
            x = Math.max( getX(), _glacier.getHeadwallX() );
        }

        final double surfaceElevation = _glacier.getSurfaceElevation( x );
        final double valleyElevation = _glacier.getValley().getElevation( x );

        // dropped where there is no ice?
        _onValleyFloor = ( surfaceElevation == valleyElevation );

        if ( getY() > surfaceElevation ) {
            // snap to ice surface
            setPosition( x, surfaceElevation );
        }
        else if ( getY() <= valleyElevation ) {
            if ( _onValleyFloor ) {
                // snap to the valley floor
                setPosition( x, valleyElevation );
            }
            else {
                // snap to slightly above the valley floor
                setPosition( x, valleyElevation + 1 );
            }
        }
    }

    public void clockTicked( ClockEvent clockEvent ) {

        if ( !isDragging() && !_onValleyFloor ) {

            // if the flag was on the surface, make sure it's still on the surface
            double currentY = getElevation();
            final double currentSurfaceElevation = _glacier.getSurfaceElevation( getX() );
            if ( currentY > currentSurfaceElevation ) {
                currentY = currentSurfaceElevation;
            }

            // distance = velocity * dt
            MutableVector2D velocity = _glacier.getIceVelocity( getX(), currentY );
            final double dt = clockEvent.getSimulationTimeChange();
            double newX = getX() + ( velocity.getX() * dt );
            double newY = getY() + ( velocity.getY() * dt );

            // constrain x to 1 meter beyond the terminus
            final double maxX = _glacier.getTerminusX() + 1;
            if ( newX > maxX ) {
                newX = maxX;
            }

            // constrain y to the surface of the glacier or valley
            final double newGlacierSurfaceElevation = _glacier.getSurfaceElevation( newX );
            if ( newY > newGlacierSurfaceElevation ) {
                newY = newGlacierSurfaceElevation;
            }

            // are we at past the terminus?
            final double newValleyElevation = _glacier.getValley().getElevation( newX );
            if ( newGlacierSurfaceElevation == newValleyElevation ) {
                _onValleyFloor = true;
                // flags "fall over" when they are past the terminus
                setOrientation( calculateRandomFalloverAngle() );
            }

            setPosition( newX, newY );
        }
    }

    /*
    * Calculates a random angle for the flag to "fall over".
    */
    private static double calculateRandomFalloverAngle() {
        return MIN_FALLOVER_ANGLE + ( RANDOM_FALLOVER.nextDouble() * ( MAX_FALLOVER_ANGLE - MIN_FALLOVER_ANGLE ) );
    }

    //----------------------------------------------------------------------------
    // Self deletion
    //----------------------------------------------------------------------------

    /*
    * Deletes itself if covered by an advancing glacier.
    */
    private void checkForDeletion() {
        if ( _onValleyFloor && !isDeletedSelf() ) {
            double iceThicknessAtFlag = _glacier.getIceThickness( getX() );
            if ( iceThicknessAtFlag > 0 ) {
                deleteSelf();
            }
        }
    }
}
