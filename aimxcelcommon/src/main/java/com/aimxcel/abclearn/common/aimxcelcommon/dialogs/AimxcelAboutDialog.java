

package com.aimxcel.abclearn.common.aimxcelcommon.dialogs;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.aboutDialogCloseButton;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.aboutDialogCreditsButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.CreditsDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.HorizontalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.SoftwareAgreementButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils.InteractiveHTMLPane;

public class AimxcelAboutDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Copyright notice, not translated so no one messes with it, and so that we can easily change the date.
    private static final String COPYRIGHT_HTML_FRAGMENT =
            "<b>abcLearn Interactive Simulations</b><br>" +
            "Copyright &copy; 2004-2015 University of Colorado.<br>" +
            "<a href=" + AimxcelCommonConstants.AIMXCEL_LICENSE_URL + ">Some rights reserved.</a><br>" +
            "Visit " + HTMLUtils.getAimxcelHomeHref();

    // translated strings
    private static final String TITLE = AimxcelCommonResources.getString( "Common.HelpMenu.AboutTitle" );
    private static final String LOGO_TOOLTIP = AimxcelCommonResources.getString( "Common.About.WebLink" );
    private static final String SIM_VERSION = AimxcelCommonResources.getString( "Common.About.Version" );
    private static final String BUILD_DATE = AimxcelCommonResources.getString( "Common.About.BuildDate" );
    private static final String DISTRIBUTION = AimxcelCommonResources.getString( "Common.About.Distribution" );
    private static final String JAVA_VERSION = AimxcelCommonResources.getString( "Common.About.JavaVersion" );
    private static final String OS_VERSION = AimxcelCommonResources.getString( "Common.About.OSVersion" );
    private static final String CREDITS_BUTTON = AimxcelCommonResources.getString( "Common.About.CreditsButton" );
    private static final String CLOSE_BUTTON = AimxcelCommonResources.getString( "Common.choice.close" );

    private final ISimInfo config;

    /**
     * Constructs the dialog.
     *
     * @param abcLearnApplication
     * @throws HeadlessException
     */
    public AimxcelAboutDialog( AimxcelApplication abcLearnApplication ) {
        this( abcLearnApplication.getAimxcelFrame(), abcLearnApplication.getSimInfo() );
    }

    /**
     * Constructs a dialog.
     *
     * @param config
     * @param owner
     */
    protected AimxcelAboutDialog( Frame owner, ISimInfo config ) {
        super( owner );
        setResizable( false );

        this.config = config;

        String titleString = config.getName();
        setTitle( TITLE + " " + titleString );

        JPanel logoPanel = createLogoPanel();
        JPanel infoPanel = createInfoPanel( config );
        JPanel buttonPanel = createButtonPanel( config.isStatisticsFeatureIncluded() );

        VerticalLayoutPanel contentPanel = new VerticalLayoutPanel();
        contentPanel.setFillHorizontal();
        contentPanel.add( logoPanel );
        contentPanel.add( new JSeparator() );
        contentPanel.add( infoPanel );
        contentPanel.add( new JSeparator() );
        contentPanel.add( buttonPanel );
        setContentPane( contentPanel );

        pack();
        SwingUtils.centerDialogInParent( this );
    }

    /*
     * Creates the panel that contains the logo and general copyright info.
     */
    private JPanel createLogoPanel() {

        BufferedImage image = AimxcelCommonResources.getInstance().getImage( AimxcelLookAndFeel.AIMXCEL_LOGO_120x50 );
        JLabel logoLabel = new JLabel( new ImageIcon( image ) );
        logoLabel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        logoLabel.setToolTipText( LOGO_TOOLTIP );
        logoLabel.addMouseListener( new MouseInputAdapter() {
            public void mouseReleased( MouseEvent e ) {
                AimxcelServiceManager.showAimxcelPage();
            }
        } );

        String html = HTMLUtils.createStyledHTMLFromFragment( COPYRIGHT_HTML_FRAGMENT );
        InteractiveHTMLPane pane = new InteractiveHTMLPane( html );
        pane.setBackground( new JPanel().getBackground() );//see #1275

        HorizontalLayoutPanel logoPanel = new HorizontalLayoutPanel();
        logoPanel.setInsets( new Insets( 10, 10, 10, 10 ) ); // top,left,bottom,right
        logoPanel.add( logoLabel );
       // logoPanel.add( pane );

        return logoPanel;
    }

    /*
     * Creates the panel that displays info specific to the simulation.
     */
    private JPanel createInfoPanel( ISimInfo config ) {

        String titleString = config.getName();
        String versionString = config.getVersion().formatForAboutDialog();
        String buildDate = config.getVersion().formatTimestamp();
        String distributionTag = config.getDistributionTag();

        VerticalLayoutPanel infoPanel = new VerticalLayoutPanel();

        // Simulation title
        JLabel titleLabel = new JLabel( titleString );
        Font f = titleLabel.getFont();
        titleLabel.setFont( new Font( f.getFontName(), Font.BOLD, f.getSize() ) );

        // Simulation version
        JLabel versionLabel = new JLabel( SIM_VERSION + " " + versionString );

        // Build date
        JLabel buildDateLabel = new JLabel( BUILD_DATE + " " + buildDate );

        // Distribution tag (optional)
        JLabel distributionTagLabel = null;
        if ( distributionTag != null && distributionTag.length() > 0 ) {
            distributionTagLabel = new JLabel( DISTRIBUTION + " " + distributionTag );
        }

        // Java runtime version
        String javaVersionString = JAVA_VERSION + " " + System.getProperty( "java.version" );
        JLabel javaVersionLabel = new JLabel( javaVersionString );

        // OS version
        String osVersion = OS_VERSION + " " + System.getProperty( "os.name" ) + " " + System.getProperty( "os.version" );
        JLabel osVersionLabel = new JLabel( osVersion );

        // layout
        int xMargin = 10;
        int ySpacing = 10;
        infoPanel.setInsets( new Insets( 0, xMargin, 0, xMargin ) ); // top,left,bottom,right
        infoPanel.add( Box.createVerticalStrut( ySpacing ) );
        infoPanel.add( titleLabel );
        infoPanel.add( Box.createVerticalStrut( ySpacing ) );
        infoPanel.add( versionLabel );
        infoPanel.add( buildDateLabel );
        if ( distributionTagLabel != null ) {
            infoPanel.add( distributionTagLabel );
        }
        infoPanel.add( Box.createVerticalStrut( ySpacing ) );
        infoPanel.add( javaVersionLabel );
        infoPanel.add( osVersionLabel );
        infoPanel.add( Box.createVerticalStrut( ySpacing ) );

        return infoPanel;
    }

    /*
    * Create the panel that contains the buttons.
    * The Credits button is added only if a credits file exists.
    */
    private JPanel createButtonPanel( boolean isStatisticsFeatureIncluded ) {

        // Software Use Agreement
        JButton agreementButton = new SoftwareAgreementButton( this );

        // Credits
        JButton creditsButton = new SimSharingJButton( aboutDialogCreditsButton, CREDITS_BUTTON );
        creditsButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                showCredits();
            }
        } );

        // Close
        JButton closeButton = new SimSharingJButton( aboutDialogCloseButton, CLOSE_BUTTON );
        getRootPane().setDefaultButton( closeButton );
        closeButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );

        // layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new FlowLayout() );
        //buttonPanel.add( agreementButton );
        //buttonPanel.add( creditsButton );
        buttonPanel.add( closeButton );

        return buttonPanel;
    }

    protected void showCredits() {
        new CreditsDialog( this, config.getProjectName() ).setVisible( true );
    }
}
