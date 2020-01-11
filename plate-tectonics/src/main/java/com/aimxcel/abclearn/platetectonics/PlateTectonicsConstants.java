// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.platetectonics;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

/**
 * Contains global constants and some dynamic global variables (like colors)
 */
public class PlateTectonicsConstants {

    public static final Property<Integer> FRAMES_PER_SECOND_LIMIT = new Property<Integer>( 60 );

    /* Not intended for instantiation. */
    private PlateTectonicsConstants() {
    }

    /*---------------------------------------------------------------------------*
    * colors
    *----------------------------------------------------------------------------*/

    // colors for different motion directions
    public static final Color ARROW_CONVERGENT_FILL = new Color( 0, 0.8f, 0, 0.8f );
    public static final Color ARROW_DIVERGENT_FILL = new Color( 0.8f, 0, 0, 0.8f );
    public static final Color ARROW_TRANSFORM_FILL = new Color( 0, 0, 1, 0.8f );

    // the "grass" green color shown in above-water low-lying areas
    public static final Color EARTH_GREEN = new Color( 0x526F35 );

    public static final Color DARK_LABEL = Color.BLACK;
    public static final Color LIGHT_LABEL = new Color( 0.9f, 0.9f, 0.9f, 1 );

    public static final Property<Color> DIAL_HIGHLIGHT_COLOR = new Property<Color>( new Color( 255, 200, 80 ) );

    public static final Color BUTTON_COLOR = Color.ORANGE;

    /*---------------------------------------------------------------------------*
    * fonts
    *----------------------------------------------------------------------------*/

    public static final AimxcelFont PANEL_TITLE_FONT = new AimxcelFont( 13, true );

    public static final AimxcelFont LABEL_FONT = new AimxcelFont( 18 );

    public static final AimxcelFont BUTTON_FONT = new AimxcelFont( 14 );
}