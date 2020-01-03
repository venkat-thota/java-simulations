
package edu.colorado.phet.signalcircuit;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

public class SignalCircuitStrings {
    public static final AbcLearnResources INSTANCE = new AbcLearnResources("signal-circuit");

    public static String getString( String s ) {
        return INSTANCE.getLocalizedString( s );
    }
}
