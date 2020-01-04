
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager.getInstance;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager.sendSystemMessage;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.messageCount;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemActions.exited;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;

/**
 * AbcLearnExit encapsulates the various ways of exiting a sim.  It also sends out notifications before System.exit(0) is
 * called in case any final work needs to be done, as in the case of sim sharing.
 *
 * @author Sam Reid
 */
public class AimxcelExit {
    private static ArrayList<VoidFunction0> exitListeners = new ArrayList<VoidFunction0>();//Listeners that are notified just before the AbcLearnApplication exits

    /**
     * Add a listener that will be notified just before the AbcLearnApplication exits.
     *
     * @param listener the listener to notify just before the AbcLearnApplication exits
     */
    public static void addExitListener( VoidFunction0 listener ) {
        exitListeners.add( listener );
    }

    /**
     * Notifies any listeners that the AbcLearnApplication is about to exit,
     * then exits the application by closing the VM with System.exit(0)
     */
    public static void exit() {
        for ( VoidFunction0 exitListener : exitListeners ) {
            exitListener.apply();
        }

        // Send a message for sim exit, work for both frame closing and File - > Exit( but not application kill )
        // Normally database log messages are sent asynchronously which means that sometimes the app exits before this message gets sent
        sendSystemMessage( SystemComponents.application, SystemComponentTypes.application, exited, parameterSet( messageCount, getInstance().getMessageCount() ) );

        //Close logs, send final messages for simsharing, etc before System.exit
        getInstance().shutdown();

        //Close the sim
        System.exit( 0 );
    }
}