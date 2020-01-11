// Copyright 2002-2011, University of Colorado
package edu.colorado.phet.theramp.v2.view;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import edu.colorado.phet.theramp.v2.model.RampModel;
import edu.colorado.phet.theramp.v2.model.RampObject;

public class TestRampModule extends Module {
    private RampModel currentState = new RampModel();
    private ArrayList listeners = new ArrayList();

    public TestRampModule() {
        super( "test ramp", new ConstantDtClock( 30, 1 ) );
        setSimulationPanel( new RampSimPanel( this ) );
        addModelElement( new ModelElement() {
            public void stepInTime( double dt ) {
                debugState();
            }
        } );
    }

    private void debugState() {
        System.out.println( "CurrentState=" + currentState );
    }

    public RampModel getCurrentState() {
        return currentState;
    }

    public void setCurrentState( RampModel rampModel ) {
        currentState = rampModel;
        notifyListeners();
    }

    public void updateCurrentState( RampObject object, RampObject newObject ) {
        setCurrentState( getCurrentState().update( object, newObject ) );
    }

    public static interface Listener {
        public void notifyChanged();
    }

    public void notifyListeners() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).notifyChanged();
        }
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void removeListener( Listener listener ) {
        listeners.remove( listener );
    }
}
