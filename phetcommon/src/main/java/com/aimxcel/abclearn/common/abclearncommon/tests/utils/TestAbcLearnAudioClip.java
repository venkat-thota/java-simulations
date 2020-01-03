
package com.aimxcel.abclearn.common.abclearncommon.tests.utils;

import com.aimxcel.abclearn.common.abclearncommon.audio.AbcLearnAudioClip;

public class TestAbcLearnAudioClip {
    public static void main( String[] args ) throws Exception {
        // Note that this test cannot be run without the data from movingman!
        AbcLearnAudioClip clip = new AbcLearnAudioClip( "audio/smash0.wav" );

        clip.play();

        System.out.println( "Test" );

        Thread.sleep( 200 );

        // Required because JavaSound uses a non-daemon thread
        System.exit( 0 );
    }
}
