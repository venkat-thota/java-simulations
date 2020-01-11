
package com.aimxcel.abclearn.recordandplayback.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.recordandplayback.model.RecordAndPlaybackModel;


public class ModePanel<T> extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModePanel( final RecordAndPlaybackModel<T> model ) {
        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        setBackground( new Color( 0, 0, 0, 0 ) );
        final JRadioButton recordingButton = new JRadioButton( AimxcelCommonResources.getString( "Common.record" ), model.isRecord() );
        recordingButton.setBackground( new Color( 0, 0, 0, 0 ) );
        recordingButton.setFont( new AimxcelFont( 15, true ) );
        recordingButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                model.setRecord( true );
            }
        } );

        final JRadioButton playbackButton = new JRadioButton( AimxcelCommonResources.getString( "Common.playback" ), model.isPlayback() );
        playbackButton.setFont( new AimxcelFont( 15, true ) );
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