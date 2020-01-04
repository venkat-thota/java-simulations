
package com.aimxcel.abclearn.core.aimxcelcore.nodes.kit;

import java.awt.Color;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowButtonNode;


public class NextKitButton extends ArrowButtonNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NextKitButton( Color buttonColor ) {
        super( Orientation.RIGHT, new ColorScheme( buttonColor ) );
    }
}