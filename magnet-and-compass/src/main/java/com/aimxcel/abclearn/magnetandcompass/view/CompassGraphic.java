 

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2.ChangeEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing.Components;
import com.aimxcel.abclearn.magnetandcompass.collision.CollisionDetector;
import com.aimxcel.abclearn.magnetandcompass.collision.ICollidable;
import com.aimxcel.abclearn.magnetandcompass.model.Compass;


public class CompassGraphic extends CompositeAimxcelGraphic
        implements SimpleObserver, ICollidable, ApparatusPanel2.ChangeListener {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    // Sizes
    private static final int RING_DIAMETER = 80; // outer diameter, including stroke
    private static final int RING_STROKE_WIDTH = 10;
    private static final int INDICATOR_DIAMETER = 6;
    private static final int ANCHOR_DIAMETER = 6;
    private static final Dimension NEEDLE_SIZE = new Dimension( 55, 15 );

    // Colors
    private static final Color RING_COLOR = new Color( 153, 153, 153 );
    private static final Color LENS_COLOR = new Color( 255, 255, 255, 15 ); // alpha!
    private static final Color INDICATOR_COLOR = Color.BLACK;
    private static final Color ANCHOR_COLOR = Color.BLACK;

    // misc.
    private static final int INDICATOR_INCREMENT = 45; // degrees

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Compass _compassModel;
    private AimxcelShapeGraphic _needleNorthGraphic, _needleSouthGraphic;
    private CompassNeedleCache _needleCache;
    private CollisionDetector _collisionDetector;
    private Ellipse2D[] _collisionBounds;
    private MagnetAndCompassMouseHandler _mouseHandler;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     * The registration point is at the rotation point of the compass needle.
     *
     * @param component    the parent Component
     * @param compassModel the compass model
     */
    public CompassGraphic( Component component, Compass compassModel ) {
        super( component );
        assert ( component != null );
        assert ( compassModel != null );

        _compassModel = compassModel;
        _compassModel.addObserver( this );

        setRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON ) );

        // Body of the compass
        BodyGraphic body = new BodyGraphic( component );
        body.centerRegistrationPoint();
        addGraphic( body );

        // Needle
        {
            // Cache
            _needleCache = new CompassNeedleCache( NEEDLE_SIZE );

            // North tip
            Color northColor = _needleCache.getNorthColor( 1.0 ); // opaque
            Shape northShape = _needleCache.getNorthShape( _compassModel.getDirection() );
            _needleNorthGraphic = new AimxcelShapeGraphic( component );
            _needleNorthGraphic.setColor( northColor );
            _needleNorthGraphic.setShape( northShape );
            addGraphic( _needleNorthGraphic );

            // South tip
            Color southColor = _needleCache.getSouthColor( 1.0 ); // opaque
            Shape southShape = _needleCache.getSouthShape( _compassModel.getDirection() );
            _needleSouthGraphic = new AimxcelShapeGraphic( component );
            _needleSouthGraphic.setColor( southColor );
            _needleSouthGraphic.setShape( southShape );
            addGraphic( _needleSouthGraphic );
        }

        // Thing that anchors the needle to the body.
        Shape anchorShape = new Ellipse2D.Double( -( ANCHOR_DIAMETER / 2 ), -( ANCHOR_DIAMETER / 2 ), ANCHOR_DIAMETER, ANCHOR_DIAMETER );
        AimxcelShapeGraphic anchor = new AimxcelShapeGraphic( component );
        anchor.setShape( anchorShape );
        anchor.setPaint( ANCHOR_COLOR );
        addGraphic( anchor );

        // Setup interactivity.
        _mouseHandler = new MagnetAndCompassMouseHandler( Components.compass, UserComponentTypes.sprite, _compassModel, this );
        _collisionDetector = new CollisionDetector( this );
        _mouseHandler.setCollisionDetector( _collisionDetector );
        super.setCursorHand();
        super.addMouseInputListener( _mouseHandler );

        update();
    }

    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _compassModel.removeObserver( this );
        _compassModel = null;
    }

    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    /**
     * Updates the view to match the model.
     */
    public void update() {
        super.setVisible( _compassModel.isEnabled() );
        if ( isVisible() ) {

            // Needle rotation
            double direction = _compassModel.getDirection();
            Shape northShape = _needleCache.getNorthShape( direction );
            Shape southShape = _needleCache.getSouthShape( direction );
            _needleNorthGraphic.setShape( northShape );
            _needleSouthGraphic.setShape( southShape );

            // Location
            setLocation( (int) _compassModel.getX(), (int) _compassModel.getY() );
        }
    }

    //----------------------------------------------------------------------------
    // ICollidable implementation
    //----------------------------------------------------------------------------

    /*
     * @see edu.colorado.phet.magnet-and-compass.view.ICollidable#getCollisionDetector()
     */
    public CollisionDetector getCollisionDetector() {
        return _collisionDetector;
    }

    /*
     * @see edu.colorado.phet.magnet-and-compass.view.ICollidable#getCollisionBounds()
     */
    public Shape[] getCollisionBounds() {
        if ( isVisible() ) {
            if ( _collisionBounds == null ) {
                _collisionBounds = new Ellipse2D.Double[1];
                _collisionBounds[0] = new Ellipse2D.Double();
            }
            double x = getX() - ( RING_DIAMETER / 2 );
            double y = getY() - ( RING_DIAMETER / 2 );
            _collisionBounds[0].setFrame( x, y, RING_DIAMETER, RING_DIAMETER );
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

    //----------------------------------------------------------------------------
    // Inner classes
    //----------------------------------------------------------------------------

    /*
    * All the parts of the compass that don't move.
    */
    private static class BodyGraphic extends AimxcelImageGraphic {
        public BodyGraphic( Component component ) {
            super( component );

            // This will be flattened after we've added graphics to it.
            GraphicLayerSet graphicLayerSet = new GraphicLayerSet( component );
            RenderingHints hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            graphicLayerSet.setRenderingHints( hints );

            // Ring & lens, center at (0,0)
            double adjustedDiameter = RING_DIAMETER - RING_STROKE_WIDTH; // adjust for half of stroke width
            Shape ringShape = new Ellipse2D.Double( -adjustedDiameter / 2, -adjustedDiameter / 2, adjustedDiameter, adjustedDiameter );
            AimxcelShapeGraphic ring = new AimxcelShapeGraphic( component );
            ring.setShape( ringShape );
            ring.setPaint( LENS_COLOR );
            ring.setBorderColor( RING_COLOR );
            ring.setStroke( new BasicStroke( RING_STROKE_WIDTH ) );
            graphicLayerSet.addGraphic( ring );

            // Indicators at evenly-spaced increments around the ring.
            int angle = 0; // degrees
            AimxcelShapeGraphic indicatorGraphic = null;
            Shape indicatorShape = new Ellipse2D.Double( -( INDICATOR_DIAMETER / 2 ), -( INDICATOR_DIAMETER / 2 ), INDICATOR_DIAMETER, INDICATOR_DIAMETER );
            while ( angle < 360 ) {
                indicatorGraphic = new AimxcelShapeGraphic( component );
                indicatorGraphic.setShape( indicatorShape );
                indicatorGraphic.setPaint( INDICATOR_COLOR );
                Vector2D v = Vector2D.createPolar( adjustedDiameter / 2, Math.toRadians( angle ) );
                int rx = (int) v.getX();
                int ry = (int) v.getY();
                indicatorGraphic.setRegistrationPoint( rx, ry );
                graphicLayerSet.addGraphic( indicatorGraphic );
                angle += INDICATOR_INCREMENT;
            }

            // Flatten the graphic layer set.
            {
                Dimension size = graphicLayerSet.getSize();
                BufferedImage bufferedImage = new BufferedImage( size.width, size.height, BufferedImage.TYPE_INT_ARGB );
                Graphics2D g2 = bufferedImage.createGraphics();
                graphicLayerSet.translate( size.width / 2, size.height / 2 );
                graphicLayerSet.paint( g2 );
                setImage( bufferedImage );
            }
        }
    }
}
