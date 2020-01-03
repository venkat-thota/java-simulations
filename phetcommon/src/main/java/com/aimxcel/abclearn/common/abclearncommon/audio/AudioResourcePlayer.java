

package com.aimxcel.abclearn.common.abclearncommon.audio;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

public class AudioResourcePlayer {

    protected final AbcLearnResources simResourceLoader;
    private boolean enabled;

    public AudioResourcePlayer( AbcLearnResources simResourceLoader, boolean enabled ) {
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
            AbcLearnCommonResources.getInstance().getAudioClip( resourceName ).play();
        }
    }
}
