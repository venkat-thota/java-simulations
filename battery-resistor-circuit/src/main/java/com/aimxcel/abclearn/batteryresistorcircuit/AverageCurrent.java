package com.aimxcel.abclearn.batteryresistorcircuit;

import java.util.ArrayList;
import java.util.Vector;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.gauges.IGauge;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.gui.CoreCountListener;
import com.aimxcel.abclearn.batteryresistorcircuit.gui.VoltageListener;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.CurrentListener;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.WireRegion;
import com.aimxcel.abclearn.common.aimxcelcommon.math.DoubleSeries;


/**
 * Sets the value of the gauge.
 */
public class AverageCurrent implements Law, VoltageListener, CoreCountListener {
    ArrayList al = new ArrayList();
    IGauge ig;
    DoubleSeries ds;
    WireRegion region;
    double resistance;
    double voltage;
    Vector listeners = new Vector();

    public void addCurrentListener( CurrentListener cl ) {
        this.listeners.add( cl );
    }

    public void valueChanged( double v ) {
        this.resistance = v;
    }

    public void coreCountChanged( int x ) {
        this.voltage = x;
    }

    public AverageCurrent( IGauge ig, int numPoints, WireRegion region ) {
        this.region = region;
        ds = new DoubleSeries( numPoints );
        this.ig = ig;
    }

    public void addParticle( WireParticle p ) {
        al.add( p );
    }

    public void iterate( double dt, System2D sys ) {
        double sum = 0;
        int n = 0;
        for ( int i = 0; i < al.size(); i++ ) {
            WireParticle wp = (WireParticle) al.get( i );
            if ( region.contains( wp ) ) {
                sum += wp.getVelocity() * wp.getCharge();
                n++;
            }
        }
        if ( n != 0 ) {
            sum /= n;
        }
        double hollyscale = 3.5 * 3.3;
        //double hollyscale = 2.5;
        sum = 0;//no hollywood.
        double hollywood = resistance / voltage * hollyscale;
        double total = ( sum + hollywood );
        ds.add( total );
        double display = ds.average();
        ig.setValue( display * .4 );
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (CurrentListener) listeners.get( i ) ).currentChanged( display );
        }
//  	if (count++%100==0)
//  	    System.err.println("hollywood="+hollywood+", sum="+sum+", tot="+total);
    }

}
