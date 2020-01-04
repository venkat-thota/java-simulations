
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.periodictable;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;

/**
 * Interface used by PeriodicTableNode for highlighting one or more of the cells.
 *
 * @author Sam Reid
 * @author John Blanco
 */
public interface PeriodicTableAtom {

    //Gets the number of protons of the selected element
    int getNumProtons();

    //Add a listener that will be notified when the number of protons in the selected atom changes--this can cause the periodic table node to highlight a different element
    void addAtomListener( VoidFunction0 voidFunction0 );
}
