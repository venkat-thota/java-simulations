
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.stepButton;

import com.aimxcel.abclearn.core.aimxcelcore.test.CoreTestFrame;


public class StepButton extends DefaultIconButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StepButton( int buttonHeight ) {
        super( stepButton, buttonHeight, new ButtonIconSet( buttonHeight, buttonHeight ).createStepIconShape() );
    }

    public static void main( String[] args ) {
        CoreTestFrame testFrame = new CoreTestFrame( "Button Test" );
        PlayPauseButton playPauseButton = new PlayPauseButton( 75 );
        testFrame.addNode( playPauseButton );

        StepButton button = new StepButton( 50 );
        button.setOffset( playPauseButton.getFullBounds().getMaxX(), playPauseButton.getFullBounds().getCenterY() - button.getFullBounds().getHeight() / 2 );
        testFrame.addNode( button );
        testFrame.setVisible( true );
    }

}
