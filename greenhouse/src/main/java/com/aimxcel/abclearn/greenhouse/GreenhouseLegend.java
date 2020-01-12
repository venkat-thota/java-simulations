package com.aimxcel.abclearn.greenhouse;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.aimxcel.abclearn.greenhouse.model.Photon;
import com.aimxcel.abclearn.greenhouse.view.PhotonGraphic;
import com.aimxcel.abclearn.photonabsorption.model.WavelengthConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelTitledBorder;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelTitledPanel;

public class GreenhouseLegend extends AimxcelTitledPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GreenhouseLegend() {
        this( AimxcelTitledBorder.DEFAULT_FONT );
    }
    
    public GreenhouseLegend( Font titleFont ){
        super( GreenhouseResources.getString( "GreenhouseLegend.LegendTitle" ), titleFont );

        // Draw an IR photon and a sunlight photon
        BufferedImage irPhotonBI = new BufferedImage( 15, 15, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2 = (Graphics2D) irPhotonBI.getGraphics();
        Photon irPhoton = new Photon( WavelengthConstants.IR_WAVELENGTH, null );
        PhotonGraphic irPhotonGraphic = new PhotonGraphic( irPhoton );
        irPhotonGraphic.paint( g2 );
        ImageIcon irPhotonIcon = new ImageIcon( irPhotonGraphic.getImage() );

        BufferedImage sunlightPhotonBI = new BufferedImage( 15, 15, BufferedImage.TYPE_INT_ARGB );
        g2 = (Graphics2D) sunlightPhotonBI.getGraphics();
        Photon sunlightPhoton = new Photon( WavelengthConstants.SUNLIGHT_WAVELENGTH, null );
        PhotonGraphic sunlightPhotonGraphic = new PhotonGraphic( sunlightPhoton );
        sunlightPhotonGraphic.paint( g2 );
        ImageIcon sunlightPhotonIcon = new ImageIcon( sunlightPhotonGraphic.getImage() );

        setLayout( new GridBagLayout() );
        try {
            GridBagConstraints gbc = new GridBagConstraints( 0, GridBagConstraints.RELATIVE, 1, 1, 0, 0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL,
                    new Insets( 0, 0, 0, 0 ), 0, 20 );

            JLabel sunlightLegend = new JLabel( GreenhouseResources.getString( "GreenhouseLegend.SunlightPhotonLabel" ),
                                                sunlightPhotonIcon, SwingConstants.LEFT );
            add(sunlightLegend, gbc);

            JLabel irLegend = new JLabel( GreenhouseResources.getString( "GreenhouseLegend.InfraredPhotonLabel" ),
                                          irPhotonIcon, SwingConstants.LEFT );

            add(irLegend, gbc);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
