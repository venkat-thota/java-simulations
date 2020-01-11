
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;


public class MyVerticalLayoutPanel extends VerticalLayoutPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Component strut = Box.createVerticalStrut( 10 );

    public MyVerticalLayoutPanel() {
        addStrut();
    }

    public Component add( Component comp ) {
        remove( strut );
        Component c = super.add( comp );
        addStrut();
        return c;
    }

    private void addStrut() {
        add( strut, new GridBagConstraints( 0, GridBagConstraints.RELATIVE, 1, 1, 1E6, 1E6, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
    }
}
