
package com.aimxcel.abclearn.common.aimxcelcommon.tests.utils;

import com.aimxcel.abclearn.common.aimxcelcommon.audio.AimxcelAudioClip;

public class TestAbcLearnAudioClip {
    public static void main( String[] args ) throws Exception {
        // Note that this test cannot be run without the data from movingman!
        AimxcelAudioClip clip = new AimxcelAudioClip( "audio/smash0.wav" );

        clip.play();

        System.out.println( "Test" );

        Thread.sleep( 200 );

        // Required because JavaSound uses a non-daemon thread
        System.exit( 0 );
    }
}
