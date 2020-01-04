
package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;


public interface PeriodicTableAtom {

    //Gets the number of protons of the selected element
    int getNumProtons();

    //Add a listener that will be notified when the number of protons in the selected atom changes--this can cause the periodic table node to highlight a different element
    void addAtomListener( VoidFunction0 voidFunction0 );
}
