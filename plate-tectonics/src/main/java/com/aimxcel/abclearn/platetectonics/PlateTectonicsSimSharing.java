// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IParameterKey;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;

/**
 * Sim-sharing enums that are specific to this sim.
 *
 * @author Sam Reid
 */
public class PlateTectonicsSimSharing {
    public static enum UserComponents implements IUserComponent {
        plateMotionTab, crustTab, zoomSlider, timeSpeedSlider, newCrustButton,

        densityView, temperatureView, bothView,
        compositionSlider, temperatureSlider, thicknessSlider,

        showLabels, showWater,

        ruler, thermometer, densityMeter, crustPiece,

        manualMode, automaticMode,

        handle,

        convergentMotion, divergentMotion, transformMotion
    }

    public static enum UserActions implements IUserAction {
        removedFromToolbox,
        putBackInToolbox,

        droppedCrustPiece,
        putBackInCrustPicker,
        attemptedToDropOnExistingCrust
    }

    public static enum ParameterKeys implements IParameterKey {
        plateType,
        motionType,
        side,
        leftPlateType,
        rightPlateType,
        timeChangeMillionsOfYears
    }

    public static enum ModelComponents implements IModelComponent {
        motion,
        time
    }

    public static enum ModelActions implements IModelAction {
        transformMotion,
        continentalCollisionMotion,
        rightPlateSubductingMotion,
        leftPlateSubductingMotion,
        divergentMotion,

        maximumTimeReached
    }
}
