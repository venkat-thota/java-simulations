

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.ILayoutStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;


public class DefaultLayoutStrategy implements ILayoutStrategy {

    private int _justification;
    private Insets _insets;

    /**
     * Creates a layout strategy that that is left justifies the components.
     */
    public DefaultLayoutStrategy() {
        this( SwingConstants.LEFT );
    }

    /**
     * Creates a layout strategy with a specified justification of components.
     *
     * @param justification SwingConstants.LEFT, CENTER or RIGHT
     */
    public DefaultLayoutStrategy( int justification ) {
        this( justification, null /* insets */ );
    }

    /**
     * Creates a layout strategy with a specified justification of components.
     *
     * @param justification SwingConstants.LEFT, CENTER or RIGHT
     */
    public DefaultLayoutStrategy( int justification, Insets insets ) {
        super();
        _justification = justification;
        _insets = insets;
    }

    /**
     * Performs layout of a specified control.
     *
     * @param valueControl
     */
    public void doLayout( AbstractValueControl valueControl ) {

        // Get the components that will be part of the layout
        JComponent slider = valueControl.getSlider();
        JComponent textField = valueControl.getTextField();
        JComponent valueLabel = valueControl.getValueLabel();
        JComponent unitsLabel = valueControl.getUnitsLabel();

        // Label+textfield+units in a panel.
        JPanel valuePanel = new JPanel();
        EasyGridBagLayout valueLayout = new EasyGridBagLayout( valuePanel );
        valueLayout.setAnchor( GridBagConstraints.WEST );
        valuePanel.setLayout( valueLayout );
        if ( _insets != null ) {
            valueLayout.setInsets( _insets );
        }
        valueLayout.addComponent( valueLabel, 0, 0 );
        valueLayout.addComponent( textField, 0, 1 );
        valueLayout.addComponent( unitsLabel, 0, 2 );

        // Label+textfield+units above slider
        EasyGridBagLayout layout = new EasyGridBagLayout( valueControl );
        valueControl.setLayout( layout );
        int anchor = justificationToAnchor( _justification );
        layout.setAnchor( anchor );
        if ( _insets != null ) {
            layout.setInsets( _insets );
        }
        layout.addComponent( valuePanel, 0, 0 );
        layout.addComponent( slider, 1, 0 );
    }

    /*
    * Converts a justification value to a GridBagConstraint anchor value.
    */
    private int justificationToAnchor( int justification ) {
        int anchor = 0;
        if ( justification == SwingConstants.LEFT ) {
            anchor = GridBagConstraints.WEST;
        }
        else if ( justification == SwingConstants.CENTER ) {
            anchor = GridBagConstraints.CENTER;
        }
        else if ( justification == SwingConstants.RIGHT ) {
            anchor = GridBagConstraints.EAST;
        }
        else {
            throw new IllegalArgumentException( "unsupported alignment: " + justification );
        }
        return anchor;
    }
}
