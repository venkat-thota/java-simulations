
package com.aimxcel.abclearn.recordandplayback.test;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.recordandplayback.gui.RecordAndPlaybackControlPanel;
import com.aimxcel.abclearn.recordandplayback.model.RecordAndPlaybackModel;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

/**
 * This application shows the minimal setup required to configure and use the recordandplayback library in a AimxcelApplication.
 *
 * @author Sam Reid
 */
public class TestRecordAndPlaybackApplication extends AimxcelApplication {

    public TestRecordAndPlaybackApplication( AimxcelApplicationConfig config ) {
        super( config );
        addModule( new TestRecordAndPlaybackModule() );
    }

    private class TestRecordAndPlaybackModule extends Module {
        private TestRecordAndPlaybackModel model = new TestRecordAndPlaybackModel();

        public TestRecordAndPlaybackModule() {
            super( "test record and playback", new SwingClock( 30, 1.0 ) );
            TestRecordAndPlaybackSimulationPanel simPanel = new TestRecordAndPlaybackSimulationPanel( model );
            setSimulationPanel( simPanel );

            //it doesn't matter how you wire up the model to update with each clock tick, here is one way
            getClock().addClockListener( new ClockAdapter() {
                public void simulationTimeChanged( ClockEvent clockEvent ) {
                    model.stepInTime( clockEvent.getSimulationTimeChange() );
                }
            } );

            //use the record and playback control panel
            setClockControlPanel( new RecordAndPlaybackControlPanel<TestState>( model, simPanel, 1000 ) );
        }
    }

    /**
     * The state that gets recorded, which should be immutable.  In this sample application, we just record the location of the particle.
     */
    public static class TestState {
        private double x;
        private double y;

        public TestState( double x, double y ) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private class TestRecordAndPlaybackSimulationPanel extends AimxcelPCanvas {
        private TestRecordAndPlaybackSimulationPanel( final TestRecordAndPlaybackModel model ) {
            ParticleNode particleNode = new ParticleNode( model.getParticle() );

            //when the user starts dragging the object, start recording
            //note that this cannot be an attachment to the model's normal movement listener, since that is updated during playback
            //alternatively, you could add a new listener interface to the model object such as Particle.userDragged
            //to ensure communication happens through model notifications
            particleNode.addInputEventListener( new PBasicInputEventHandler() {
                public void mouseDragged( PInputEvent event ) {
                    model.startRecording();
                }
            } );
            addScreenChild( particleNode );
        }
    }

    private class TestRecordAndPlaybackModel extends RecordAndPlaybackModel<TestState> {
        private Particle particle = new Particle();

        protected TestRecordAndPlaybackModel() {
            super( 1000 );
        }

        public Particle getParticle() {
            return particle;
        }

        /**
         * Update the physics and return the resultant state for recording.
         *
         * @param simulationTimeChange the amount of time to update the simulation (in whatever units the simulation model is using).
         * @return the new state memento
         */
        public TestState step( double simulationTimeChange ) {
            //first step is to update state, apply physics, whatever
            //however, in this example, the movement is totally user controlled, so there is no physics update in this step

            return new TestState( particle.getX(), particle.getY() );
        }

        /**
         * Restore the specified state to the model (which should also be reflected in the view
         *
         * @param state the state to display
         */
        public void setPlaybackState( TestState state ) {
            particle.setPosition( state.getX(), state.getY() );
        }
    }

    public static void main( String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, "record-and-playback", TestRecordAndPlaybackApplication.class );
    }
}
