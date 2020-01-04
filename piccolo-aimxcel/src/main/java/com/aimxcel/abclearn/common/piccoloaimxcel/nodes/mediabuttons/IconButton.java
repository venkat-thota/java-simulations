
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.mediabuttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.AimxcelPPath;

import edu.umd.cs.piccolo.nodes.PPath;

/**
 * Draws an icon Shape over the button.
 */
public class IconButton extends AbstractMediaButton {
    private PPath iconNode;

    public IconButton( int buttonHeight ) {
        super( buttonHeight );
        iconNode = new AimxcelPPath( Color.BLACK, new BasicStroke( 1 ), Color.LIGHT_GRAY );
        iconNode.setPickable( false );//let events fall through to parent
        addChild( iconNode );
    }

    protected void updateImage() {
        super.updateImage();
        iconNode.setPaint( isEnabled() ? Color.black : Color.gray );
    }

    public void setIconPath( Shape shape ) {
        iconNode.setPathTo( shape );
    }
}
