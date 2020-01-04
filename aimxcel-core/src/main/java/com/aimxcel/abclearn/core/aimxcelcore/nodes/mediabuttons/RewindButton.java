
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.rewindButton;

import com.aimxcel.abclearn.core.aimxcelcore.test.PiccoloTestFrame;


public class RewindButton extends DefaultIconButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RewindButton( int buttonHeight ) {
        super( rewindButton, buttonHeight, new ButtonIconSet( buttonHeight, buttonHeight ).createRewindIconShape() );
    }

    public static void main( String[] args ) {
        PiccoloTestFrame testFrame = new PiccoloTestFrame( "Button Test" );
        PlayPauseButton playPauseButton = new PlayPauseButton( 75 );
        testFrame.addNode( playPauseButton );

        RewindButton button = new RewindButton( 50 );
        button.setOffset( playPauseButton.getFullBounds().getMaxX(), playPauseButton.getFullBounds().getCenterY() - button.getFullBounds().getHeight() / 2 );
        testFrame.addNode( button );
        testFrame.setVisible( true );
    }
}