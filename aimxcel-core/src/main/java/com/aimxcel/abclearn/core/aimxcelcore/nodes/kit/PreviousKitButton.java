
package com.aimxcel.abclearn.core.aimxcelcore.nodes.kit;

import java.awt.Color;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowButtonNode;

public class PreviousKitButton extends ArrowButtonNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PreviousKitButton( Color buttonColor ) {
        super( Orientation.LEFT, new ColorScheme( buttonColor ) );
    }
}