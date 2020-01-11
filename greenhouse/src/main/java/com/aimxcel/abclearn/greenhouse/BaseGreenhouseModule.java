package com.aimxcel.abclearn.greenhouse;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.greenhouse.common.graphics.ApparatusPanel;
import com.aimxcel.abclearn.greenhouse.common.graphics.CompositeGraphic;
import com.aimxcel.abclearn.greenhouse.filter.Filter1D;
import com.aimxcel.abclearn.greenhouse.filter.IrFilter;
import com.aimxcel.abclearn.greenhouse.model.Atmosphere;
import com.aimxcel.abclearn.greenhouse.model.BlackHole;
import com.aimxcel.abclearn.greenhouse.model.Earth;
import com.aimxcel.abclearn.greenhouse.model.GreenhouseClock;
import com.aimxcel.abclearn.greenhouse.model.GreenhouseModel;
import com.aimxcel.abclearn.greenhouse.model.Photon;
import com.aimxcel.abclearn.greenhouse.model.PhotonAbsorber;
import com.aimxcel.abclearn.greenhouse.model.PhotonEmitter;
import com.aimxcel.abclearn.greenhouse.model.ScatterEvent;
import com.aimxcel.abclearn.greenhouse.model.Star;
import com.aimxcel.abclearn.greenhouse.model.Thermometer;
import com.aimxcel.abclearn.greenhouse.view.AtmosphereGraphic;
import com.aimxcel.abclearn.greenhouse.view.EarthGraphic;
import com.aimxcel.abclearn.greenhouse.view.FlipperAffineTransformFactory;
import com.aimxcel.abclearn.greenhouse.view.PhotonGraphic;
import com.aimxcel.abclearn.greenhouse.view.ScatterEventGraphic;
import com.aimxcel.abclearn.greenhouse.view.TestApparatusPanel;
import com.aimxcel.abclearn.greenhouse.view.ThermometerGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;


public abstract class BaseGreenhouseModule extends Module {
	private static final double MAX_TIME_BETWEEN_TICKS = (double)GreenhouseClock.DEFAULT_DELAY_BETWEEN_TICKS * 5;
    private final HashMap photonToGraphicsMap = new HashMap();
    protected CompositeGraphic drawingCanvas;
    private ThermometerGraphic thermometerGraphic;
    protected EarthGraphic earthGraphic;
    private PhotonEmitterListener earthPhotonEmitterListener;
    private PhotonEmitterListener sunPhotonEmitterListener;
    private boolean thermometerEnabled;
    double exposedEarth = 1;

    // Determines how many photons are put in the model for everyone one
    // that shows up on the screen
    private int invisiblePhotonCnt = 10;
    private Earth earth;
    HashMap scatterToGraphicMap = new HashMap();

    private static final double EARTH_DIAM = Earth.radius * 2;
    private static final double SUN_DIAM = EARTH_DIAM * 5;
    private static final double SUN_EARTH_DIST = SUN_DIAM * 5;
    private Star sun;
    private ApparatusPanel apparatusPanel;
    private GreenhouseModel model;

    private static boolean s_firstTime = true;
    private Rectangle2D.Double modelBounds;
    private AtmosphereGraphic atmosphereGraphic;
	private ClockDelaySlider timeSpeedSlider;

    protected BaseGreenhouseModule( String s ) {
        super( s, new GreenhouseClock() );
        init();
    }

