
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JMenuBar;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;


public class SimSharingJMenuBar extends JMenuBar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final BufferedImage networkTransmitImage = AimxcelCommonResources.getImage( "status/network/network-transmit-2.png" );

    public SimSharingJMenuBar() {
    }

    @Override protected void paintComponent( Graphics g ) {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D) g;

        //Start drawing a gradient to make it easy to spot from across the classroom, since Teachers and TA's need to be able to easily detect which mode the student is running
        float fractionToStartIndicator = 0.7f;
        g2.setPaint( new GradientPaint( getWidth() * fractionToStartIndicator, 0, new Color( 0, 0, 0, 0 ), getWidth(), 0, new Color( 0, 255, 0, 150 ) ) );
        g2.fillRect( (int) ( getWidth() * fractionToStartIndicator ), 0, getWidth() / 2, getHeight() );

        //Indicate the status.  Right now there is just one status identified "transmitting to network"
        g2.drawImage( networkTransmitImage, getWidth() - networkTransmitImage.getWidth() - 5, getHeight() / 2 - networkTransmitImage.getHeight() / 2, null );
    }
}