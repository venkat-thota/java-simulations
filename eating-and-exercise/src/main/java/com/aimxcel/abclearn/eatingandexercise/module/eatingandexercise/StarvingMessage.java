
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class StarvingMessage extends WarningMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Human human;

    public StarvingMessage( final Human human ) {
        super( "<html>Starving!<html>" );

        this.human = human;

        human.addListener( new Human.Adapter() {
            public void starvingChanged() {
                updateVisibility();
            }
        } );
        updateVisibility();
    }

    protected void updateVisibility() {
        setVisible( human.isStarving() );
    }
}