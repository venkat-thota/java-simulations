
package com.aimxcel.abclearn.conductivity.macro.battery;

import java.util.ArrayList;

import com.aimxcel.abclearn.conductivity.macro.circuit.LinearBranch;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class Battery extends LinearBranch {

    public Battery( MutableVector2D aimxcelvector, MutableVector2D aimxcelvector1 ) {
        super( aimxcelvector, aimxcelvector1 );
        volts = 0.0D;
        obs = new ArrayList();
    }

    public void addBatteryListener( BatteryListener batterylistener ) {
        obs.add( batterylistener );
    }

    public void setVoltage( double d ) {
        volts = d;
        for ( int i = 0; i < obs.size(); i++ ) {
            BatteryListener batterylistener = (BatteryListener) obs.get( i );
            batterylistener.voltageChanged( this );
        }

    }

    public double getVoltage() {
        return volts;
    }

    private double volts;
    ArrayList obs;
}
