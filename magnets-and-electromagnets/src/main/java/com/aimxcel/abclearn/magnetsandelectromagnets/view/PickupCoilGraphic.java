

package com.aimxcel.abclearn.magnetsandelectromagnets.view;

import java.awt.Component;
import java.awt.Point;
import java.awt.Shape;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2.ChangeEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.NonInteractiveEventListener;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.collision.CollisionDetector;
import com.aimxcel.abclearn.magnetsandelectromagnets.collision.ICollidable;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Lightbulb;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.PickupCoil;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Voltmeter;


public class PickupCoilGraphic extends GraphicLayerSet
        implements SimpleObserver, ICollidable, ApparatusPanel2.ChangeListener {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private PickupCoil _pickupCoilModel;
    private CoilGraphic _coilGraphic;
    private LightbulbGraphic _lightbulbGraphic;
    private VoltmeterGraphic _voltmeterGraphic;
    private CompositeAimxcelGraphic _foreground, _background;
    private CollisionDetector _collisionDetector;
    private FluxDisplayGraphic _fluxDisplayGraphic;
    private PickupCoilSamplePointsGraphic _samplePointsGraphic;
    private MagnetsAndElectromagnetsMouseHandler _mouseHandler;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param component       the parent Component
     * @param baseModel       the base model
     * @param pickupCoilModel
     * @param lightbulbModel
     * @param voltmeterModel
     */
    public PickupCoilGraphic(
            final Component component,
            BaseModel baseModel,
            PickupCoil pickupCoilModel,
            Lightbulb lightbulbModel,
            Voltmeter voltmeterModel ) {

        assert ( component != null );
        assert ( baseModel != null );
        assert ( pickupCoilModel != null );
        assert ( lightbulbModel != null );
        assert ( voltmeterModel != null );

        _pickupCoilModel = pickupCoilModel;
        _pickupCoilModel.addObserver( this );

        _collisionDetector = new CollisionDetector( this );

        // Graphics components
        _coilGraphic = new CoilGraphic( component, pickupCoilModel, baseModel );
        _coilGraphic.setEndsConnected( true );
        _lightbulbGraphic = new LightbulbGraphic( component, lightbulbModel );
        ;
        _voltmeterGraphic = new VoltmeterGraphic( component, voltmeterModel );

        // Foreground composition
        _foreground = new CompositeAimxcelGraphic( component );
        _foreground.addGraphic( _coilGraphic.getForeground() );
        _foreground.addGraphic( _lightbulbGraphic );
        _foreground.addGraphic( _voltmeterGraphic );

        // Background composition
        _background = new CompositeAimxcelGraphic( component );
        _background.addGraphic( _coilGraphic.getBackground() );

        /* =================================================================
        * NOTE! --
        * Do NOT add the foreground and background to this GraphicLayerSet.
        * They will be added directly to the apparatus panel in the module,
        * so that objects can pass between the foreground and background.
        * =================================================================
        */

        // Interactivity
        setDraggingEnabled( true );

        // Points on the coil where the magnetic field is sampled to compute flux.
        _samplePointsGraphic = new PickupCoilSamplePointsGraphic( component, pickupCoilModel );
        _samplePointsGraphic.setVisible( false );
        _foreground.addGraphic( _samplePointsGraphic );

        // Area & flux display
        _fluxDisplayGraphic = new FluxDisplayGraphic( component, pickupCoilModel );
        _fluxDisplayGraphic.setVisible( false );
        _fluxDisplayGraphic.setLocation( 75, -50 );
        _foreground.addGraphic( _fluxDisplayGraphic );

        update();
    }

    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _pickupCoilModel.removeObserver( this );
        _pickupCoilModel = null;
        _coilGraphic.cleanup();
    }

    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------

    /**
     * Gets the AimxcelGraphic that contains the foreground elements of the coil.
     *
     * @return the foreground graphics
     */
    public AimxcelGraphic getForeground() {
        return _foreground;
    }

    /**
     * Gets the AimxcelGraphic that contains the background elements of the coil.
     *
     * @return the background graphics
     */
    public AimxcelGraphic getBackground() {
        return _background;
    }

    /**
     * Gets the coil graphic.
     * This is intended for use in debugging, or for connecting a control panel.
     */
    public CoilGraphic getCoilGraphic() {
        return _coilGraphic;
    }

    /**
     * Gets the lightbulb graphic.
     * This is intended for use in debugging.
     */
    public LightbulbGraphic getLightbulbGraphic() {
        return _lightbulbGraphic;
    }

    /**
     * Enables and disables dragging of the coil.
     *
     * @param enabled true or false
     */
    public void setDraggingEnabled( boolean enabled ) {

        _foreground.removeAllMouseInputListeners();
        _background.removeAllMouseInputListeners();

        if ( enabled ) {
            // Interactivity
            _mouseHandler = new MagnetsAndElectromagnetsMouseHandler( Components.pickupCoil, UserComponentTypes.sprite, _pickupCoilModel, this );
            _mouseHandler.setCollisionDetector( _collisionDetector );

            _foreground.setCursorHand();
            _foreground.addMouseInputListener( _mouseHandler );

            _background.setCursorHand();
            _background.addMouseInputListener( _mouseHandler );
        }
        else {
            // Sim-sharing "not interactive" messages
            _foreground.addMouseInputListener( new NonInteractiveEventListener( Components.pickupCoil ) );
            _background.addMouseInputListener( new NonInteractiveEventListener( Components.pickupCoil ) );
        }
    }

    public void setSamplePointsVisible( boolean visible ) {
        _samplePointsGraphic.setVisible( visible );
    }

    public boolean isSamplePointsVisible() {
        return _samplePointsGraphic.isVisible();
    }

    public void setFluxDisplayVisible( boolean visible ) {
        _fluxDisplayGraphic.setVisible( visible );
    }

    public boolean isFluxDisplayVisible() {
        return _fluxDisplayGraphic.isVisible();
    }

    //----------------------------------------------------------------------------
    // GraphicLayerSet overrides
    //----------------------------------------------------------------------------

    /**
     * Is a specified point inside the graphic?
     *
     * @param x
     * @param y
     * @return true or false
     */
    public boolean contains( int x, int y ) {
        return _foreground.contains( x, y ) || _background.contains( x, y );
    }

    /**
     * Is a specified point inside the graphic?
     *
     * @param p the point
     * @return true or false
     */
    public boolean contains( Point p ) {
        return contains( p.x, p.y );
    }

    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    public void update() {

        if ( _foreground.isVisible() ) {

            // Location
            _foreground.setLocation( (int) _pickupCoilModel.getX(), (int) _pickupCoilModel.getY() );
            _background.setLocation( (int) _pickupCoilModel.getX(), (int) _pickupCoilModel.getY() );

            // Position the lightbulb and voltmeter at the top of the coil.
            _foreground.clearTransform();
            _background.clearTransform();
            int x = -10;
            int y = -( _coilGraphic.getForeground().getHeight() / 2 );
            _lightbulbGraphic.setLocation( x, y );
            _voltmeterGraphic.setLocation( x + 5, y + 5 );

            // Direction (do this *after* positioning lightbulb and voltmeter!)
            _foreground.rotate( _pickupCoilModel.getDirection() );
            _background.rotate( _pickupCoilModel.getDirection() );

            _foreground.repaint();
            _background.repaint();
        }
    }

    //----------------------------------------------------------------------------
    // ICollidable implementation
    //----------------------------------------------------------------------------

    /*
    * @see edu.colorado.phet.magnets-and-electromagnets.view.ICollidable#getCollisionDetector()
    */
    public CollisionDetector getCollisionDetector() {
        return _collisionDetector;
    }

    /*
    * @see edu.colorado.phet.magnets-and-electromagnets.view.ICollidable#getCollisionBounds()
    */
    public Shape[] getCollisionBounds() {
        return _coilGraphic.getCollisionBounds();
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