

package com.aimxcel.abclearn.common.abclearncommon.audio;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

/**
 * Player for JAR audio resources.
 * Can play sim-specific or phetcommon sounds, can be enabled and disabled.
 * The downside of this implementation is that the resources can't be loaded statically,
 * so we can't verify at startup time that the resources exists.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
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
     * Plays an audio resource using phetcommon's resource loader.
     *
     * @param resourceName
     */
    public void playCommonAudio( String resourceName ) {
        if ( isEnabled() ) {
            AbcLearnCommonResources.getInstance().getAudioClip( resourceName ).play();
        }
    }
}
