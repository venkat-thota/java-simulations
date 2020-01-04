
package edu.colorado.phet.common.motion.charts;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.PlayPauseButton;

import edu.colorado.phet.recordandplayback.model.RecordAndPlaybackModel;
import edu.umd.cs.piccolo.PNode;

/**
 * This is a small icon button that can be used for play/pause.  It is used in Moving Man, Forces and Motion, Ramps next to chart control panels
 * so that the user has a control nearby to the chart where they have typed in new values.
 * There was contention about whether this component would be useful since it duplicates functionality in the control panel, but interviews suggest that it is useful.
 *
 * @author Sam Reid
 */
public class GoButton extends PNode {
    public GoButton( final RecordAndPlaybackModel recordAndPlaybackModel, final BooleanProperty visible ) {
        final PlayPauseButton button = new PlayPauseButton( 30 );
        SimpleObserver updateButtonState = new SimpleObserver() {
            public void update() {
                button.setPlaying( !recordAndPlaybackModel.isPaused() );
            }
        };
        recordAndPlaybackModel.addObserver( updateButtonState );
        updateButtonState.update();
        button.addListener( new PlayPauseButton.Listener() {
            public void playbackStateChanged() {
                recordAndPlaybackModel.setPaused( !button.isPlaying() );
            }
        } );
        final SimpleObserver observer = new SimpleObserver() {
            public void update() {
                button.setVisible( visible.get() );
            }
        };
        observer.update();
        visible.addObserver( observer );
        recordAndPlaybackModel.addObserver( observer );
        addChild( button );
    }
}
