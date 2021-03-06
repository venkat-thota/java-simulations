
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.model.Human;

public class AliveCheckBox extends JCheckBox {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AliveCheckBox( final Human human ) {
        super( "Alive", human.isAlive() );
        human.addListener( new Human.Adapter() {
            public void aliveChanged() {
                setSelected( human.isAlive() );
            }
        } );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                human.setAlive( isSelected() );
            }
        } );
    }
}
