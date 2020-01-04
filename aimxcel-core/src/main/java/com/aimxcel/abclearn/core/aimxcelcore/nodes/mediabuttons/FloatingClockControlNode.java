
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;


public class FloatingClockControlNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double DISABLED_IMAGE_RESCALE_OP_SCALE = 1;
    private final PlayPauseButton playPauseButton;
    private final StepButton stepButton;

    /**
     * @param playButtonPressed a flag to indicate whether the play button has been pressed.  Note that this does not necessarily mean the clock will be running, the module should also be active so that multiple module clocks don't run at once
     * @param timeReadout
     * @param clock
     * @param clearString
     * @param timeReadoutColor
     */
    public FloatingClockControlNode( SettableProperty<Boolean> playButtonPressed, final Function1<Double, String> timeReadout,
                                     final IClock clock, final String clearString, ObservableProperty<Color> timeReadoutColor ) {
        this( playButtonPressed, timeReadout == null ? null : new Property<String>( timeReadout.apply( clock.getSimulationTime() ) ) {{
            clock.addClockListener( new ClockAdapter() {
                @Override
                public void simulationTimeChanged( ClockEvent clockEvent ) {
                    set( timeReadout.apply( clock.getSimulationTime() ) );
                }
            } );
        }},
              new VoidFunction0() {
                  public void apply() {
                      clock.stepClockWhilePaused();
                  }
              },
              new VoidFunction0() {
                  public void apply() {
                      clock.setSimulationTime( 0.0 );
                  }
              },
              new Property<Double>( clock.getSimulationTime() ) {{
                  clock.addClockListener( new ClockAdapter() {
                      public void simulationTimeChanged( ClockEvent clockEvent ) {
                          set( clock.getSimulationTime() );
                      }
                  } );
              }}, clearString, timeReadoutColor
        );
    }

    /**
     * Creates a FloatingClockControlNode
     *
     * @param playButtonPressed property to indicate whether the clock should be running or not; this value is mediated by a Property<Boolean> since this needs to also be 'and'ed with whether the module is active for multi-tab simulations.
     * @param timeReadout
     * @param step              steps the clock when 'step' is pressed which the sim is paused
     * @param resetTime
     * @param simulationTime
     * @param clearString
     * @param timeReadoutColor
     */
    public FloatingClockControlNode( final SettableProperty<Boolean> playButtonPressed, final Property<String> timeReadout,
                                     final VoidFunction0 step, final VoidFunction0 resetTime, final Property<Double> simulationTime,
                                     final String clearString, final ObservableProperty<Color> timeReadoutColor ) {
        playPauseButton = new PlayPauseButton( 80 ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPlaying( playButtonPressed.get() );
            final Listener updatePlayPauseButtons = new Listener() {
                public void playbackStateChanged() {
                    playButtonPressed.set( isPlaying() );
                }
            };
            addListener( updatePlayPauseButtons );
            updatePlayPauseButtons.playbackStateChanged();//Sync immediately
            playButtonPressed.addObserver( new SimpleObserver() {
                public void update() {
                    setPlaying( playButtonPressed.get() );
                }
            } );
        }};
        stepButton = new StepButton( 60 ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
                setOffset( playPauseButton.getFullBounds().getMaxX() + 5, playPauseButton.getFullBounds().getCenterY() - getFullBounds().getHeight() / 2 );
                final PlayPauseButton.Listener updateEnabled = new PlayPauseButton.Listener() {
                    public void playbackStateChanged() {
                        setEnabled( !playPauseButton.isPlaying() );
                    }
                };
                playButtonPressed.addObserver( new SimpleObserver() {
                    public void update() {
                        updateEnabled.playbackStateChanged();
                    }
                } );
                playPauseButton.addListener( updateEnabled );
                updateEnabled.playbackStateChanged();
                addListener( new Listener() {
                    public void buttonPressed() {
                        if ( isEnabled() ) {
                            step.apply();
                        }
                    }
                } );
            }

            @Override
            protected double getDisabledImageRescaleOpScale() {
                return DISABLED_IMAGE_RESCALE_OP_SCALE;
            }
        };
        addChild( playPauseButton );
        addChild( stepButton );

        if ( timeReadout != null ) {
            final PText readoutNode = new PText() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setFont( new AimxcelFont( 24, true ) );
                timeReadoutColor.addObserver( new SimpleObserver() {
                    public void update() {
                        setTextPaint( timeReadoutColor.get() );
                    }
                } );
                timeReadout.addObserver( new SimpleObserver() {
                    public void update() {
                        setText( timeReadout.get() );
                        setOffset( stepButton.getFullBounds().getMaxX() + 5, stepButton.getFullBounds().getCenterY() - getFullBounds().getHeight() / 2 );
                    }
                } );
            }};
            addChild( readoutNode );

            //Only show the "clear" button if the user specified a string
            //TODO: In the future, this string could be provided by aimxcelcommon-strings, and we would need another way for the client code to identify whether the clear button should be shown
            if ( clearString != null && clearString.length() > 0 ) {
                addChild( new HTMLImageButtonNode( clearString ) {/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				{
                    setOffset( readoutNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, readoutNode.getFullBounds().getMaxY() );
                    addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent e ) {
                            resetTime.apply();
                        }
                    } );
                    simulationTime.addObserver( new SimpleObserver() {
                        public void update() {
                            setEnabled( simulationTime.get() > 0 );
                        }
                    } );
                }} );
            }
        }
    }

    public PlayPauseButton getPlayPauseButton() {
        return playPauseButton;
    }

    /**
     * This class should be used when adding a rewind button to the control
     * panel.  It provides a consistent look and feel as well as a default
     * location to the left of the play button.
     */
    public class FloatingRewindButton extends RewindButton {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FloatingRewindButton() {
            super( 60 );

            // Set default position, can be moved by client if needed.
            setOffset(
                    getPlayPauseButton().getFullBounds().getMinX() - getFullBounds().getWidth() - 5,
                    getPlayPauseButton().getFullBounds().getCenterY() - getFullBounds().getHeight() / 2 );
        }

        @Override
        protected double getDisabledImageRescaleOpScale() {
            return DISABLED_IMAGE_RESCALE_OP_SCALE;
        }
    }

}