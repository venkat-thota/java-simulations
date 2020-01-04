
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.kit;

import java.awt.Color;

import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.ArrowButtonNode;

/**
 * Button for moving to the previous kit.
 *
 * @author Sam Reid
 */
public class PreviousKitButton extends ArrowButtonNode {
    public PreviousKitButton( Color buttonColor ) {
        super( Orientation.LEFT, new ColorScheme( buttonColor ) );
    }
}