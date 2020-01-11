
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.view.LabelNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class TimeoutWarningMessage extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long lastVisibilityRestart;

    public TimeoutWarningMessage( String message ) {
        LabelNode labelNode = new LabelNode( message );
        addChild( labelNode );

        Timer timer = new Timer( 100, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                update();
                updateVisibility();
            }
        } );
        timer.start();
    }

    protected void update() {
    }

    protected void updateVisibility() {
        setVisible( System.currentTimeMillis() - lastVisibilityRestart < 5000 );
    }

    public long getLastVisibilityRestart() {
        return lastVisibilityRestart;
    }

    protected void resetVisibleTime() {
        this.lastVisibilityRestart = System.currentTimeMillis();
        updateVisibility();
    }

}