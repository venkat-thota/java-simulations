
package com.aimxcel.abclearn.common.aimxcelcommon.view.util;

import java.awt.Color;
import java.awt.Font;
import java.text.MessageFormat;
import java.util.Locale;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;



public class HTMLUtils {

    private static final Font DEFAULT_FONT = new AimxcelFont();
    private static final String DEFAULT_CSS = "<head><style type=\"text/css\">body { font-size: @FONT_SIZE@; font-family: @FONT_FAMILY@ }</style></head>";
    private static final String PHP_ARG_DELIMITER = "&";

    /* not intended for instantiation */
    private HTMLUtils() {
    }

    /**
     * Creates an \<a\> tag with an href attribute.
     * The link is displayed.
     *
     * @param url the URL to be loaded when the hyperlinked object is activated.
     * @return
     */
    public static String getHref( String url ) {
        return getHref( url, url );
    }

    /**
     * Creates an \<a\> tag with an href attribute.
     *
     * @param url  the URL to be loaded when the hyperlinked object is activated.
     * @param text the text to be displayed
     * @return
     */
    public static String getHref( String url, String text ) {
        return "<a href=" + url + ">" + text + "</a>";
    }

    /**
     * Creates an \<a\> tag that contains an href link to the Aimxcel homepage.
     * The link is displayed.
     *
     * @return
     */
    public static String getAimxcelHomeHref() {
        return getAimxcelHomeHref( AimxcelCommonConstants.AIMXCEL_HOME_URL );
    }

    /**
     * Creates an \<a\> tag that contains an href link to the Aimxcel homepage.
     *
     * @param text the text to be displayed
     * @return
     */
    public static String getAimxcelHomeHref( String text ) {
        return getHref( AimxcelCommonConstants.AIMXCEL_HOME_URL, text );
    }

    /**
     * Creates an \<a\> tag that contains a mailto link to the Aimxcel help email address.
     * The email address is displayed.
     *
     * @return
     */
    public static String getAimxcelMailtoHref() {
        return getAimxcelMailtoHref( AimxcelCommonConstants.AIMXCEL_EMAIL );
    }

    /**
     * Creates an \<a\> tag that contains a mailto link to the Aimxcel help email address.
     *
     * @param text the text to be displayed
     * @return
     */
    public static String getAimxcelMailtoHref( String text ) {
        return getHref( "mailto:" + AimxcelCommonConstants.AIMXCEL_EMAIL, text );
    }

    /**
     * Gets the URL for a simulation's web page on the Aimxcel site.
     *
     * @param project
     * @param sim
     * @return
     */
    public static String getSimWebsiteURL( String project, String sim ) {
        return AimxcelCommonConstants.SIM_WEBSITE_REDIRECT_URL + "?" +
               "request_version=" + AimxcelCommonConstants.SIM_WEBSITE_REDIRECT_VERSION + PHP_ARG_DELIMITER +
               "project=" + project + PHP_ARG_DELIMITER +
               "sim=" + sim;
    }


    /**
     * Gets the URL for a simulation's JAR file on the Aimxcel site.
     *
     * @param project
     * @param sim
     * @param locale
     * @return
     */
    public static String getSimJarURL( String project, String sim, Locale locale ) {
        String url = AimxcelCommonConstants.SIM_JAR_REDIRECT_URL + "?" +
                     "request_version=" + AimxcelCommonConstants.SIM_JAR_REDIRECT_VERSION + PHP_ARG_DELIMITER +
                     "project=" + project + PHP_ARG_DELIMITER +
                     "sim=" + sim + PHP_ARG_DELIMITER +
                     "language=" + locale.getLanguage();
        if ( !locale.getCountry().equals( "" ) ) {
            // add optional country code
            url += PHP_ARG_DELIMITER + "country=" + locale.getCountry();
        }
        return url;
    }

    /**
     * Returns the URL for the <project>_all.jar from the Aimxcel site.
     *
     * @param project
     * @param ampersand
     * @return
     */
    public static String getProjectJarURL( String project, String ampersand ) {
        return AimxcelCommonConstants.SIM_JAR_REDIRECT_URL + "?" +
               "request_version=" + AimxcelCommonConstants.SIM_JAR_REDIRECT_VERSION + ampersand +
               "project=" + project;
    }

