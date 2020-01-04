 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.RichPNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.PiccoloClockControlPanel;

import edu.umd.cs.piccolox.pswing.PSwing;


public class SlowMotionNormalTimeControlPanel extends RichPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color BLANK = new Color( 0, 0, 0, 0 );
    private final AimxcelFont RADIO_BUTTON_FONT = new AimxcelFont( 16 );
    protected final PiccoloClockControlPanel piccoloClockControlPanel;

      public SlowMotionNormalTimeControlPanel( IUserComponent slowMotionRadioButton, String slowMotionText, String normalSpeedText, IUserComponent normalSpeedRadioButton, SettableProperty<Boolean> normalSpeed, IClock clock ) {
        piccoloClockControlPanel = new PiccoloClockControlPanel( clock ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setBackground( BLANK );
            getButtonCanvas().setBackground( BLANK );
            getBackgroundNode().setVisible( false );
        }};

        addChild( new HBox( new PSwing( new PropertyRadioButton<Boolean>( slowMotionRadioButton, slowMotionText, normalSpeed, false ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setBackground( BLANK );
            setFont( RADIO_BUTTON_FONT );
        }} ), new PSwing( new PropertyRadioButton<Boolean>( normalSpeedRadioButton, normalSpeedText, normalSpeed, true ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setBackground( BLANK );
            setFont( RADIO_BUTTON_FONT );
        }} ), new PSwing( piccoloClockControlPanel ) ) );
    }
}