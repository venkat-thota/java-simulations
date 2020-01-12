
package com.aimxcel.abclearn.glaciers.control;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public abstract class AbstractSubPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractSubPanel( String title, Color titleColor, Font titleFont ) {
        super();
        
        Border emptyBorder = BorderFactory.createEmptyBorder( 3, 3, 3, 3 ); // top, left, bottom, right
        TitledBorder titledBorder = new TitledBorder( title );
        titledBorder.setTitleFont( titleFont );
        titledBorder.setTitleColor( titleColor );
        titledBorder.setBorder( BorderFactory.createLineBorder( titleColor, 1 ) );
        Border compoundBorder = BorderFactory.createCompoundBorder( emptyBorder /* outside */, titledBorder /* inside */);
        setBorder( compoundBorder );
    }
}
