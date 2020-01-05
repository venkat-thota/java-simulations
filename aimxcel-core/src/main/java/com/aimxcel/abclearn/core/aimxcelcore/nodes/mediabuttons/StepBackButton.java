
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.stepBackButton;

import com.aimxcel.abclearn.core.aimxcelcore.test.CoreTestFrame;


public class StepBackButton extends DefaultIconButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StepBackButton( int buttonHeight ) {
        super( stepBackButton, buttonHeight, new ButtonIconSet( buttonHeight, buttonHeight ).createStepBackIconShape() );
    }

    public static void main( String[] args ) {
        CoreTestFrame testFrame = new CoreTestFrame( "Button Test" );
        StepBackButton button = new StepBackButton( 50 );
        button.setOffset( 50, 50 );
        testFrame.addNode( button );

        PlayPauseButton playPauseButton = new PlayPauseButton( 75 );
        playPauseButton.setOffset( button.getFullBounds().getMaxX(), button.getFullBounds().getCenterY() - playPauseButton.getFullBounds().getHeight() / 2 );
        testFrame.addNode( playPauseButton );

        testFrame.setVisible( true );
    }

}
