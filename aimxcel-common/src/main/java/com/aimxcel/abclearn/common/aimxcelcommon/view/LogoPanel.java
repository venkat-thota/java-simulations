
 
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;

public class LogoPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String IMAGE_AIMXCEL_LOGO = AimxcelLookAndFeel.AIMXCEL_LOGO_120x50;

    private ImageIcon imageIcon;
    private JLabel titleLabel;

    public LogoPanel() {
        imageIcon = new ImageIcon( AimxcelCommonResources.getInstance().getImage( IMAGE_AIMXCEL_LOGO ) );
        titleLabel = new JLabel( imageIcon );
        titleLabel.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
        add( titleLabel );
    }
}
