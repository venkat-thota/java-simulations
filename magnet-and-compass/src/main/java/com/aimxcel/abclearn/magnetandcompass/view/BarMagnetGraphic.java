

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Shape;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2.ChangeEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing.Components;
import com.aimxcel.abclearn.magnetandcompass.collision.CollisionDetector;
import com.aimxcel.abclearn.magnetandcompass.collision.ICollidable;
import com.aimxcel.abclearn.magnetandcompass.model.BarMagnet;


public class BarMagnetGraphic extends AimxcelImageGraphic
        implements SimpleObserver, ICollidable, ApparatusPanel2.ChangeListener {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private BarMagnet _barMagnetModel;
    private CollisionDetector _collisionDetector;
    private Rectangle[] _collisionBounds;
    private MagnetAndCompassMouseHandler _mouseHandler;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param component      the parent Component
     * @param barMagnetModel model of the bar magnet
     */
    public BarMagnetGraphic( Component component, BarMagnet barMagnetModel ) {
        super( component, MagnetAndCompassResources.getImage( MagnetAndCompassConstants.BAR_MAGNET_IMAGE ) );

        assert ( component != null );
        assert ( barMagnetModel != null );

        // Save a reference to the model.
        _barMagnetModel = barMagnetModel;
        _barMagnetModel.addObserver( this );

        // Registration point is the center of the image.
        centerRegistrationPoint();

        // Setup interactivity.
        _mouseHandler = new MagnetAndCompassMouseHandler( Components.barMagnet, UserComponentTypes.sprite, _barMagnetModel, this );
        _collisionDetector = new CollisionDetector( this );
        _mouseHandler.setCollisionDetector( _collisionDetector );
        super.setCursorHand();
        super.addMouseInputListener( _mouseHandler );

        // Synchronize view with model.
        update();
    }

    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _barMagnetModel.removeObserver( this );
    }

    //----------------------------------------------------------------------------
    // Override inherited methods
    //----------------------------------------------------------------------------

    /**
     * Updates when we become visible.
     *
     * @param visible true for visible, false for invisible
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        update();
    }

    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    /**
     * Updates the view to match the model.
     */
    public void update() {

        if ( isVisible() ) {

            clearTransform();

            // Size
            final double xScale = _barMagnetModel.getWidth() / getWidth();
            final double yScale = _barMagnetModel.getHeight() / getHeight();
            scale( xScale, yScale );

            // Rotation
            if ( _barMagnetModel.getDirection() != 0 ) {
                rotate( _barMagnetModel.getDirection() );
            }

            // Location
            setLocation( (int) _barMagnetModel.getX(), (int) _barMagnetModel.getY() );

            repaint();
        }
    }

    //----------------------------------------------------------------------------
    // ICollidable implementation
    //----------------------------------------------------------------------------

    /*
     * @see edu.colorado.aimxcel.magnet-and-compass.view.ICollidable#getCollisionDetector()
     */
    public CollisionDetector getCollisionDetector() {
        return _collisionDetector;
    }

    /*
     * @see edu.colorado.aimxcel.magnet-and-compass.view.ICollidable#getCollisionBounds()
     */
    public Shape[] getCollisionBounds() {
        if ( isVisible() ) {
            if ( _collisionBounds == null ) {
                _collisionBounds = new Rectangle[1];
                _collisionBounds[0] = new Rectangle();
            }
            _collisionBounds[0].setBounds( getBounds() );
            return _collisionBounds;
        }
        else {
            return null;
        }
    }

    //----------------------------------------------------------------------------
    // ApparatusPanel2.ChangeListener implementation
    //----------------------------------------------------------------------------

    /*
    * Informs the mouse handler of changes to the apparatus panel size.
    */
    public void canvasSizeChanged( ChangeEvent event ) {
        _mouseHandler.setDragBounds( 0, 0, event.getCanvasSize().width, event.getCanvasSize().height );
    }
}
