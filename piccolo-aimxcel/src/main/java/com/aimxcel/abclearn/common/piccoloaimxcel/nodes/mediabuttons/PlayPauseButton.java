

package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.mediabuttons;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.piccoloaimxcel.event.ButtonEventHandler;
import com.aimxcel.abclearn.common.piccoloaimxcel.event.ButtonEventHandler.ButtonEventAdapter;
import com.aimxcel.abclearn.common.piccoloaimxcel.test.PiccoloTestFrame;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.isPlaying;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.playPauseButton;

public class PlayPauseButton extends IconButton {

    private boolean playing;
    private ButtonIconSet buttonIconSet;
    private ArrayList listeners = new ArrayList();

    public PlayPauseButton( int buttonHeight ) {
        super( buttonHeight );
        buttonIconSet = new ButtonIconSet( buttonHeight, buttonHeight );

        // this handler ensures that the button won't fire unless the mouse is released while inside the button
        ButtonEventHandler handler = new ButtonEventHandler();
        addInputEventListener( handler );
        handler.addButtonEventListener( new ButtonEventAdapter() {
            public void fire() {
                if ( isEnabled() ) {

                    SimSharingManager.sendUserMessage( playPauseButton, UserComponentTypes.button, pressed, ParameterSet.parameterSet( isPlaying, !isPlaying() ) );

                    setPlaying( !isPlaying() );
                    update();
                    notifyListeners();
                }
            }
        } );

        setPlaying( true );
    }

    public void setPlaying( boolean b ) {
        this.playing = b;
        update();
        updateImage();
    }

    public boolean isPlaying() {
        return playing;
    }

    private void update() {
        setIconPath( isPlaying() ? buttonIconSet.createPauseIconShape() : buttonIconSet.createPlayIconShape() );
    }

    public static interface Listener {
        void playbackStateChanged();
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void removeListener( Listener listener ) {
        listeners.remove( listener );
    }

    private void notifyListeners() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).playbackStateChanged();
        }
    }

    public static void main( String[] args ) {
        PiccoloTestFrame testFrame = new PiccoloTestFrame( "Button Test" );
        testFrame.addNode( new PlayPauseButton( 75 ) );
        testFrame.setVisible( true );
    }
}
