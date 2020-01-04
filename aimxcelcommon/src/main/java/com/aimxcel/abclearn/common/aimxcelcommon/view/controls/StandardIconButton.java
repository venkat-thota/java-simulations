

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;

/**
 * StandardIconButton is the abstract base class for all buttons that display PhET's standard icons.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public abstract class StandardIconButton extends JButton {

    private StandardIconButton( String imageResourceName ) {
        super();
        Image image = AimxcelCommonResources.getInstance().getImage( imageResourceName );
        Icon icon = new ImageIcon( image );
        setIcon( icon );
        setOpaque( false );
        setMargin( new Insets( 0, 0, 0, 0 ) );
    }

    public static class CloseButton extends StandardIconButton {

        public CloseButton() {
            super( AimxcelCommonResources.IMAGE_CLOSE_BUTTON );
        }
    }

    public static class MinimizeButton extends StandardIconButton {

        public MinimizeButton() {
            super( AimxcelCommonResources.IMAGE_MINIMIZE_BUTTON );
        }
    }

    public static class MaximizeButton extends StandardIconButton {

        public MaximizeButton() {
            super( AimxcelCommonResources.IMAGE_MAXIMIZE_BUTTON );
        }
    }
}
