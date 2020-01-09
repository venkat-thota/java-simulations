package com.aimxcel.abclearn.sound;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.aimxcel.abclearn.sound.model.WaveMedium;
import com.aimxcel.abclearn.sound.model.Wavefront;
import com.aimxcel.abclearn.sound.view.SingleSourceApparatusPanel;
import com.aimxcel.abclearn.sound.view.SoundControlPanel;
import com.aimxcel.abclearn.sound.view.WaveMediumGraphic;

/**
 * A module with a single speaker source
 */
public abstract class SingleSourceModule extends SoundModule {
    
    private final SingleSourceApparatusPanel apparatusPanel;
    private final WaveMedium waveMedium;

    /**
     * @param application
     * @param name
     */
    protected SingleSourceModule( String name ) {
        super( name );
        apparatusPanel = new SingleSourceApparatusPanel( getSoundModel(), getClock() );
        setApparatusPanel( apparatusPanel );

        waveMedium = getSoundModel().getWaveMedium();
        WaveMediumGraphic waveMediumGraphic = new WaveMediumGraphic( waveMedium, apparatusPanel, this );
        apparatusPanel.addGraphic( waveMediumGraphic, 7 );
        Point2D.Double audioSource = new Point2D.Double( SoundConfig.s_wavefrontBaseX,
                                                         SoundConfig.s_wavefrontBaseY );
        waveMediumGraphic.initLayout( audioSource,
                                      SoundConfig.s_wavefrontHeight,
                                      SoundConfig.s_wavefrontRadius );
        this.setControlPanel( new SoundControlPanel( this ) );
    }

    protected void resetWaveMediumGraphic() {
        ArrayList wavefronts = waveMedium.getWavefronts();
        for( int i = 0; i < wavefronts.size(); i++ ) {
            Wavefront wavefront = (Wavefront)wavefronts.get( i );
            wavefront.clear();
        }
    }
}