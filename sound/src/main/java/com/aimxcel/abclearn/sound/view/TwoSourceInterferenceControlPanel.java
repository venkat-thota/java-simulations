// Copyright 2002-2011, University of Colorado

/**
 * Class: TwoSourceInterferenceControlPanel
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 13, 2004
 */
package com.aimxcel.abclearn.sound.view;

import com.aimxcel.abclearn.sound.SoundModule;

public class TwoSourceInterferenceControlPanel extends SoundControlPanel {

    public TwoSourceInterferenceControlPanel( SoundModule module ) {
        super( module );
        this.addPanel( new AudioControlPanel( module, false ));
        setAmplitude( 1.0 );
    }
}