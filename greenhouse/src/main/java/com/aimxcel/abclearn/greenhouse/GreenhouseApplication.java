// Copyright 2002-2011, University of Colorado

/**
 * Class: GreenhouseApplication
 * Package: edu.colorado.phet.greenhouse
 * Author: Another Guy
 * Date: Oct 9, 2003
 */
package com.aimxcel.abclearn.greenhouse;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;

/**
 * General comments, issues:
 * I wrote this using real model coordinates and units. The origin is at the center of the earth, and the positive
 * y direction is up. Unfortunately, this has turned out to cause a host of issues.
 * <p/>
 * The snow in the ice age reflects photons, but is not really in the model. Instead I do a rough estimate of where
 * it is in the background image (in the view) and use that.
 */
public class GreenhouseApplication extends CoreAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PhotonAbsorptionModule photonAbsorptionModule;

    public GreenhouseApplication( AimxcelApplicationConfig config ) {
        super( config );

        // modules
        AimxcelFrame parentFrame = getAimxcelFrame();
        addModule( new GreenhouseModule() );
        addModule( new GlassPaneModule() );
        photonAbsorptionModule = new PhotonAbsorptionModule(parentFrame);
        addModule( photonAbsorptionModule );

        // Developer controls.
        JMenu developerMenu = parentFrame.getDeveloperMenu();
        final JCheckBoxMenuItem photonAbsorptionParamCheckBox = new JCheckBoxMenuItem( "Photon Absorption Controls" );
        developerMenu.add( photonAbsorptionParamCheckBox );
        photonAbsorptionParamCheckBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                photonAbsorptionModule.setPhotonAbsorptionParamsDlgVisible( photonAbsorptionParamCheckBox.isSelected() );
            }
        } );

        paintContentImmediately();
        getAimxcelFrame().addWindowFocusListener( new WindowFocusListener() {
            public void windowGainedFocus( WindowEvent e ) {
                paintContentImmediately();
            }

            public void windowLostFocus( WindowEvent e ) {
            }
        } );
    }

    public static void paintContentImmediately() {
        Container contentPane = AimxcelApplication.getInstance().getAimxcelFrame().getContentPane();
        if ( contentPane instanceof JComponent ) {
            JComponent jComponent = (JComponent) contentPane;
            jComponent.paintImmediately( 0, 0, jComponent.getWidth(), jComponent.getHeight() );
        }
    }

    private static class GreenhouseLookAndFeel extends AimxcelLookAndFeel {
        public GreenhouseLookAndFeel() {
            setBackgroundColor( GreenhouseConfig.PANEL_BACKGROUND_COLOR );
            setTitledBorderFont( new AimxcelFont( Font.PLAIN, 12 ) );
        }
    }

    public static void main( String[] args ) {
        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new GreenhouseApplication( config );
            }
        };
        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, GreenhouseConfig.PROJECT_NAME, GreenhouseConfig.FLAVOR_NAME_GREENHOUSE );
        appConfig.setLookAndFeel( new GreenhouseLookAndFeel() );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}
