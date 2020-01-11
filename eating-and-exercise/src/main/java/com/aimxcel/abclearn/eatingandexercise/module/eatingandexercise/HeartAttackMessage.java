
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class HeartAttackMessage extends WarningMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Human human;

    public HeartAttackMessage( Human human ) {
        super( "" );
        this.human = human;
        human.addListener( new Human.Adapter() {
            public void heartAttackProbabilityChanged() {
                updateMessage();
            }
        } );
        updateMessage();
    }

    private void updateMessage() {
        setText( getWarningMessage() );
        setVisible( human.getHeartAttackProbabilityPerDay() > 0 );
    }

    private String getWarningMessage() {
        return "<html>Increased risk of heart attack.</html>";
    }

    private String getLevel() {
        double heartAttackProbabilityPerDay = human.getHeartAttackProbabilityPerDay();
        if ( heartAttackProbabilityPerDay < 0.1 ) {
            return "moderate";
        }
        else if ( heartAttackProbabilityPerDay < 0.3 ) {
            return "high";
        }
        else {
            return "extreme";
        }
    }
}
