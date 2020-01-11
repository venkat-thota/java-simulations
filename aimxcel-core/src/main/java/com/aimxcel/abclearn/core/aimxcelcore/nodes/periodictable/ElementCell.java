
package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public abstract class ElementCell extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int atomicNumber;

    public ElementCell( int atomicNumber ) {
        this.atomicNumber = atomicNumber;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    //Callback that allows nodes to update after the table has been built.  This is used so that highlighted or larger cells can move themselves to the front so they aren't clipped on 2 sides
    public void tableInitComplete() {
    }
}
