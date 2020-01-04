

package com.aimxcel.abclearn.common.aimxcelcommon.tests.utils;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageDebugFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageDebugFrame( Image im ) {
        setContentPane( new JLabel( new ImageIcon( im ) ) );
        pack();
    }
}