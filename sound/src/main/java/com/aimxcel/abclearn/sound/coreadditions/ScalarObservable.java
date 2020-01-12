package com.aimxcel.abclearn.sound.coreadditions;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public abstract class ScalarObservable extends SimpleObservable {

    public void addObserver( ScalarObserver observer ) {
        super.addObserver( observer );
    }

    public abstract double getValue();
}
