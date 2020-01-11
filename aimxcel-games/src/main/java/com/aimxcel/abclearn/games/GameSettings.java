
package com.aimxcel.abclearn.games;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ConstrainedIntegerProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.IntegerRange;

public class GameSettings {

    public final ConstrainedIntegerProperty level;
    public final BooleanProperty soundEnabled;
    public final BooleanProperty timerEnabled;

    public GameSettings( IntegerRange levelsRange, boolean soundEnabled, boolean timerEnabled ) {
        this.level = new ConstrainedIntegerProperty( levelsRange );
        this.soundEnabled = new BooleanProperty( soundEnabled );
        this.timerEnabled = new BooleanProperty( timerEnabled );
    }

    public int getNumberOfLevels() {
        return level.getMax() - level.getMin() + 1;
    }

    public void reset() {
        level.reset();
        soundEnabled.reset();
        timerEnabled.reset();
    }
}
