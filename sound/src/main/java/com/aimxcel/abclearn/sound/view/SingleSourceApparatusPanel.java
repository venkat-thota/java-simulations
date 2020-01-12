package com.aimxcel.abclearn.sound.view;

import com.aimxcel.abclearn.sound.SoundConfig;
import com.aimxcel.abclearn.sound.model.SoundModel;
import com.aimxcel.abclearn.sound.model.WaveMedium;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public class SingleSourceApparatusPanel extends SoundApparatusPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpeakerGraphic speakerGraphic;


    /**
     * @param model
     */
    public SingleSourceApparatusPanel( SoundModel model, IClock clock ) {
        super( model, clock );
        this.setBackground( SoundConfig.MIDDLE_GRAY );

        // Set up the speaker
        final WaveMedium waveMedium = model.getWaveMedium();
        speakerGraphic = new SpeakerGraphic( this, waveMedium );
        this.addGraphic( speakerGraphic, 8 );
        waveMedium.addObserver( new SimpleObserver() {
            private int s_maxSpeakerConeExcursion = 6;

            public void update() {
                int coneOffset = (int)( waveMedium.getAmplitudeAt( 0 ) / SoundConfig.s_maxAmplitude * s_maxSpeakerConeExcursion );
                speakerGraphic.setConePosition( coneOffset );
            }
        } );
    }
}
