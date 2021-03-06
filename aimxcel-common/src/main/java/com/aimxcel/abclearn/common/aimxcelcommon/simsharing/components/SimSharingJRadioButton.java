
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.enabled;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes.radioButton;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;

public class SimSharingJRadioButton extends JRadioButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IUserComponent userComponent;

    public SimSharingJRadioButton( IUserComponent userComponent ) {
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, Icon icon ) {
        super( icon );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, Action a ) {
        super( a );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, Icon icon, boolean selected ) {
        super( icon, selected );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, String text ) {
        super( text );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, String text, boolean selected ) {
        super( text, selected );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, String text, Icon icon ) {
        super( text, icon );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJRadioButton( IUserComponent userComponent, String text, Icon icon, boolean selected ) {
        super( text, icon, selected );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    @Override protected void fireActionPerformed( ActionEvent event ) {
        sendUserMessage( new ParameterSet() );
        super.fireActionPerformed( event );
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

    private void sendUserMessage( ParameterSet parameterSet ) {
        SimSharingManager.sendUserMessage( userComponent, radioButton, pressed, parameterSet );
    }
}
