
package com.aimxcel.abclearn.common.aimxcelcommon.sponsorship;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.GridPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils.InteractiveHTMLPane;

public class SponsorPanel extends GridPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SponsorPanel( AimxcelApplicationConfig config ) {

        SponsorProperties properties = new SponsorProperties( config );
        assert ( properties.isWellFormed() );

        // property values, optional ones are null
        String imageResourceName = properties.getImageResourceName();
        final String actualURL = properties.getActualURL();
        String visibleURL = properties.getVisibleURL();
        String sinceYear = properties.getSinceYear();

        // layout components, some of which are optional
        int xMargin = 40;
        int yMargin = 20;
        setBorder( new CompoundBorder( new LineBorder( Color.BLACK, 1 ), new EmptyBorder( yMargin, xMargin, yMargin, xMargin ) ) );
        setGridX( 0 ); // vertical
        setAnchor( Anchor.CENTER );
        add( new JLabel( AimxcelCommonResources.getString( "Sponsor.sponsoredBy" ) ) {{
            setFont( new AimxcelFont( 18 ) );
        }} );
        add( Box.createVerticalStrut( 15 ) );
        // image
        if ( imageResourceName != null ) {
            add( new JLabel( new ImageIcon( config.getResourceLoader().getImage( imageResourceName ) ) ) {{
                if ( actualURL != null ) {
                    // #3364, clicking on the image opens a web browser to the URL
                    addMouseListener( new MouseAdapter() {
                        @Override public void mousePressed( MouseEvent e ) {
                            AimxcelServiceManager.showWebPage( actualURL );
                        }
                    } );
                }
            }} );
        }
        // url (both actual and visible required if you want any URL displayed)
        if ( actualURL != null && visibleURL != null ) {
            add( Box.createVerticalStrut( 15 ) );
            add( createInteractiveHTMLPane( actualURL, visibleURL, new AimxcelFont( 14 ) ) );
        }
        add( Box.createVerticalStrut( 15 ) );
        // since year
        if ( sinceYear != null ) {
            add( new JLabel( MessageFormat.format( AimxcelCommonResources.getString( "Sponsor.sinceDate" ), sinceYear ) ) {{
                setFont( new AimxcelFont( 10 ) );
            }} );
        }
    }

    private static InteractiveHTMLPane createInteractiveHTMLPane( String actualURL, String visibleURL, Font font ) {
        return new InteractiveHTMLPane( HTMLUtils.createStyledHTMLFromFragment( "<a href=\"" + actualURL + "\" target=\"_blank\">" + visibleURL, font ) ) {{
            setOpaque( false );
        }};
    }
}
