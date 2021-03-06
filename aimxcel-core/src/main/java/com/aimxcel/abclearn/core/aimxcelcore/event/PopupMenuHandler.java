
package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.awt.Component;

import javax.swing.JPopupMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.popupTriggered;

public class PopupMenuHandler extends PBasicInputEventHandler {
    private final IUserComponent userComponent;
    private Component parent;
    private JPopupMenu popupMenu;

    public PopupMenuHandler( IUserComponent userComponent, Component parent, JPopupMenu popupMenu ) {
        this.userComponent = userComponent;
        this.parent = parent;
        this.popupMenu = popupMenu;
    }

    
    public void mousePressed( PInputEvent event ) {
        super.mouseReleased( event );
        handlePopup( event );
    }

    public void mouseReleased( PInputEvent event ) {
        super.mouseReleased( event );
        handlePopup( event );
    }

    private void handlePopup( PInputEvent event ) {
        if ( event.isPopupTrigger() ) {
            popupMenu.show( parent, (int) event.getCanvasPosition().getX(), (int) event.getCanvasPosition().getY() );
        }
    }
}