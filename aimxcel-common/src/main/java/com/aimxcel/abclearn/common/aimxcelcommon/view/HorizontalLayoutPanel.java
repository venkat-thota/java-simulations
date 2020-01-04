
 package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class HorizontalLayoutPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gridBagConstraints;

    public HorizontalLayoutPanel() {
        setLayout( new GridBagLayout() );
        gridBagConstraints = new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 );
    }

    public GridBagConstraints getGridBagConstraints() {
        return gridBagConstraints;
    }

    public Component add( Component comp ) {
        super.add( comp, gridBagConstraints );
        gridBagConstraints.gridx++;
        return null;
    }

    public void setFill( int fill ) {
        gridBagConstraints.fill = fill;
    }

    public void setAnchor( int anchor ) {
        gridBagConstraints.anchor = anchor;
    }

    public void setInsets( Insets insets ) {
        gridBagConstraints.insets = insets;
    }
}
