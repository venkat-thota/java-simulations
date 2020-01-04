
package edu.colorado.phet.recordandplayback.gui;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;

import edu.colorado.phet.recordandplayback.model.RecordAndPlaybackModel;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolox.pswing.PSwing;

/**
 * The PlaybackSpeedSlider allows the user to view and modify the playback speed for a recorded sequence.
 *
 * @param <T>
 * @author Sam Reid
 */
public class PlaybackSpeedSlider<T> extends PNode {
    private RecordAndPlaybackModel<T> model;

    public PlaybackSpeedSlider( final RecordAndPlaybackModel<T> model ) {
        this.model = model;
        addInputEventListener( new CursorHandler() );

        final JSlider slider = new JSlider();
        slider.setBackground( new Color( 0, 0, 0, 0 ) );
        final Function.LinearFunction transform = new Function.LinearFunction( slider.getMinimum(), slider.getMaximum(), 0.25, 2.0 );

        Hashtable<Integer, JLabel> dict = new Hashtable<Integer, JLabel>();

        dict.put( slider.getMinimum(), new JLabel( AimxcelCommonResources.getString( "Common.time.slow" ) ) );
        dict.put( slider.getMaximum(), new JLabel( AimxcelCommonResources.getString( "Common.time.fast" ) ) );

        slider.setLabelTable( dict );
        slider.setPaintLabels( true );
        PSwing playbackSpeedSlider = new PSwing( slider );
        addChild( playbackSpeedSlider );
        slider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                model.setPlaybackSpeed( transform.evaluate( slider.getValue() ) );
            }
        } );

        SimpleObserver updatePlaybackSliderVisible = new SimpleObserver() {
            public void update() {
                slider.setValue( (int) transform.createInverse().evaluate( model.getPlaybackSpeed() ) );
                setVisible( model.isPlayback() );
            }
        };
        model.addObserver( updatePlaybackSliderVisible );
        updatePlaybackSliderVisible.update();
    }
}