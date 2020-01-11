
package edu.colorado.phet.theramp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ShowZeroPointPotentialControl {
    private JCheckBox checkBox;
    private RampModule rampModule;

    public ShowZeroPointPotentialControl( final RampModule rampModule ) {
        this.rampModule = rampModule;

        checkBox = new JCheckBox( TheRampStrings.getString( "controls.show-zero-point-pe" ), rampModule.getRampPanel().getRampWorld().isPotentialEnergyZeroGraphicVisible() );
        checkBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                rampModule.getRampPanel().getRampWorld().setPotentialEnergyZeroGraphicVisible( checkBox.isSelected() );
            }
        } );
    }

    public JComponent getComponent() {
        return checkBox;
    }
}
