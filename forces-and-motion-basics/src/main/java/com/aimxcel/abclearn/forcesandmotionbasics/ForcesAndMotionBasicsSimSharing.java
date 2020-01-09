package com.aimxcel.abclearn.forcesandmotionbasics;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IParameterKey;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;


public class ForcesAndMotionBasicsSimSharing {
    public static enum UserComponents implements IUserComponent {
        tugOfWarTab, motionTab, frictionTab, accelerationLabTab,
        massCheckBox, valuesCheckBox, sumOfForcesCheckBox, speedCheckBox, accelerometerCheckBox, forcesCheckBox,
        soundCheckBox, goButton, stopButton, returnButton, largeBluePuller, mediumBluePuller, smallBluePuller1, smallBluePuller2,
        largeRedPuller, mediumRedPuller, smallRedPuller1, smallRedPuller2, appliedForceSliderKnob, frictionSliderKnob, fridge, crate1, crate2, girl,
        man, trash, gift, appliedForceTextField, speedCheckBoxIcon, accelerometerCheckBoxIcon, bucket,
        showForcesCheckBoxIcon, showSumOfForcesCheckBoxIcon
    }

    public static enum ModelComponents implements IModelComponent {
        forceModel, tugOfWarGame, stack
    }

    public static enum ParameterKeys implements IParameterKey {
        winningTeam, mass, sumOfForces, items, appliedForce
    }
}