
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.kit;

import java.awt.Color;

import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.ArrowButtonNode;

/**
 * Button for moving to the next kit.
 *
 * @author Sam Reid
 */
public class NextKitButton extends ArrowButtonNode {
    public NextKitButton( Color buttonColor ) {
        super( Orientation.RIGHT, new ColorScheme( buttonColor ) );
    }
}