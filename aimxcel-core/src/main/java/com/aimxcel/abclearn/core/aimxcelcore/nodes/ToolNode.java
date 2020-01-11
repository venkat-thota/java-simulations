
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;


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