    public static String createStyledHTMLFromFragment( String htmlFragment ) {
        return createStyledHTMLFromFragment( htmlFragment, DEFAULT_FONT, DEFAULT_CSS );
    }

    public static String createStyledHTMLFromFragment( String htmlFragment, Font font ) {
        return createStyledHTMLFromFragment( htmlFragment, font, DEFAULT_CSS );
    }

    /**
     * Creates an HTML fragment that contains a CSS that sets font properties.
     *
     * @param htmlFragment
     * @param font
     * @param css
     * @return
     */
    public static String createStyledHTMLFromFragment( String htmlFragment, Font font, String css ) {
        String html = HTMLUtils.toHTMLString( css + htmlFragment );
        return setFontInStyledHTML( html, font );
    }

    /**
     * Fills in font information in any HTML that contains CSS placeholders ala DEFAULT_CSS.
     *
     * @param html
     * @param font
     * @return
     */
    public static String setFontInStyledHTML( String html, Font font ) {
        html = html.replaceAll( "@FONT_SIZE@", font.getSize() + "pt" );
        html = html.replaceAll( "@FONT_FAMILY@", font.getFamily() );
        return html;
    }

    /**
     * An HTML editor pane.
     * This is the base class for other types of HTML editor panes.
     * You need to add your own hyperlink behavior via addHyperlinkListener.
     */
    public static class HTMLEditorPane extends JEditorPane {
        public HTMLEditorPane( String html ) {
            setEditorKit( new HTMLEditorKit() );
            setText( html );
            setEditable( false );
        }
    }

    /**
     * An HTML editor pane that opens a web browser for hyperlinks.
     */
    public static class InteractiveHTMLPane extends HTMLEditorPane {
        public InteractiveHTMLPane( String html ) {
            super( html );
            addHyperlinkListener( new HyperlinkListener() {
                public void hyperlinkUpdate( HyperlinkEvent e ) {
                    if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {
                        AimxcelServiceManager.showWebPage( e.getURL() );
                    }
                }
            } );
        }
    }

    /**
     * Combines an array of HTML strings, HTML fragments
     * and plain text strings into a single HTML string.
     *
     * @param strings
     * @return
     */
    public static String toHTMLString( String[] strings ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append( "<html>" );
        for ( int i = 0; i < strings.length; i++ ) {
            String string = strings[i];
            if ( string != null ) {
                string = string.replaceAll( "<html>", "" );
                string = string.replaceAll( "</html>", "" );
                buffer.append( string );
            }
        }
        buffer.append( "</html>" );
        return buffer.toString();
    }

    /**
     * Creates an HMTL fragment is a specified color.
     *
     * @param fragment
     * @param color
     * @return
     */
    public static String createColoredFragment( String fragment, Color color ) {
        String pattern = "<font color=\"#{0}\">{1}</font>";
        String rgb = Integer.toHexString( color.getRGB() );
        rgb = rgb.substring( 2, rgb.length() );
        Object[] args = { rgb, fragment };
        return MessageFormat.format( pattern, args );
    }

    /**
     * Converts a string to an HTML string.
     * If you have a plain-text string, this is a convenience method to add the \<html\> tag.
     * Sometimes we want to combine HTML strings, and we end up with too many \<html\> tags.
     * This method ensures that we only have \<html\> and \</html\> at the beginning and
     * end of the string.
     * <p/>
     * Example: "hello world" becomes "\<html\>hello world\</html\>".
     * <p/>
     * Example "\<html\>foo\</html\> \<html\>bar\</html\>" becomes "\<html\>foo bar\</html\>".
     *
     * @param string
     * @return
     */
    public static String toHTMLString( String string ) {
        String[] s = { string };
        return toHTMLString( s );
    }

    /**
     * Replaces '<' and '>' with their escape entities.
     * Useful when HTML tags need to appear in a user-visible message
     * that is implemented using HTML.
     *
     * @param html
     * @return
     */
    public static String escape( String html ) {
        String s = new String( html );
        s = s.replace( "<", "&lt;" );
        s = s.replace( ">", "&gt;" );
        return s;
    }
}
