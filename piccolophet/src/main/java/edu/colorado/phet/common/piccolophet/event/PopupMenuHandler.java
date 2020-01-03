
package edu.colorado.phet.common.piccolophet.event;

import java.awt.Component;

import javax.swing.JPopupMenu;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions.popupTriggered;

public class PopupMenuHandler extends PBasicInputEventHandler {
    private final IUserComponent userComponent;
    private Component parent;
    private JPopupMenu popupMenu;

    public PopupMenuHandler( IUserComponent userComponent, Component parent, JPopupMenu popupMenu ) {
        this.userComponent = userComponent;
        this.parent = parent;
        this.popupMenu = popupMenu;
    }

    /**
     * right-click popup menu on mac is fired on mousePressed, not mouseReleased:
     * http://developer.apple.com/technotes/tn/tn2042.html
     *
     * @param event
     */
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