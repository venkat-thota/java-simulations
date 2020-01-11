
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class GameOverDialog {
    private AimxcelFrame parentFrame;
    private Human human;
    private EatingAndExerciseModule module;

    public GameOverDialog( AimxcelFrame parentFrame, final Human human, EatingAndExerciseModule module ) {
        this.parentFrame = parentFrame;
        this.human = human;
        this.module = module;
        human.addListener( new Human.Adapter() {
            public void aliveChanged() {
                if ( !human.isAlive() ) {
                    String causeOfDeath = human.getCauseOfDeath();
                    showDialog( causeOfDeath );
                }
            }
        } );
    }

    private void showDialog( final String causeOfDeath ) {
        final Timer timer = new Timer( 1000, null );
        timer.setRepeats( false );

        timer.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                module.getClock().pause();
//                AimxcelOptionPane.showMessageDialog( parentFrame, "Game Over.  Body died from " + causeOfDeath + ". Click to start over." );
                Object[] options = {"Restart"};
                JOptionPane.showOptionDialog( parentFrame, "Game Over", "Game Over", JOptionPane.OK_OPTION,
                                              JOptionPane.WARNING_MESSAGE, null, options, options[0] ); //default button title
                module.resetAll();
            }
        } );
        timer.start();

    }

    public void start() {
    }
}
