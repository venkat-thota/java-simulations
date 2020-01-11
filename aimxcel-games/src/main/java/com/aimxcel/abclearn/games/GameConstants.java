package com.aimxcel.abclearn.games;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;

public class GameConstants {
    /*---------------------------------------------------------------------------*
    * images
    *----------------------------------------------------------------------------*/
    public static final BufferedImage SOUND_ICON = GamesResources.getImage( "sound-icon.png" );
    public static final BufferedImage SOUND_OFF_ICON = GamesResources.getImage( "sound-off-icon.png" );
    public static final BufferedImage STOPWATCH_ICON = GamesResources.getImage( "blue-stopwatch.png" );

    /*---------------------------------------------------------------------------*
    * strings
    *----------------------------------------------------------------------------*/
    public static final String TITLE_GAME_SETTINGS = AimxcelCommonResources.getString( "Games.title.gameSettings" );
    public static final String LABEL_LEVEL_CONTROL = AimxcelCommonResources.getString( "Games.label.levelControl" );
    public static final String RADIO_BUTTON_ON = AimxcelCommonResources.getString( "Games.radioButton.on" );
    public static final String RADIO_BUTTON_OFF = AimxcelCommonResources.getString( "Games.radioButton.off" );
    public static final String BUTTON_START = AimxcelCommonResources.getString( "Games.button.start" );
}
