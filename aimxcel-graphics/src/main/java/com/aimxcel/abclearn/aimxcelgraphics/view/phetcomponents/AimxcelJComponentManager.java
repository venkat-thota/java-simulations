

package com.aimxcel.abclearn.aimxcelgraphics.view.phetcomponents;

import java.util.ArrayList;



public class AimxcelJComponentManager {
    private ArrayList listeners = new ArrayList();

    public static interface Listener {
        void phetJComponentCreated( AimxcelJComponent phetJComponent );
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void phetJComponentCreated( AimxcelJComponent phetJComponent ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.phetJComponentCreated( phetJComponent );
        }
    }

}