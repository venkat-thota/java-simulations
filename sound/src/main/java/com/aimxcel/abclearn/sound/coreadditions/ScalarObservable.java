// Copyright 2002-2011, University of Colorado

/**
 * Class: ScalarObservable
 * Package: edu.colorado.phet.coreadditions
 * Author: Another Guy
 * Date: Sep 8, 2004
 */
package com.aimxcel.abclearn.sound.coreadditions;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public abstract class ScalarObservable extends SimpleObservable {

    public void addObserver( ScalarObserver observer ) {
        super.addObserver( observer );
    }

    public abstract double getValue();
}
