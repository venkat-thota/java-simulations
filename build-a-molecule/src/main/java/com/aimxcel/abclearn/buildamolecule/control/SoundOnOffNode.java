package com.aimxcel.abclearn.buildamolecule.control;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeApplication;
import com.aimxcel.abclearn.games.GameConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;
public class SoundOnOffNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SoundOnOffNode() {
        // speaker image
        final JLabel soundLabel = new JLabel( new ImageIcon( GameConstants.SOUND_ICON ) );

        // ON radio button
        final PropertyRadioButton<Boolean> soundOnRadioButton = new PropertyRadioButton<Boolean>( UserComponent.soundOn, GameConstants.RADIO_BUTTON_ON, BuildAMoleculeApplication.soundEnabled, true ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOpaque( false );
        }};

        // OFF radio button
        final PropertyRadioButton<Boolean> soundOffRadioButton = new PropertyRadioButton<Boolean>( UserComponent.soundOff, GameConstants.RADIO_BUTTON_OFF, BuildAMoleculeApplication.soundEnabled, false ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOpaque( false );
        }};

        new ButtonGroup() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            add( soundOnRadioButton );
            add( soundOffRadioButton );
        }};

        addChild( new PSwing( new JPanel( new FlowLayout( FlowLayout.LEFT ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOpaque( false );
            add( soundLabel );
            add( soundOnRadioButton );
            add( soundOffRadioButton );
        }} ) );
    }
}
