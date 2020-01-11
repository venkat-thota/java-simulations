
package com.aimxcel.abclearn.theramp;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.theramp.model.RampObject;



public class ObjectComboBox extends JComboBox {
    private AdvancedRampControlPanel controlPanel;


    public ObjectComboBox( final RampObject[] rampObjects, final AdvancedRampControlPanel controlPanel ) {
        super( toLabelArray( rampObjects, controlPanel ) );
        setRenderer( new ComboBoxRenderer() );
        this.controlPanel = controlPanel;
        
        addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent e ) {
                int index = getSelectedIndex();
                controlPanel.setup( rampObjects[index] );
            }
        } );

    }

    private static ImageIcon[] toLabelArray( RampObject[] imageElements, Component component ) {
        ImageIcon[] lab = new ImageIcon[imageElements.length];
        for ( int i = 0; i < lab.length; i++ ) {
            try {
                BufferedImage image = ImageLoader.loadBufferedImage( imageElements[i].getLocation() );
                image = BufferedImageUtils.rescaleYMaintainAspectRatio( image, 35 );
                lab[i] = new ImageIcon( image );
                lab[i].setDescription( MessageFormat.format( TheRampStrings.getString( "readout.mass" ), new Object[] { imageElements[i].getName(), new Double( imageElements[i].getMass() ) } ) );
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        return lab;
    }

    public static class ComboBoxRenderer extends DefaultListCellRenderer {
        public ComboBoxRenderer() {
            setOpaque( true );
            setHorizontalAlignment( CENTER );
            setVerticalAlignment( CENTER );
        }

        public Component getListCellRendererComponent( JList list,
                                                       Object value,
                                                       int index,
                                                       boolean isSelected,
                                                       boolean cellHasFocus ) {
            DefaultListCellRenderer component = (DefaultListCellRenderer) super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );

            ImageIcon icon = (ImageIcon) value;
            setText( icon.getDescription() );
            setIcon( icon );

            return component;
        }
    }
}
