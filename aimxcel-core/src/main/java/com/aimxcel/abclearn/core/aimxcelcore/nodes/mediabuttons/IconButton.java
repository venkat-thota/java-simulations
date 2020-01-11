
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

/**
 * Draws an icon Shape over the button.
 */
public class IconButton extends AbstractMediaButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
