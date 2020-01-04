
package com.aimxcel.abclearn.signalcircuit;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

public class SignalCircuitStrings {
    public static final AimxcelResources INSTANCE = new AimxcelResources("signal-circuit");

    public static String getString( String s ) {
        return INSTANCE.getLocalizedString( s );
    }
}
