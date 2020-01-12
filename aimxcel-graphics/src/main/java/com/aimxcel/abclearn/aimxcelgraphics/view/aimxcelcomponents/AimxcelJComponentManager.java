

package com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelcomponents;

import java.util.ArrayList;



public class AimxcelJComponentManager {
    private ArrayList listeners = new ArrayList();

    public static interface Listener {
        void aimxcelJComponentCreated( AimxcelJComponent aimxcelJComponent );
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void aimxcelJComponentCreated( AimxcelJComponent aimxcelJComponent ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.aimxcelJComponentCreated( aimxcelJComponent );
        }
    }

}