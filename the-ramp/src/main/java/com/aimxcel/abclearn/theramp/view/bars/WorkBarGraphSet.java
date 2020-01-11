package com.aimxcel.abclearn.theramp.view.bars;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ModelViewTransform1D;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.theramp.model.ValueAccessor;
import com.aimxcel.abclearn.theramp.view.RampPanel;



public class WorkBarGraphSet extends BarGraphSet {
    public WorkBarGraphSet( RampPanel rampPanel, RampPhysicalModel rampPhysicalModel, ModelViewTransform1D transform1D ) {
        super( rampPanel, rampPhysicalModel, TheRampStrings.getString( "energy.work" ), transform1D );
        ValueAccessor[] workAccess = new ValueAccessor[] {
                new ValueAccessor.TotalWork( getLookAndFeel() ),
                new ValueAccessor.GravityWork( getLookAndFeel() ),
                new ValueAccessor.FrictiveWork( getLookAndFeel() ),
                new ValueAccessor.AppliedWork( getLookAndFeel() )
        };
        super.finishInit( workAccess );
    }
}
