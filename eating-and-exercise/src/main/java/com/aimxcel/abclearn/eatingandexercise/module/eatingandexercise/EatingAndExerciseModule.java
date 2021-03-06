

package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ResetAllButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.core.aimxcelcore.CoreModule;
import com.aimxcel.abclearn.core.aimxcelcore.help.DefaultWiggleMe;
import com.aimxcel.abclearn.core.aimxcelcore.help.HelpPane;
import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.control.CaloricItem;
import com.aimxcel.abclearn.eatingandexercise.control.ChartNode;
import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;
import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

public class EatingAndExerciseModule extends CoreModule {

    private EatingAndExerciseModel _model;
    private EatingAndExerciseCanvas _canvas;
    private CoreClockControlPanel _clockControlPanel;
    private JFrame parentFrame;
    private boolean inited = false;
    private boolean everStarted = false;
    private EatingAndExerciseClock eatingAndExerciseClock;

    private int numAddedItems = 0;
    private boolean showedInitialDragWiggleMe = false;

    private double getAgeYears() {
        return EatingAndExerciseUnits.secondsToYears( _model.getHuman().getAge() );
    }

    public EatingAndExerciseModule( final AimxcelFrame parentFrame ) {
        super( EatingAndExerciseStrings.TITLE_EATING_AND_EXERCISE_MODULE, new EatingAndExerciseClock(), EatingAndExerciseDefaults.STARTS_PAUSED );
        this.parentFrame = parentFrame;

        // Model
        eatingAndExerciseClock = (EatingAndExerciseClock) getClock();
        eatingAndExerciseClock.addClockListener( new ClockAdapter() {
            public void clockStarted( ClockEvent clockEvent ) {
                everStarted = true;
            }
        } );
        _model = new EatingAndExerciseModel( eatingAndExerciseClock );

        HumanAudioPlayer humanAudioPlayer = new HumanAudioPlayer( _model.getHuman() );
        humanAudioPlayer.start();

        GameOverDialog gameOverDialog = new GameOverDialog( parentFrame, _model.getHuman(), this );
        gameOverDialog.start();

        // Canvas
        _canvas = new EatingAndExerciseCanvas( _model, parentFrame );

        _model.addListener( new EatingAndExerciseModel.Adapter() {
            public void simulationTimeChanged() {
                if ( getAgeYears() >= _canvas.getChartNode().getMaxChartTime() ) {
                    _model.getClock().pause();
                    getClockControlPanel().setEnabled( false );
                }
            }
        } );
        _canvas.getChartNode().addListener( new ChartNode.Listener() {
            public void chartDataCleared() {
                getClockControlPanel().setEnabled( true );
            }
        } );

//        _canvas.getChartNode().add
        _canvas.addEditorClosedListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                activateStartButtonWiggleMe();
            }
        } );
        _model.getHuman().getSelectedExercise().addListener( new CalorieSet.Adapter() {
            public void itemAdded( CaloricItem item ) {
                incrementAddedItems();
            }
        } );

        _model.getHuman().getSelectedFoods().addListener( new CalorieSet.Adapter() {
            public void itemAdded( CaloricItem item ) {
                incrementAddedItems();
            }

        } );

        _canvas.addFoodPressedListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( !showedInitialDragWiggleMe ) {
                    showedInitialDragWiggleMe = true;
                    new DragToTargetHelpItem( EatingAndExerciseModule.this, _canvas, _canvas.getPlateNode(), EatingAndExerciseResources.getString( "put.food.on.plate" ),
                                              EatingAndExerciseModule.this.getHuman().getSelectedFoods() ).start();
                }
            }
        } );
        _canvas.addExerciseDraggedListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( !showedInitialDragWiggleMe ) {
                    showedInitialDragWiggleMe = true;
                    new DragToTargetHelpItem( EatingAndExerciseModule.this, _canvas, _canvas.getDiaryNode(), EatingAndExerciseResources.getString( "put.exercise.on.diary" ),
                                              EatingAndExerciseModule.this.getHuman().getSelectedExercise() ).start();
                }
            }
        } );

        setSimulationPanel( _canvas );

        // Control Panel
        setControlPanel( null );
        setLogoPanelVisible( false );

        // Clock controls
        _clockControlPanel = new CoreClockControlPanel( getClock() ) {
            public void setTimeDisplay( double time ) {
                super.setTimeDisplay( EatingAndExerciseUnits.secondsToYears( time ) );
            }
        };
        _clockControlPanel.setRewindButtonVisible( true );
        _clockControlPanel.setTimeDisplayVisible( true );
        _clockControlPanel.setUnits( EatingAndExerciseStrings.UNITS_TIME );
        _clockControlPanel.setTimeColumns( 10 );
        _clockControlPanel.setRewindButtonVisible( false );
        _clockControlPanel.setStepButtonTooltip( EatingAndExerciseResources.getString( "time.next-month" ) );
//        _clockControlPanel.setStepButtonText( EatingAndExerciseResources.getString( "time.next-month" ) );
        _clockControlPanel.setTimeFormat( "0.0" );

        ResetAllButton resetButton = new ResetAllButton( new Resettable() {
            public void reset() {
                resetAll();
            }
        }, parentFrame );

        JButton disclaimerButton = new JButton( EatingAndExerciseResources.getString( "disclaimer" ) );
        disclaimerButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                AimxcelOptionPane.showMessageDialog( parentFrame, EatingAndExerciseStrings.DISCLAIMER );
            }
        } );
        _clockControlPanel.add( new EatingAndExerciseHelpButton(), 0 );
        _clockControlPanel.add( disclaimerButton, 1 );

        _clockControlPanel.add( Box.createHorizontalStrut( 100 ), 2 );
        _clockControlPanel.add( resetButton, 3 );

        setClockControlPanel( _clockControlPanel );

        setHelpEnabled( true );
        setHelpPane( new HelpPane( parentFrame ) );
        reset();
    }

    public void resetAll() {
        _model.resetAll();
        _canvas.resetAll();
        getClockControlPanel().setEnabled( true );
    }

    private void incrementAddedItems() {
        numAddedItems++;
        if ( numAddedItems >= 3 ) {
            activateStartButtonWiggleMe();
        }
    }

    private void activateStartButtonWiggleMe() {
        if ( !inited && !everStarted ) {
            final MotionHelpBalloon motionHelpBalloon = new DefaultWiggleMe( _canvas, EatingAndExerciseResources.getString( "time.start" ) );
            eatingAndExerciseClock.addClockListener( new ClockAdapter() {
                public void clockStarted( ClockEvent clockEvent ) {
                    hideWiggleMe( motionHelpBalloon );
                }

                public void simulationTimeChanged( ClockEvent clockEvent ) {
                    hideWiggleMe( motionHelpBalloon );
                }
            } );
            motionHelpBalloon.setArrowTailPosition( MotionHelpBalloon.BOTTOM_CENTER );
            motionHelpBalloon.setOffset( 800, 0 );
            getDefaultHelpPane().add( motionHelpBalloon );
            motionHelpBalloon.animateTo( _clockControlPanel.getButtonCanvas(), 15 );

            inited = true;
        }
    }

    private void hideWiggleMe( MotionHelpBalloon motionHelpBalloon ) {
        if ( getDefaultHelpPane().getLayer().indexOfChild( motionHelpBalloon ) >= 0 ) {
            getDefaultHelpPane().remove( motionHelpBalloon );
        }
    }

    public Human getHuman() {
        return _model.getHuman();
    }

    public void applicationStarted() {
        _canvas.applicationStarted();
    }
}