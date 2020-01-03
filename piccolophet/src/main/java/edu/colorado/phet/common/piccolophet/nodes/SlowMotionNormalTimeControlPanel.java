// Copyright 2002-2012, University of Colorado
package edu.colorado.phet.common.piccolophet.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import edu.colorado.phet.common.piccolophet.RichPNode;
import edu.colorado.phet.common.piccolophet.nodes.layout.HBox;
import edu.colorado.phet.common.piccolophet.nodes.mediabuttons.PiccoloClockControlPanel;
import edu.umd.cs.piccolox.pswing.PSwing;

/**
 * Clock control panel that shows "slow motion" and "normal" as radio buttons with a play/pause and step button.
 *
 * @author Sam Reid
 */
public class SlowMotionNormalTimeControlPanel extends RichPNode {

    private final Color BLANK = new Color( 0, 0, 0, 0 );
    private final AbcLearnFont RADIO_BUTTON_FONT = new AbcLearnFont( 16 );
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