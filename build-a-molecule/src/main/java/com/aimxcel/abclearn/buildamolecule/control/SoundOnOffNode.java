//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.control;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeApplication;
import com.aimxcel.abclearn.games.GameConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

/**
 * Allows the user to turn the global sound for the sim on or off
 * <p/>
 * NOTE: consider a "muted" icon where there are no "sound" waves coming from the speaker icon
 */
public class SoundOnOffNode extends PNode {
    public SoundOnOffNode() {
        // speaker image
        final JLabel soundLabel = new JLabel( new ImageIcon( GameConstants.SOUND_ICON ) );

        // ON radio button
        final PropertyRadioButton<Boolean> soundOnRadioButton = new PropertyRadioButton<Boolean>( UserComponent.soundOn, GameConstants.RADIO_BUTTON_ON, BuildAMoleculeApplication.soundEnabled, true ) {{
            setOpaque( false );
        }};

        // OFF radio button
        final PropertyRadioButton<Boolean> soundOffRadioButton = new PropertyRadioButton<Boolean>( UserComponent.soundOff, GameConstants.RADIO_BUTTON_OFF, BuildAMoleculeApplication.soundEnabled, false ) {{
            setOpaque( false );
        }};

        new ButtonGroup() {{
            add( soundOnRadioButton );
            add( soundOffRadioButton );
        }};

        addChild( new PSwing( new JPanel( new FlowLayout( FlowLayout.LEFT ) ) {{
            setOpaque( false );
            add( soundLabel );
            add( soundOnRadioButton );
            add( soundOffRadioButton );
        }} ) );
    }
}
