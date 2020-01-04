
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.enabled;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes.icon;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;


public class SimSharingIcon extends JLabel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IUserComponent object;
    private final VoidFunction0 function;

    public SimSharingIcon( IUserComponent object, Icon icon, final VoidFunction0 function ) {
        super( icon );
        this.object = object;
        this.function = function;
        enableEvents( AWTEvent.MOUSE_EVENT_MASK );
    }

     @Override protected void processMouseEvent( MouseEvent e ) {
        if ( e.getID() == MouseEvent.MOUSE_PRESSED ) {
            SimSharingManager.sendUserMessage( object, icon, pressed, parameterSet( enabled, isEnabled() ).with( interactive, isEnabled() ) );
            function.apply();
        }
        super.processMouseEvent( e );
    }
}