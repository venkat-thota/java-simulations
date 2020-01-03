
package com.aimxcel.abclearn.common.abclearncommon.simsharing.components;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.enabled;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions.pressed;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes.menu;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JMenu;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes;

/**
 * Menu used in phetcommon for transmitting data on student usage of menus, see #3144
 *
 * @author Sam Reid
 */
public class SimSharingJMenu extends JMenu {

    private final IUserComponent userComponent;

    public SimSharingJMenu( IUserComponent userComponent ) {
        this.userComponent = userComponent;
    }

    public SimSharingJMenu( IUserComponent userComponent, String text ) {
        super( text );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJMenu( IUserComponent userComponent, Action action ) {
        super( action );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJMenu( IUserComponent userComponent, String text, boolean canBeTornOff ) {
        super( text, canBeTornOff );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    private void enableMouseEvents() {
        enableEvents( AWTEvent.MOUSE_EVENT_MASK );
    }

    //When mouse is pressed, send a simsharing event if the component is disabled.  Safer to override than add listener, since the listener could be removed with removeAllListeners().
    //Only works if enableEvents has been called.  See #3218
    @Override protected void processMouseEvent( MouseEvent e ) {
        if ( e.getID() == MouseEvent.MOUSE_PRESSED && !isEnabled() ) {
            sendUserMessage( parameterSet( enabled, isEnabled() ).with( interactive, isEnabled() ) );
        }
        super.processMouseEvent( e );
    }

    @Override protected void fireActionPerformed( ActionEvent event ) {
        SimSharingManager.sendUserMessage( userComponent, menu, pressed );
        super.fireActionPerformed( event );
    }

    @Override protected void fireMenuSelected() {
        sendUserMessage( new ParameterSet() );
        super.fireMenuSelected();
    }

    private void sendUserMessage( ParameterSet parameterSet ) {
        SimSharingManager.sendUserMessage( userComponent, UserComponentTypes.menu, pressed, parameterSet );
    }
}
