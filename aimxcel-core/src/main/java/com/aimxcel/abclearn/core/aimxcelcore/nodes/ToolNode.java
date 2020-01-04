
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PDimension;


public abstract class ToolNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract void dragAll( PDimension viewDelta );
	public PNode[] getDroppableComponents() {
        return new PNode[] { this };
    }
}
