
package com.aimxcel.abclearn.motion.charts;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.PlayPauseButton;
import com.aimxcel.abclearn.recordandplayback.model.RecordAndPlaybackModel;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class GoButton extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