    private void init() {

    	// Create the clock control panel, including slider.
    	CoreClockControlPanel clockControlPanel = new CoreClockControlPanel( getClock() );
    	timeSpeedSlider = new ClockDelaySlider(150, 30, "0.00", getGreenhouseClock(), null);
        timeSpeedSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                getGreenhouseClock().setDelay((int)Math.round(MAX_TIME_BETWEEN_TICKS/timeSpeedSlider.getValue()));
            }
        } );
    	clockControlPanel.addBetweenTimeDisplayAndButtons(timeSpeedSlider);
        setClockControlPanel( clockControlPanel );

        // Set up the model and apparatus panel
        double modelHeight = EARTH_DIAM + SUN_DIAM + SUN_EARTH_DIST * 2;
        modelHeight = exposedEarth + Atmosphere.troposphereThickness;
        modelBounds = new Rectangle2D.Double( -modelHeight * 4 / 3 / 2, -exposedEarth,
                                                   modelHeight * 4 / 3,
                                                   modelHeight );

        model = new GreenhouseModel( modelBounds );
        this.setModel( model );

        apparatusPanel = new TestApparatusPanel( 4 / 3, new FlipperAffineTransformFactory( modelBounds ) );
        this.setSimulationPanel( apparatusPanel );
        apparatusPanel.setBackground( Color.black );

        drawingCanvas = apparatusPanel.getCompositeGraphic();

        //
        // Create the model elements and their view elements
        //
        earthPhotonEmitterListener = new PhotonEmitterListener();

        // Create the earth
        double gamma = Math.atan( ( modelBounds.getWidth() / 2 ) / Earth.radius );
        earth = new Earth( new Point2D.Double( 0, -Earth.radius + exposedEarth ), Math.PI / 2 - gamma, Math.PI / 2 + gamma );
        model.setEarth( earth );
        earth.getPhotonSource().setProductionRate( 1E-2 );
        earth.addPhotonEmitterListener( earthPhotonEmitterListener );
        earth.addPhotonAbsorberListener( new PhotonAbsorberListener() );
        earth.getPhotonSource().addListener( model );
        // EarthGraphic adds itself to the apparatus panel. Manages its own transform, too
        earthGraphic = new EarthGraphic( getApparatusPanel(), earth, modelBounds );
        earth.setReflectivityAssessor( earthGraphic );

        // Create the atmosphere
        Atmosphere atmosphere = new Atmosphere( earth );
        model.setAtmosphere( atmosphere );
        atmosphereGraphic = new AtmosphereGraphic( atmosphere, modelBounds, apparatusPanel );
        atmosphere.addScatterEventListener( new ModuleScatterEventListener() );
        atmosphereGraphic.setVisible( false );
        drawingCanvas.addGraphic( atmosphereGraphic, Double.MAX_VALUE );

        model.addModelElement( new ModelElement() {
            public void stepInTime( double dt ) {
                atmosphereGraphic.update( null, null );
            }
        } );

        // Create the sun
        sun = new Star( SUN_DIAM / 2, new Point2D.Double( 0, EARTH_DIAM + SUN_EARTH_DIST + SUN_DIAM / 2 ),
                        new Rectangle2D.Double( modelBounds.getX(),
                                                modelBounds.getY() + modelBounds.getHeight(),
                                                modelBounds.getWidth() / 1,
                                                1 ) );

        sun.setProductionRate( 0 );
        sunPhotonEmitterListener = new PhotonEmitterListener();
        sun.addListener( sunPhotonEmitterListener );
        model.setSun( sun );

        // Create a black hole to suck away any photons that are beyond the
        // model bounds
        BlackHole blackHole = new BlackHole( model );
        blackHole.addListener( model );
        blackHole.addListener( new PhotonAbsorberListener() );
        model.addModelElement( blackHole );

        // Put a thermometer on the earth
        Thermometer thermometer = new Thermometer( earth );
        thermometer.setLocation( new Point2D.Double( modelBounds.getX() + 2, .5 ) );
        model.addModelElement( thermometer );
        thermometerGraphic = new ThermometerGraphic( thermometer );

        // Set initial conditions
        thermometerEnabled( false );

        // Initialize the views
        if ( s_firstTime ) {
        	s_firstTime = false;
        	// Prevent a backdrop from appearing
            earthGraphic.setNoBackdrop();

            // Set up the model bounds.
            ( (TestApparatusPanel) getApparatusPanel() ).setModelBounds( modelBounds );

            atmosphereGraphic.setVisible( true );
            thermometerEnabled( true );

            GreenhouseApplication.paintContentImmediately();

            sun.setProductionRate( GreenhouseConfig.defaultSunPhotonProductionRate );

            // Set the default view. Note that this is a real mess. It works in conjunction with code in
            // GreenhouseControlPanel.AtmoshpereSelectionPane
            setToday();
        }
        else {

            ( (TestApparatusPanel) getApparatusPanel() ).setModelBounds( modelBounds );
            atmosphereGraphic.setVisible( true );
            thermometerEnabled( true );
            getApparatusPanel().setAffineTransformFactory( new FlipperAffineTransformFactory( modelBounds ) );
            sun.setProductionRate( GreenhouseConfig.defaultSunPhotonProductionRate );
        }
    }

    public GreenhouseModel getGreenhouseModel() {
        return model;
    }

    public GreenhouseClock getGreenhouseClock() {
    	return (GreenhouseClock)super.getClock();
    }

    protected ApparatusPanel getApparatusPanel() {
        return apparatusPanel;
    }

    @Override
    public void updateGraphics( ClockEvent event ) {
        super.updateGraphics( event );
        apparatusPanel.update( null, null );// force a repaint
    }

    protected HashMap getPhotonToGraphicsMap() {
        return photonToGraphicsMap;
    }

    @Override
    public void reset() {

        earth.getPhotonSource().setProductionRate( 1E-2 );
        earth.reset();
        sun.setProductionRate( GreenhouseConfig.defaultSunPhotonProductionRate );

        java.util.List photons = model.getPhotons();
        for ( int i = 0; i < photons.size(); i++ ) {
            Photon photon = (Photon) photons.get( i );
            model.photonAbsorbed( photon );
        }

        model.getPhotons().clear();
        sun.setProductionRate( GreenhouseConfig.defaultSunPhotonProductionRate );

        Iterator keyIt = photonToGraphicsMap.keySet().iterator();
        while ( keyIt.hasNext() ) {
            Object o = keyIt.next();
            PhotonGraphic pg = (PhotonGraphic) photonToGraphicsMap.get( o );
            getApparatusPanel().removeGraphic( pg );
        }
        photonToGraphicsMap.clear();

        // Reset the slider that controls sim speed.
        getGreenhouseClock().setDelay( GreenhouseClock.DEFAULT_DELAY_BETWEEN_TICKS );
    }

    public Earth getEarth() {
        return earth;
    }

    protected Rectangle2D.Double getFinalModelBounds() {
        return modelBounds;
    }

    public void thermometerEnabled( boolean enabled ) {
        thermometerEnabled = enabled;
        if ( enabled ) {
            getApparatusPanel().addGraphic( thermometerGraphic, ApparatusPanel.LAYER_DEFAULT );

        }
        else {
            getApparatusPanel().removeGraphic( thermometerGraphic );
        }
    }

    public void setVisiblePhotonRatio( double ratio ) {
        double cnt = 0;
        for ( Iterator iterator = photonToGraphicsMap.keySet().iterator(); iterator.hasNext(); ) {
            PhotonGraphic graphic = (PhotonGraphic) photonToGraphicsMap.get( iterator.next() );
            cnt += ratio;
            drawingCanvas.removeGraphic( graphic );
            graphic.setVisible( false );
            if ( cnt >= 1 ) {
                drawingCanvas.addGraphic( graphic, GreenhouseConfig.SUNLIGHT_PHOTON_GRAPHIC_LAYER );
                graphic.setVisible( true );
                cnt = 0;
            }
        }
        invisiblePhotonCnt = (int) ( 1 / ratio );
        earthPhotonEmitterListener.setInvisiblePhotonCnt( (int) ( 1 / ratio ) );
        sunPhotonEmitterListener.setInvisiblePhotonCnt( (int) ( 1 / ratio ) );
    }

    public boolean isThermometerEnabled() {
        return thermometerEnabled;
    }

    public void removeScatterEvent( ScatterEvent event ) {
        model.removeModelElement( event );
        ScatterEventGraphic seg = (ScatterEventGraphic) scatterToGraphicMap.get( event );
        getApparatusPanel().removeGraphic( seg );
        scatterToGraphicMap.remove( event );
    }

    public void setVirginEarth() {
        earthGraphic.setVirginEarth();
    }

    public void setIceAge() {
        earthGraphic.setIceAge();
    }

    public void setToday() {
        earthGraphic.setToday();
    }

    public void setVenus() {
        earthGraphic.setVenus();
    }

    public void setPreIndRev() {
        earthGraphic.set1750();
    }

    //----------------------------------------------------------------
    // Inner classes
    //----------------------------------------------------------------

    protected class PhotonEmitterListener implements PhotonEmitter.Listener<Photon> {
        // Used to tell if a photon is IR
        private final Filter1D irFilter = new IrFilter();
        private int n = 0;

        public void setInvisiblePhotonCnt( int cnt ) {
            invisiblePhotonCnt = cnt;
        }

        public void photonEmitted( Photon photon ) {
            n++;
            PhotonGraphic photonView = new PhotonGraphic( photon );
            photonToGraphicsMap.put( photon, photonView );
            photonView.setVisible( false );
            if ( n >= invisiblePhotonCnt ) {
                photonView.setVisible( true );
                double layer = irFilter.absorbs( photon.getWavelength() )
                               ? GreenhouseConfig.IR_PHOTON_GRAPHIC_LAYER
                               : GreenhouseConfig.SUNLIGHT_PHOTON_GRAPHIC_LAYER;
                drawingCanvas.addGraphic( photonView, layer );

                // reset counter
                n = 0;
            }
        }
    }

    /**
     * When a photon is absorbed, this removes its graphic from the aparatus panel
     */
    protected class PhotonAbsorberListener implements PhotonAbsorber.Listener {
        public void photonAbsorbed( Photon photon ) {
            PhotonGraphic photonView = (PhotonGraphic) photonToGraphicsMap.get( photon );
            drawingCanvas.removeGraphic( photonView );
            photonToGraphicsMap.remove( photon );
        }
    }

    public class ModuleScatterEventListener implements Atmosphere.ScatterEventListener {
        public void photonScatered( Photon photon ) {
            if ( photonToGraphicsMap.get( photon ) != null ) {
                ScatterEvent se = new ScatterEvent( photon, BaseGreenhouseModule.this );
                model.addModelElement( se );
            }
        }
    }
}
