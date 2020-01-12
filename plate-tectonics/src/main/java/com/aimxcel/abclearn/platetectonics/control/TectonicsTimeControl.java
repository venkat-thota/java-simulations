package com.aimxcel.abclearn.platetectonics.control;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.PANEL_TITLE_FONT;

import java.text.DecimalFormat;

import javax.swing.*;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;
import com.aimxcel.abclearn.platetectonics.model.TectonicsClock;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.PlayPauseButton;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.StepButton;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.HSliderNode;
import com.aimxcel.abclearn.lwjgl.utils.GLSwingForwardingClock;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class TectonicsTimeControl extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// this property is handled in the Swing EDT
    private Property<Double> speedProperty = new Property<Double>( 1.0 );

    private static final double SLIDER_MIN = 0.1;
    private static final double SLIDER_MAX = 10;
    private final TectonicsClock lwjglClock;
    private final HBox container;

    public TectonicsTimeControl( final TectonicsClock lwjglClock, final Property<Boolean> isAutoMode ) {
        this.lwjglClock = lwjglClock;
        final IClock swingClock = new GLSwingForwardingClock( lwjglClock );

        final DecimalFormat timeFormat = new DecimalFormat( "0" );

        final PNode timeSlider = new TimeSlider( lwjglClock, isAutoMode );

        // play/pause button.
        final PlayPauseButton playPauseButton = new PlayPauseButton( (int) ( 100 * 0.7 * 0.7 ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            // initial state
            setPlaying( !swingClock.isPaused() );

            // control the clock
            addListener( new Listener() {
                public void playbackStateChanged() {
                    if ( swingClock.isPaused() ) {
                        swingClock.start();
                    }
                    else {
                        swingClock.pause();
                    }
                }
            } );

            // listen to the clock event changes (and disable it when it is at the time limit)
            lwjglClock.addClockListener( new ClockAdapter() {
                @Override public void clockStarted( ClockEvent clockEvent ) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setPlaying( true );
                        }
                    } );
                }

                @Override public void clockPaused( ClockEvent clockEvent ) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setPlaying( false );
                        }
                    } );
                }

                @Override public void simulationTimeChanged( ClockEvent clockEvent ) {
                    final boolean atMax = isAtTimeLimit();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setEnabled( !atMax );
                            if ( atMax ) {
                                setPlaying( false );
                            }
                        }
                    } );
                }
            } );
        }};

        // step button
        final StepButton stepButton = new StepButton( (int) ( playPauseButton.getButtonDimension().width * 0.8 ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{

            // control the clock
            addListener( new Listener() {
                public void buttonPressed() {
                    swingClock.stepClockWhilePaused();
                }
            } );

            // disable it when the clock is running OR when it is at the time limit
            lwjglClock.addClockListener( new ClockAdapter() {
                private boolean shouldBeEnabled() {
                    return lwjglClock.isPaused() && !isAtTimeLimit();
                }

                private void updateState() {
                    final boolean enabled = shouldBeEnabled();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setEnabled( enabled );
                        }
                    } );
                }

                @Override public void clockStarted( ClockEvent clockEvent ) {
                    updateState();
                }

                @Override public void clockPaused( ClockEvent clockEvent ) {
                    updateState();
                }

                @Override public void simulationTimeChanged( ClockEvent clockEvent ) {
                    updateState();
                }
            } );
        }};

        container = new HBox( 12, HBox.CENTER_ALIGNED,
                              new VBox( VBox.CENTER_ALIGNED,
                                        new PText( Strings.TIME_ELAPSED ) {/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

										{
                                            setFont( PANEL_TITLE_FONT );
                                        }},
                                        new HBox( 12, HBox.CENTER_ALIGNED,
                                                  new PText( "0" ) {/**
													 * 
													 */
													private static final long serialVersionUID = 1L;

												{
                                                      // update the time readout whenever the clock changes
                                                      lwjglClock.addClockListener( new ClockAdapter() {
                                                          @Override public void simulationTimeChanged( ClockEvent clockEvent ) {
                                                              final double simulationTime = lwjglClock.getSimulationTime();
                                                              SwingUtilities.invokeLater( new Runnable() {
                                                                  public void run() {
                                                                      setText( timeFormat.format( simulationTime ) );
                                                                      repaint();
                                                                  }
                                                              } );
                                                          }
                                                      } );
                                                  }},
                                                  new PText( Strings.MILLION_YEARS ) {/**
													 * 
													 */
													private static final long serialVersionUID = 1L;

												{
                                                      setFont( new AimxcelFont( 12 ) );
                                                  }}
                                        ) ),
                              timeSlider,
                              playPauseButton,
                              stepButton );
        addChild( container );

        isAutoMode.addObserver( new SimpleObserver() {
            public void update() {
                final boolean isAuto = isAutoMode.get();
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        setChild( timeSlider, isAuto );
                        setChild( playPauseButton, isAuto );
                        setChild( stepButton, isAuto );
                        repaint();

                        final double speed = speedProperty.get();

                        LWJGLUtils.invoke( new Runnable() {
                            public void run() {
                                lwjglClock.setTimeMultiplier( isAuto ? speed : 1 );
                            }
                        } );
                    }
                } );
            }
        } );
    }

    private void setChild( PNode node, boolean visible ) {
        if ( container == null ) {
            return;
        }
        if ( visible && node.getParent() == null ) {
            container.addChild( node );
        }
        if ( !visible && node.getParent() != null ) {
            container.removeChild( node );
        }
    }

    public void resetAll() {
        // speed property is accessed in the Swing EDT, and our resetAll() runs in the LWJGL thread
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                speedProperty.reset();
            }
        } );
    }

    // this function run from LWJGL thread
    private boolean isAtTimeLimit() {
        return lwjglClock.getSimulationTime() >= lwjglClock.getTimeLimit();
    }

    private class TimeSlider extends HSliderNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TimeSlider( final TectonicsClock clock, final Property<Boolean> isAutoMode ) {
            super( UserComponents.timeSpeedSlider, SLIDER_MIN, SLIDER_MAX, 5, 100, speedProperty, new Property<Boolean>( true ) );

            addLabel( SLIDER_MIN, new PText( Strings.TIME_SLOW ) );
            addLabel( SLIDER_MAX, new PText( Strings.TIME_FAST ) );

            speedProperty.addObserver( new SimpleObserver() {
                public void update() {
                    // our clock is running in the LWJGL thread, so we need to wrap it
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            clock.setTimeMultiplier( speedProperty.get() );
                        }
                    } );
                }
            } );
        }
    }
}
