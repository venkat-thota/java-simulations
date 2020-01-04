
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


public class AimxcelExit {
    private static ArrayList<VoidFunction0> exitListeners = new ArrayList<VoidFunction0>();//Listeners that are notified just before the AimxcelApplication exits

   
    public static void addExitListener( VoidFunction0 listener ) {
        exitListeners.add( listener );
    }

    
    public static void exit() {
        for ( VoidFunction0 exitListener : exitListeners ) {
            exitListener.apply();
        }

          sendSystemMessage( SystemComponents.application, SystemComponentTypes.application, exited, parameterSet( messageCount, getInstance().getMessageCount() ) );

          getInstance().shutdown();

      
        System.exit( 0 );
    }
}