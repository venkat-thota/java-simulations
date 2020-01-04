

package com.aimxcel.abclearn.common.aimxcelcommon.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.LocaleUtils;

/**
 * AimxcelCommonResources is a singleton that provides access to aimxcelcommon's JAR resources.
 */
public class AimxcelCommonResources {

    // Symbolic names for localized string keys
    public static final String STRING_CLOCK_PLAY = "Common.ClockControlPanel.Play";
    public static final String STRING_CLOCK_PAUSE = "Common.ClockControlPanel.Pause";
    public static final String STRING_CLOCK_STEP = "Common.ClockControlPanel.Step";
    public static final String STRING_CLOCK_STEP_BACK = "Common.ClockControlPanel.StepBack";
    public static final String STRING_CLOCK_RESTART = "Common.ClockControlPanel.Restart";
    public static final String STRING_CLOCK_REWIND = "Common.ClockControlPanel.Rewind";
    public static final String STRING_RESET_ALL = "ControlPanel.button.resetAll";
    //The RESET string may cause problems since it is loaded from the StopwatchPanel, but it is already used like this in many places, so it shouldn't be changed lightly
    public static final String STRING_RESET = "Common.StopwatchPanel.reset";
    public static final String STRING_YES = "Common.choice.yes";
    public static final String STRING_NO = "Common.choice.no";
    public static final String STRING_HELP_MENU_HELP = "Common.HelpMenu.Help";

    // Symbolic names for image resources
    public static final String IMAGE_CLOSE_BUTTON = "buttons/closeButton.png";
    public static final String IMAGE_MINIMIZE_BUTTON = "buttons/minimizeButton.png";
    public static final String IMAGE_MAXIMIZE_BUTTON = "buttons/maximizeButton.png";
    public static final String IMAGE_FAST_FORWARD = "clock/FastForward24.gif";
    public static final String IMAGE_PAUSE = "clock/Pause24.gif";
    public static final String IMAGE_PLAY = "clock/Play24.gif";
    public static final String IMAGE_REWIND = "clock/Rewind24.gif";
    public static final String IMAGE_RESTART = IMAGE_REWIND;
    public static final String IMAGE_STEP_FORWARD = "clock/StepForward24.gif";
    public static final String IMAGE_STOP = "clock/Stop24.gif";
    public static final String IMAGE_ABC_LEARN_LOGO = "logos/aimxcel-logo-120x50.jpg";

    // preferred physical font names for various ISO language codes
    private static final String PREFERRED_FONTS_RESOURCE = "localization/aimxcelcommon-fonts.properties";

    private static AimxcelResources INSTANCE = new AimxcelResources( "aimxcelcommon" );

    //Values for translated strings
    public static final String PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_SPEED = getString( "PiccoloAimxcel.VelocitySensorNode.speed" );
    public static final String PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_UNKNOWN = getString( "PiccoloAimxcel.VelocitySensorNode.unknown" );

    /* not intended for instantiation */
    private AimxcelCommonResources() {
    }

    public static AimxcelResources getInstance() {
        return INSTANCE;
    }

    /**
     * Reads a list of preferred physical font names from the aimxcelcommon-fonts.properties resource.
     * Returns the names as an array.
     * If no preferred fonts are specified, null is returned.
     */
    public static String[] getPreferredFontNames( Locale locale ) {
        String[] names = null;
        Properties fontProperties = new Properties();
        try {
            fontProperties.load( AimxcelCommonResources.getInstance().getResourceAsStream( PREFERRED_FONTS_RESOURCE ) );
            String localeString = LocaleUtils.localeToString( locale );
            String key = "preferredFonts." + localeString; // eg, preferredFonts.ja
            String allNames = fontProperties.getProperty( key );
            if ( allNames != null ) {
                names = allNames.split( "," ); // comma separated, no whitespace
            }
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * Convenience method for accessing a localized String from aimxcelcommon.
     *
     * @param key the key for which to look up the String value
     * @return the localized String
     */
    public static String getString( String key ) {
        return INSTANCE.getLocalizedString( key );
    }

    public static final char getChar( String name, char defaultValue ) {
        return INSTANCE.getLocalizedChar( name, defaultValue );
    }

    /**
     * Convenience method for accessing an image file from aimxcelcommon.
     *
     * @param name the name of the image
     * @return BufferedImage
     */
    public static BufferedImage getImage( String name ) {
        return INSTANCE.getImage( name );
    }

    /**
     * Formats a string containing a value and units using the pattern specified in the translation file by Common.value.units.  In English
     * this has the form "{0} {1}", for example "3 meters"
     *
     * @param value the value to display
     * @param units the units for the value
     * @return the formatted string
     */
    public static String formatValueUnits( String value, String units ) {
        return MessageFormat.format( getString( "Common.value_units" ), value, units );
    }

    public static BufferedImage getMaximizeButtonImage() {
        return getImage( IMAGE_MAXIMIZE_BUTTON );
    }

    public static BufferedImage getMinimizeButtonImage() {
        return getImage( IMAGE_MINIMIZE_BUTTON );
    }
}
