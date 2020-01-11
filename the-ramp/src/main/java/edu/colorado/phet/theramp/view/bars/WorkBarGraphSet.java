package edu.colorado.phet.theramp.view.bars;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ModelViewTransform1D;
import edu.colorado.phet.theramp.TheRampStrings;
import edu.colorado.phet.theramp.model.RampPhysicalModel;
import edu.colorado.phet.theramp.model.ValueAccessor;
import edu.colorado.phet.theramp.view.RampPanel;



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
