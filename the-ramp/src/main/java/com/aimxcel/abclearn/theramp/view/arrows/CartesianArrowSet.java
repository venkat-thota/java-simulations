package com.aimxcel.abclearn.theramp.view.arrows;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.theramp.view.BlockGraphic;
import com.aimxcel.abclearn.theramp.view.RampLookAndFeel;
import com.aimxcel.abclearn.theramp.view.RampPanel;



public class CartesianArrowSet extends AbstractArrowSet {

    public CartesianArrowSet( final RampPanel rampPanel, BlockGraphic blockGraphic ) {
        super( rampPanel, blockGraphic );
        RampLookAndFeel ralf = new RampLookAndFeel();

        final RampPhysicalModel rampPhysicalModel = rampPanel.getRampModule().getRampPhysicalModel();
        ForceArrowGraphic forceArrowGraphic = new ForceArrowGraphic( rampPanel, AbstractArrowSet.APPLIED, ralf.getAppliedForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getAppliedForce();
            }
        }, getBlockGraphic() );

        ForceArrowGraphic totalArrowGraphic = new ForceArrowGraphic( rampPanel, TOTAL, ralf.getNetForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getTotalForce();
            }
        }, getBlockGraphic() );

        ForceArrowGraphic frictionArrowGraphic = new ForceArrowGraphic( rampPanel, FRICTION, ralf.getFrictionForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getFrictionForce();
            }
        }, getBlockGraphic() );

        ForceArrowGraphic gravityArrowGraphic = new ForceArrowGraphic( rampPanel, WEIGHT, ralf.getWeightColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getGravityForce();
            }
        }, getBlockGraphic() );

        ForceArrowGraphic normalArrowGraphic = new ForceArrowGraphic( rampPanel, NORMAL, ralf.getNormalColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getNormalForce();
            }
        }, getBlockGraphic() );

        ForceArrowGraphic wallArrowGraphic = new ForceArrowGraphic( rampPanel, WALL, ralf.getWallForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                return rampPhysicalModel.getWallForce();
            }
        }, getBlockGraphic() );

        addForceArrowGraphic( gravityArrowGraphic );
        addForceArrowGraphic( normalArrowGraphic );

        addForceArrowGraphic( frictionArrowGraphic );
        addForceArrowGraphic( forceArrowGraphic );

        addForceArrowGraphic( wallArrowGraphic );

        addForceArrowGraphic( totalArrowGraphic );
       
    }

}
