package com.aimxcel.abclearn.games;

import com.aimxcel.abclearn.common.aimxcelcommon.audio.AudioResourcePlayer;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class GameAudioPlayer extends AudioResourcePlayer {

    private static boolean inited;

    public GameAudioPlayer( boolean enabled ) {
        super( new AimxcelResources( "games" ), enabled );
        init();
    }

    public void init() {
        if ( !inited ) {
            inited = true;
        }
    }

    public void correctAnswer() {
        playSimAudio( "correctAnswer.wav" );
    }

    public void wrongAnswer() {
        playSimAudio( "wrongAnswer.wav" );
    }

    public void gameOverZeroScore() {
        playSimAudio( "wrongAnswer.wav" );
    }

    public void gameOverImperfectScore() {
        playSimAudio( "gameOver-imperfectScore.wav" );
    }

    public void gameOverPerfectScore() {
        playSimAudio( "gameOver-perfectScore.wav" );
    }
}