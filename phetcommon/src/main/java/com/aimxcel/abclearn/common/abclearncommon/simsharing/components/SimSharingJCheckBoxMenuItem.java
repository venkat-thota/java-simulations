
package com.aimxcel.abclearn.common.abclearncommon.simsharing.components;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.*;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet.parameterSet;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes;

/**
 * CheckBoxMenuItem used in phetcommon for transmitting data on student usage of menus, see #3144
 *
 * @author Sam Reid
 */
public class SimSharingJCheckBoxMenuItem extends JCheckBoxMenuItem {

    private final IUserComponent userComponent;

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent ) {
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, Icon icon ) {
        super( icon );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, String text ) {
        super( text );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, Action a ) {
        super( a );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, String text, Icon icon ) {
        super( text, icon );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, String text, boolean b ) {
        super( text, b );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJCheckBoxMenuItem( IUserComponent userComponent, String text, Icon icon, boolean b ) {
        super( text, icon, b );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    //Make sure processMouseEvent gets called even if no listeners registered.  See http://www.dickbaldwin.com/java/Java102.htm#essential_ingredients_for_extending_exis
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
        sendUserMessage( ParameterSet.parameterSet( isSelected, isSelected() ) );
        super.fireActionPerformed( event );
    }

    private void sendUserMessage( ParameterSet parameterSet ) {
        SimSharingManager.sendUserMessage( userComponent, UserComponentTypes.checkBoxMenuItem, UserActions.pressed, parameterSet );
    }
}
