package com.aimxcel.abclearn.games;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IParameterKey;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;

public class GameSimSharing {

    public static enum UserComponents implements IUserComponent {
        startGameButton, newGameButton,
        newGameYesButton, newGameNoButton,
        levelRadioButton, timerOnRadioButton, timerOffRadioButton, soundOnRadioButton, soundOffRadioButton
    }

    public static enum ModelComponents implements IModelComponent {
        game
    }

    public static enum ModelActions implements IModelAction {
        completed
    }

    public static enum ParameterKeys implements IParameterKey {
        score, perfectScore, time, bestTime, isNewBestTime, timerVisible, level, correct, attempts
    }
}
