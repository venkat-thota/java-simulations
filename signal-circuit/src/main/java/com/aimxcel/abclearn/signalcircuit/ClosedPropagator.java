
package com.aimxcel.abclearn.signalcircuit;

import java.util.Vector;

import com.aimxcel.abclearn.electron.wire1d.Propagator1d;
import com.aimxcel.abclearn.electron.wire1d.WireParticle;
import com.aimxcel.abclearn.electron.wire1d.WirePatch;
import com.aimxcel.abclearn.signalcircuit.phys2d.Law;
import com.aimxcel.abclearn.signalcircuit.phys2d.System2D;

/*Sends messages to SignalListeners.*/

public class ClosedPropagator implements Propagator1d, Law, SwitchListener {
    boolean switchClosed;
    WirePatch patch;
    double fore;
    double back;
    Propagator1d inside;
    Propagator1d outside;
    double backSpeed;
    double foreSpeed;
    Vector list = new Vector();

    public ClosedPropagator( WirePatch patch, Propagator1d inside, Propagator1d outside, double backSpeed, double foreSpeed ) {
        this.inside = inside;
        this.outside = outside;
        this.backSpeed = backSpeed;
        this.foreSpeed = foreSpeed;
        this.patch = patch;
    }

    public void setSwitchClosed( boolean c ) {
        this.switchClosed = c;
        if( !c ) {
            fore = 0;
            back = patch.getLength();
        }
        notifyListeners();
    }

    public void addSignalListener( SignalListener s ) {
        list.add( s );
    }

    public void iterate( double dt, System2D sys ) {
        if( switchClosed ) {
            fore += foreSpeed * dt;
            back -= backSpeed * dt;
        }
        notifyListeners();
    }

    public void notifyListeners() {
        for( int i = 0; i < list.size(); i++ ) {
            ( (SignalListener)list.get( i ) ).signalMoved( fore, back );
        }
    }

    public void propagate( WireParticle wp, double dt ) {
        //o.O.d(""+signalPosition);
        double pos = wp.getPosition();
        //double len=patch.getLength();
        if( pos <= fore || pos >= back ) {
            inside.propagate( wp, dt );
        }
        else {
            outside.propagate( wp, dt );
            //Just use the correction term.
        }
    }
}
