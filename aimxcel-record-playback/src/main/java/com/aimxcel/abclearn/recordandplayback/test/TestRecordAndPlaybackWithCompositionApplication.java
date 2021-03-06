
package com.aimxcel.abclearn.recordandplayback.test;

import java.util.Observable;

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

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

public class TestRecordAndPlaybackWithCompositionApplication extends AimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestRecordAndPlaybackWithCompositionApplication( AimxcelApplicationConfig config ) {
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
            setClockControlPanel( new RecordAndPlaybackControlPanel<TestState>( model.getRecordAndPlaybackModel(), simPanel, 1000 ) );
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
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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

    /**
     * Model class which contains a RecordAndPlaybackModel as a component
     */
    private class TestRecordAndPlaybackModel extends Observable {
        private Particle particle = new Particle();
        private RecordAndPlaybackModel<TestState> recordAndPlaybackModel = new RecordAndPlaybackModel<TestState>( 1000 ) {
            public TestState step( double simulationTimeChange ) {
                return new TestState( particle.getX(), particle.getY() );
            }

            public void setPlaybackState( TestState state ) {
                particle.setPosition( state.getX(), state.getY() );
            }
        };

        protected TestRecordAndPlaybackModel() {
        }

        public Particle getParticle() {
            return particle;
        }

        public TestState stepRecording( double simulationTimeChange ) {
            return recordAndPlaybackModel.step( simulationTimeChange );
        }

        public void stepInTime( double simulationTimeChange ) {
            recordAndPlaybackModel.stepInTime( simulationTimeChange );
        }

        public RecordAndPlaybackModel<TestState> getRecordAndPlaybackModel() {
            return recordAndPlaybackModel;
        }

        public void startRecording() {
            recordAndPlaybackModel.startRecording();
        }
    }

    public static void main( String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, "record-and-playback", TestRecordAndPlaybackWithCompositionApplication.class );
    }
}