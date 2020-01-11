// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.control;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.lwjgl.utils.GLSimSharingPropertyRadioButton;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

/**
 * Allows the user to select between automatic and manual mode
 */
public class PlayModePanel extends PNode {
    public PlayModePanel( final Property<Boolean> isAutoMode ) {
        PSwing autoRadioButton = new PSwing( new GLSimSharingPropertyRadioButton<Boolean>( UserComponents.automaticMode, Strings.AUTOMATIC_MODE, isAutoMode, true ) );
        PSwing manualRadioButton = new PSwing( new GLSimSharingPropertyRadioButton<Boolean>( UserComponents.manualMode, Strings.MANUAL_MODE, isAutoMode, false ) );

        addChild( autoRadioButton );
        addChild( manualRadioButton );

        manualRadioButton.setOffset( autoRadioButton.getFullBounds().getMaxX() + 10, 0 );
    }
}
