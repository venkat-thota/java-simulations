package com.aimxcel.abclearn.sound.view;

import com.aimxcel.abclearn.sound.SoundModule;

public class TwoSourceInterferenceControlPanel extends SoundControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TwoSourceInterferenceControlPanel( SoundModule module ) {
        super( module );
        this.addPanel( new AudioControlPanel( module, false ));
        setAmplitude( 1.0 );
    }
}