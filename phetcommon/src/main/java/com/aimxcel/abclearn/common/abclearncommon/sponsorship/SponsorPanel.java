
package com.aimxcel.abclearn.common.abclearncommon.sponsorship;

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

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.servicemanager.AbcLearnServiceManager;
import com.aimxcel.abclearn.common.abclearncommon.view.util.GridPanel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.HTMLUtils.InteractiveHTMLPane;

//import com.aimxcel.abclearn.common.abclearncommon.view.util.Anchor;

/**
 * Panel that displays sponsor information.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SponsorPanel extends GridPanel {

    public SponsorPanel( AbcLearnApplicationConfig config ) {

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
        add( new JLabel( AbcLearnCommonResources.getString( "Sponsor.sponsoredBy" ) ) {{
            setFont( new AbcLearnFont( 18 ) );
        }} );
        add( Box.createVerticalStrut( 15 ) );
        // image
        if ( imageResourceName != null ) {
            add( new JLabel( new ImageIcon( config.getResourceLoader().getImage( imageResourceName ) ) ) {{
                if ( actualURL != null ) {
                    // #3364, clicking on the image opens a web browser to the URL
                    addMouseListener( new MouseAdapter() {
                        @Override public void mousePressed( MouseEvent e ) {
                            AbcLearnServiceManager.showWebPage( actualURL );
                        }
                    } );
                }
            }} );
        }
        // url (both actual and visible required if you want any URL displayed)
        if ( actualURL != null && visibleURL != null ) {
            add( Box.createVerticalStrut( 15 ) );
            add( createInteractiveHTMLPane( actualURL, visibleURL, new AbcLearnFont( 14 ) ) );
        }
        add( Box.createVerticalStrut( 15 ) );
        // since year
        if ( sinceYear != null ) {
            add( new JLabel( MessageFormat.format( AbcLearnCommonResources.getString( "Sponsor.sinceDate" ), sinceYear ) ) {{
                setFont( new AbcLearnFont( 10 ) );
            }} );
        }
    }

    private static InteractiveHTMLPane createInteractiveHTMLPane( String actualURL, String visibleURL, Font font ) {
        return new InteractiveHTMLPane( HTMLUtils.createStyledHTMLFromFragment( "<a href=\"" + actualURL + "\" target=\"_blank\">" + visibleURL, font ) ) {{
            setOpaque( false );
        }};
    }
}
