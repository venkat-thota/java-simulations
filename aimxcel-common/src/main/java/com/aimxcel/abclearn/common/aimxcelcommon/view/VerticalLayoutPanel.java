
 package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;


public class VerticalLayoutPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gridBagConstraints;

    public VerticalLayoutPanel() {
        setLayout( new GridBagLayout() );
        gridBagConstraints = new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 );
    }

    public GridBagConstraints getGridBagConstraints() {
        return gridBagConstraints;
    }

    public Component add( Component comp ) {
        super.add( comp, gridBagConstraints );
        gridBagConstraints.gridy++;
        return null;
    }

    public void setGridY( int gridy ) {
        this.gridBagConstraints.gridy = gridy;
    }

    public void setFill( int fill ) {
        gridBagConstraints.fill = fill;
    }

    public void setFillHorizontal() {
        setFill( GridBagConstraints.HORIZONTAL );
    }

    public void setFillNone() {
        setFill( GridBagConstraints.NONE );
    }

    public void setAnchor( int anchor ) {
        gridBagConstraints.anchor = anchor;
    }

    public void setInsets( Insets insets ) {
        gridBagConstraints.insets = insets;
    }

    public int getFill() {
        return gridBagConstraints.fill;
    }

    public Component addFullWidth( Component component ) {
        int fill = getFill();
        setFill( GridBagConstraints.HORIZONTAL );
        Component c = add( component );
        setFill( fill );
        return c;
    }
}
