package com.aimxcel.abclearn.theramp.view.arrows;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.theramp.view.BlockGraphic;
import com.aimxcel.abclearn.theramp.view.RampLookAndFeel;
import com.aimxcel.abclearn.theramp.view.RampPanel;



public class ParallelArrowSet extends AbstractArrowSet {

    public ParallelArrowSet( final RampPanel component, BlockGraphic blockGraphic ) {
        super( component, blockGraphic );
        RampLookAndFeel ralf = new RampLookAndFeel();
        final RampPhysicalModel rampPhysicalModel = component.getRampModule().getRampPhysicalModel();
        String sub = "||";
        ForceArrowGraphic forceArrowGraphic = new ForceArrowGraphic( component, APPLIED, ralf.getAppliedForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector appliedForce = rampPhysicalModel.getAppliedForce();
                return appliedForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic totalArrowGraphic = new ForceArrowGraphic( component, TOTAL, ralf.getNetForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getTotalForce();
                return totalForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic frictionArrowGraphic = new ForceArrowGraphic( component, FRICTION, ralf.getFrictionForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getFrictionForce();
                return totalForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic gravityArrowGraphic = new ForceArrowGraphic( component, WEIGHT, ralf.getWeightColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getGravityForce();
                return totalForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic normalArrowGraphic = new ForceArrowGraphic( component, NORMAL, ralf.getNormalColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getNormalForce();
                return totalForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic wallArrowGraphic = new ForceArrowGraphic( component, WALL, ralf.getWallForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getWallForce();
                return totalForce.toParallelVector();
            }
        }, getBlockGraphic(), sub );

        addForceArrowGraphic( gravityArrowGraphic );
        addForceArrowGraphic( normalArrowGraphic );

        addForceArrowGraphic( frictionArrowGraphic );
        addForceArrowGraphic( forceArrowGraphic );
        addForceArrowGraphic( wallArrowGraphic );

        addForceArrowGraphic( totalArrowGraphic );

       
    }

}
