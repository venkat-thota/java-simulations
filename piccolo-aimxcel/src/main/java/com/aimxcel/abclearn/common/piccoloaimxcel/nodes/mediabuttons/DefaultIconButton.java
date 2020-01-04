
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.mediabuttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.piccoloaimxcel.event.ButtonEventHandler;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.AimxcelPPath;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;

public class DefaultIconButton extends IconButton {
    protected AimxcelPPath iconNode;
    private ArrayList listeners = new ArrayList();
    private final IUserComponent simSharingObject;

    public DefaultIconButton( IUserComponent simSharingObject, int buttonHeight, Shape shape ) {
        super( buttonHeight );
        this.simSharingObject = simSharingObject;
        iconNode = new AimxcelPPath( shape, Color.BLACK, new BasicStroke( 1 ), Color.LIGHT_GRAY );
        addChild( iconNode );

        // this handler ensures that the button won't fire unless the mouse is released while inside the button
        ButtonEventHandler handler = new ButtonEventHandler();
        addInputEventListener( handler );
        handler.addButtonEventListener( new ButtonEventHandler.ButtonEventAdapter() {
            public void fire() {
                if ( isEnabled() ) {
                    notifyListeners();
                }
            }
        } );
    }

    protected void updateImage() {
        super.updateImage();
        iconNode.setPaint( isEnabled() ? Color.black : Color.gray );
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void notifyListeners() {
        SimSharingManager.sendUserMessage( simSharingObject, UserComponentTypes.button, pressed );
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).buttonPressed();
        }
    }

    public static interface Listener {
        void buttonPressed();
    }
}
