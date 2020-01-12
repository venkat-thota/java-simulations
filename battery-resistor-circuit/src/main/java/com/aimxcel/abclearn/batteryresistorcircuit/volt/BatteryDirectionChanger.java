package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import com.aimxcel.abclearn.batteryresistorcircuit.gui.VoltageListener;

public class BatteryDirectionChanger implements VoltageListener {
    BatteryPainter bp;

    public BatteryDirectionChanger( BatteryPainter bp ) {
        this.bp = bp;
    }

    public void valueChanged( double val ) {
        if ( val < 0 ) {
            bp.setLeft( true );
        }
        else {
            bp.setLeft( false );
        }
    }

}

