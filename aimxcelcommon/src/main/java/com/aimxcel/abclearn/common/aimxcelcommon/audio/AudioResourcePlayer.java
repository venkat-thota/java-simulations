

package com.aimxcel.abclearn.common.aimxcelcommon.audio;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

public class AudioResourcePlayer {

    protected final AimxcelResources simResourceLoader;
    private boolean enabled;

    public AudioResourcePlayer( AimxcelResources simResourceLoader, boolean enabled ) {
        this.simResourceLoader = simResourceLoader;
        this.enabled = enabled;
    }

    /**
     * Is sound enabled?
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables sound.
     *
     * @param isEnabled
     */
    public void setEnabled( boolean isEnabled ) {
        this.enabled = isEnabled;
    }

    /**
     * Plays an audio resource using the sim-specific resource loader.
     *
     * @param resourceName
     */
    public void playSimAudio( String resourceName ) {
        if ( isEnabled() ) {
            simResourceLoader.getAudioClip( resourceName ).play();
        }
    }

    /**
     * Plays an audio resource using abcLearncommon's resource loader.
     *
     * @param resourceName
     */
    public void playCommonAudio( String resourceName ) {
        if ( isEnabled() ) {
            AimxcelCommonResources.getInstance().getAudioClip( resourceName ).play();
        }
    }
}
