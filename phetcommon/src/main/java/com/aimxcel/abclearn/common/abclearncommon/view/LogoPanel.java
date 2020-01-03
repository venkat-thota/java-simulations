

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */

package com.aimxcel.abclearn.common.abclearncommon.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;

import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnLookAndFeel;


/**
 * LogoPanel is the panel used to show the PhET logo.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 * @version $Revision$
 */
public class LogoPanel extends JPanel {

    public static final String IMAGE_ABC_LEARN_LOGO = AbcLearnLookAndFeel.ABC_LEARN_LOGO_120x50;

    private ImageIcon imageIcon;
    private JLabel titleLabel;

    public LogoPanel() {
        imageIcon = new ImageIcon( AbcLearnCommonResources.getInstance().getImage( IMAGE_ABC_LEARN_LOGO ) );
        titleLabel = new JLabel( imageIcon );
        titleLabel.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
        add( titleLabel );
    }
}
