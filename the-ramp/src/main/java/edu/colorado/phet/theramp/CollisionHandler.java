

package edu.colorado.phet.theramp;

import com.aimxcel.abclearn.common.aimxcelcommon.audio.AimxcelAudioClip;
import edu.colorado.phet.theramp.model.Block;
import edu.colorado.phet.theramp.model.Collision;


public class CollisionHandler extends Block.Adapter {
    private RampModule rampModule;
    private AimxcelAudioClip smash0;
    private AimxcelAudioClip smash1;
    private AimxcelAudioClip smash2;

    public CollisionHandler( RampModule rampModule ) {
        this.rampModule = rampModule;
        smash0 = new AimxcelAudioClip( "the-ramp/audio/smash0.wav" );
        smash1 = new AimxcelAudioClip( "the-ramp/audio/smash1.wav" );
        smash2 = new AimxcelAudioClip( "the-ramp/audio/smash2.wav" );
    }

    public void collisionOccurred( Collision collision ) {
        handleAudio( collision );
        double dp = collision.getMomentumChange();
        rampModule.getRampPhysicalModel().setWallForce( dp / collision.getDt() );
    }

    private void handleAudio( Collision collision ) {
        double mom = collision.getAbsoluteMomentumChange();
        if ( mom < 50 ) {
       
        }
        else if ( mom < 2000 ) {
            smash0.play();
          
        }
        else if ( mom < 4000 ) {
            smash1.play();

        }
        else {
            smash2.play();

        }
    }
}
