package com.aimxcel.abclearn.theramp.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.HorizontalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.theramp.RampModule;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class AppliedForceSimpleControl extends PNode {
    private RampModule module;
    private RampPanel rampPanel;

    public AppliedForceSimpleControl( final RampModule module, RampPanel rampPanel ) {
        this.module = module;
        this.rampPanel = rampPanel;
        double maxValue = 3000;
        HorizontalLayoutPanel horizontalLayoutPanel = new HorizontalLayoutPanel();

        VerticalLayoutPanel verticalLayoutPanel = new VerticalLayoutPanel();
        verticalLayoutPanel.add( new JLabel( TheRampStrings.getString( "forces.applied.n" ) ) );

        SpinnerNumberModel model = new SpinnerNumberModel( module.getRampPhysicalModel().getAppliedForceScalar(), -maxValue, maxValue, 100 );

        final JSpinner spinner = new JSpinner( model );
        spinner.setEditor( new JSpinner.NumberEditor( spinner, "0.00" ) );
        verticalLayoutPanel.add( spinner );
        horizontalLayoutPanel.add( verticalLayoutPanel );
        PSwing pSwing = new PSwing( horizontalLayoutPanel );
        addChild( pSwing );
        spinner.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                Number value = (Number) spinner.getValue();
                module.setAppliedForce( value.doubleValue() );
            }
        } );
        module.getRampPhysicalModel().addListener( new RampPhysicalModel.Adapter() {
            public void appliedForceChanged() {
                Double value = new Double( module.getRampPhysicalModel().getAppliedForceScalar() );
                if ( !spinner.getValue().equals( value ) ) {
                    spinner.setValue( value );
                    repaint();
                }
            }

        } );
    }
}
