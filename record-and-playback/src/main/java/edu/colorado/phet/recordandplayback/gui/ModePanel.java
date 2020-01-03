
package edu.colorado.phet.recordandplayback.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import edu.colorado.phet.recordandplayback.model.RecordAndPlaybackModel;

/**
 * The ModePanel indicates whether the sim is in record or playback mode; the record or playback text is highlighted
 * in red if the sim is indeed recording or playing back (may not be if recording time is full or sim is paused).
 *
 * @param <T> the model type
 * @author Sam Reid
 */
public class ModePanel<T> extends JPanel {

    public ModePanel( final RecordAndPlaybackModel<T> model ) {
        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        setBackground( new Color( 0, 0, 0, 0 ) );
        final JRadioButton recordingButton = new JRadioButton( AbcLearnCommonResources.getString( "Common.record" ), model.isRecord() );
        recordingButton.setBackground( new Color( 0, 0, 0, 0 ) );
        recordingButton.setFont( new AbcLearnFont( 15, true ) );
        recordingButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                model.setRecord( true );
            }
        } );

        final JRadioButton playbackButton = new JRadioButton( AbcLearnCommonResources.getString( "Common.playback" ), model.isPlayback() );
        playbackButton.setFont( new AbcLearnFont( 15, true ) );
        playbackButton.setBackground( new Color( 0, 0, 0, 0 ) );
        playbackButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                model.setRecord( false );
                model.rewind();
                model.setPaused( true );
            }
        } );

        add( recordingButton );
        add( playbackButton );

        model.addObserver( new SimpleObserver() {
            public void update() {
                recordingButton.setForeground( color( recordingButton.isSelected() && !model.isPaused() && !model.isRecordingFull() ) );
                playbackButton.setForeground( color( playbackButton.isSelected() && !model.isPaused() ) );
                recordingButton.setSelected( model.isRecord() );
                playbackButton.setSelected( model.isPlayback() );
            }
        } );
    }

    private Color color( boolean b ) {
        if ( b ) { return Color.red; }
        else { return Color.black; }
    }
}