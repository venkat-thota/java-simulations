
package com.aimxcel.abclearn.theramp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.theramp.model.RampObject;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;


public class ObjectSelectionPanel extends JPanel {

    private Font selectedFont = new Font( AimxcelFont.getDefaultFontName(), Font.BOLD, 14 );
    private Font normalFont = new Font( AimxcelFont.getDefaultFontName(), Font.PLAIN, 12 );

    public ObjectSelectionPanel( final RampModule rampModule, final RampObject[] imageElements ) {
        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        ButtonGroup bg = new ButtonGroup();
        final JRadioButton[] jRadioButtons = new JRadioButton[imageElements.length];
        for ( int i = 0; i < imageElements.length; i++ ) {
            final RampObject imageElement = imageElements[i];
            BufferedImage image = null;
            try {
                image = ImageLoader.loadBufferedImage( imageElements[i].getLocation() );
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
            image = BufferedImageUtils.rescaleYMaintainAspectRatio( image, 35 );
            ImageIcon icon = new ImageIcon( image );
            JRadioButton jRadioButton = new JRadioButton( getIconText( imageElement ), icon );
            jRadioButtons[i] = jRadioButton;
            if ( i == 0 ) {
                jRadioButton.setSelected( true );
                jRadioButton.setFont( selectedFont );
            }
            else {
                jRadioButton.setFont( normalFont );
            }
            bg.add( jRadioButton );
            final int i1 = i;
            jRadioButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {

                    rampModule.setObject( imageElement );
                    for ( int j = 0; j < jRadioButtons.length; j++ ) {
                        JRadioButton radioButton = jRadioButtons[j];
                        if ( j == i1 ) {
                            radioButton.setFont( selectedFont );
                        }
                        else {
                            radioButton.setFont( normalFont );
                        }
                    }
                }
            } );
            add( jRadioButton );

        }
        setBorder( BorderFactory.createTitledBorder( BorderFactory.createRaisedBevelBorder(), TheRampStrings.getString( "controls.choose-object" ) ) );
    }

    private String getIconText( final RampObject imageElement ) {

        char muChar = '\u03BC';
        return MessageFormat.format( TheRampStrings.getString( "readout.object-mass" ), new Object[] { imageElement.getName(), new Double( imageElement.getMass() ), new Character( muChar ), new Double( imageElement.getStaticFriction() ) } );

    }
}
