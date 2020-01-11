
package com.aimxcel.abclearn.eatingandexercise.model;


public class DefaultHumanUpdate implements HumanUpdate {
    private CompositeHumanUpdate compositeHumanUpdate = new CompositeHumanUpdate();

    public DefaultHumanUpdate() {
        compositeHumanUpdate.add( new MuscleAndFatMassLoss2() );
        compositeHumanUpdate.add( new MuscleGainedFromExercising() );
    }

    public void update( Human human, double dt ) {
        compositeHumanUpdate.update( human, dt );
    }
}
