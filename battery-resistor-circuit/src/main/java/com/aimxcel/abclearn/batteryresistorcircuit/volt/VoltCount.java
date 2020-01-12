package com.aimxcel.abclearn.batteryresistorcircuit.volt;


import com.aimxcel.abclearn.batteryresistorcircuit.BatteryResistorCircuitStrings;
import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.TextPainter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;

public class VoltCount implements Law {
    Batt batt;
    TextPainter tp;
    TextPainter tot;
    TextPainter rightTp;

    public VoltCount( TextPainter tp, Batt batt, TextPainter tot, TextPainter right ) {
        this.rightTp = right;
        this.tot = tot;
        this.tp = tp;
        this.batt = batt;
    }

    public void iterate( double dt, System2D sys ) {
        int left = batt.countLeft();
        int right = batt.countRight();
        int total = right - left;
        String text = right + " " + BatteryResistorCircuitStrings.get( "VoltCount.Electrons" );
        String textRight = "- " + left + " " + BatteryResistorCircuitStrings.get( "VoltCount.Electrons" );
        rightTp.setText( textRight );
        tp.setText( text );
        String tp2 = "= " + total + " " + BatteryResistorCircuitStrings.get( "VoltCount.Volts" );
        tot.setText( tp2 );
    }
}
