package com.aimxcel.abclearn.theramp.view.bars;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ModelViewTransform1D;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.theramp.model.ValueAccessor;
import com.aimxcel.abclearn.theramp.view.RampPanel;



public class EnergyBarGraphSet extends BarGraphSet {
    public EnergyBarGraphSet( RampPanel rampPanel, RampPhysicalModel rampPhysicalModel, ModelViewTransform1D transform1D ) {
        super( rampPanel, rampPhysicalModel, TheRampStrings.getString( "energy.energy" ), transform1D );
        ValueAccessor[] energyAccess = new ValueAccessor[] {
                new ValueAccessor.KineticEnergy( super.getLookAndFeel() ), new ValueAccessor.PotentialEnergy( getLookAndFeel() ),
                new ValueAccessor.ThermalEnergy( getLookAndFeel() ), new ValueAccessor.TotalEnergy( getLookAndFeel() )
        };
        finishInit( energyAccess );
    }
}
