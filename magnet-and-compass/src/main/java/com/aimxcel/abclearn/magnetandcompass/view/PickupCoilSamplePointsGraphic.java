

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.model.PickupCoil;

/**
 * SamplePointsGraphic is the graphical representation of the
 * points on the coil where the magnetic field is sampled.
 * This graphic is used for debugging, and is intended to be a child of PickupCoilGraphic.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class PickupCoilSamplePointsGraphic extends CompositeAimxcelGraphic implements SimpleObserver {
    
    private static final Color POINT_COLOR = Color.YELLOW;
    private static final Shape POINT_SHAPE = new Ellipse2D.Double( -2, 2, 4, 4 );
    
    private PickupCoil _pickupCoilModel;
    
    /**
     * Sole constructor.
     * Creates a set of graphics that represent the sample points.
     * 
     * @param component
     */
    public PickupCoilSamplePointsGraphic( Component component, PickupCoil pickupCoilModel ) {
        super( component );
        _pickupCoilModel = pickupCoilModel;
        _pickupCoilModel.addObserver( this );
    }
    
    /**
     * Updates this graphic when it becomes visible.
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( visible ) {
            update();
        }
    }
    
    /**
     * Updates the sample points display when the model changes.
     */
    public void update() {
        if ( isVisible() ) {
            clear(); // remove all child graphics
            final Point2D[] samplePoints = _pickupCoilModel.getSamplePoints();
            Component component = getComponent();
            for ( int i = 0; i < samplePoints.length; i++ ) {
                AimxcelGraphic samplePointGraphic = createGraphic( component, (int)samplePoints[i].getX(), (int)samplePoints[i].getY() );
                addGraphic( samplePointGraphic );
            }
        }
    }
    
    /*
     * Creates the graphical representation of a sample point.
     */
    private static AimxcelGraphic createGraphic( Component component, int x, int y ) {
        AimxcelShapeGraphic g = new AimxcelShapeGraphic( component );
        g.setShape( POINT_SHAPE );
        g.setColor( POINT_COLOR );
        g.centerRegistrationPoint();
        g.setLocation( x, y );
        return g;
    }
}