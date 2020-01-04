// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.piccoloaimxcel.RichPNode;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.layout.HBox;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.mediabuttons.PiccoloClockControlPanel;

import edu.umd.cs.piccolox.pswing.PSwing;

/**
 * Clock control panel that shows "slow motion" and "normal" as radio buttons with a play/pause and step button.
 *
 * @author Sam Reid
 */
public class SlowMotionNormalTimeControlPanel extends RichPNode {

    private final Color BLANK = new Color( 0, 0, 0, 0 );
    private final AimxcelFont RADIO_BUTTON_FONT = new AimxcelFont( 16 );
    protected final PiccoloClockControlPanel piccoloClockControlPanel;

    //Fully expanded constructor that allows sim-specific IUserComponents and Strings.  Defaults for these should be added in piccolo-phet and sims that were using sim-specific strings can cut over to the common versions
    //as they become available.
    public SlowMotionNormalTimeControlPanel( IUserComponent slowMotionRadioButton, String slowMotionText, String normalSpeedText, IUserComponent normalSpeedRadioButton, SettableProperty<Boolean> normalSpeed, IClock clock ) {
        piccoloClockControlPanel = new PiccoloClockControlPanel( clock ) {{
            setBackground( BLANK );
            getButtonCanvas().setBackground( BLANK );
            getBackgroundNode().setVisible( false );
        }};

        addChild( new HBox( new PSwing( new PropertyRadioButton<Boolean>( slowMotionRadioButton, slowMotionText, normalSpeed, false ) {{
            setBackground( BLANK );
            setFont( RADIO_BUTTON_FONT );
        }} ), new PSwing( new PropertyRadioButton<Boolean>( normalSpeedRadioButton, normalSpeedText, normalSpeed, true ) {{
            setBackground( BLANK );
            setFont( RADIO_BUTTON_FONT );
        }} ), new PSwing( piccoloClockControlPanel ) ) );
    }
}