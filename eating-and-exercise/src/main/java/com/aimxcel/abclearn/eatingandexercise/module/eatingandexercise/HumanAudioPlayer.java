
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

/**
 * Created by: Sam
 * Jun 26, 2008 at 4:12:57 PM
 */
public class HumanAudioPlayer {
    private Human human;

    public HumanAudioPlayer( final Human human ) {
        this.human = human;
        human.addListener( new Human.Adapter() {
            public void aliveChanged() {
                if ( !human.isAlive() ) {
                    EatingAndExerciseResources.getResourceLoader().getAudioClip( "killed.wav" ).play();
                }
            }
        } );
    }

    public void start() {
    }
}
