


package com.aimxcel.abclearn.theramp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.theramp.common.AudioSourceDataLinePlayer;



public class AudioEnabledController {
    private RampModule module;
    private JCheckBox checkBox;

    public AudioEnabledController( final RampModule module ) {
        this.module = module;
        checkBox = new JCheckBox( TheRampStrings.getString( "controls.sound" ), true );
        checkBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                AudioSourceDataLinePlayer.setAudioEnabled( checkBox.isSelected() );
            }
        } );
        AudioSourceDataLinePlayer.addListener( new AudioSourceDataLinePlayer.Listener() {
            public void propertyChanged() {
                checkBox.setSelected( AudioSourceDataLinePlayer.isAudioEnabled() );
            }
        } );
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }
}
