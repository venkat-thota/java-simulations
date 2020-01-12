
package com.aimxcel.abclearn.conductivity.macro;

import java.util.ArrayList;

import com.aimxcel.abclearn.conductivity.macro.bands.ConductorBandSet;
import com.aimxcel.abclearn.conductivity.macro.bands.DefaultBandSet;
import com.aimxcel.abclearn.conductivity.macro.bands.InsulatorBandSet;
import com.aimxcel.abclearn.conductivity.macro.bands.PhotoconductorBandSet;
import com.aimxcel.abclearn.conductivity.macro.battery.Battery;
import com.aimxcel.abclearn.conductivity.macro.battery.BatteryListener;
import com.aimxcel.abclearn.conductivity.macro.circuit.MacroCircuit;
import com.aimxcel.abclearn.conductivity.macro.particles.WireParticle;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;

public class MacroSystem
        implements ModelElement {

    public DefaultBandSet getBandSet() {
        return bandSet;
    }

    public MacroSystem( double minVolts, double maxVolts, double particleWidth ) {
        particles = new ArrayList();
        this.minVolts = minVolts;
        this.maxVolts = maxVolts;
        circuit = new MacroCircuit();
        Battery battery = circuit.getBattery();
        battery.addBatteryListener( new BatteryListener() {

            public void voltageChanged( Battery battery1 ) {
                doVoltageChanged();
            }

        } );
        conductor = new ConductorBandSet( this, particleWidth );
        insulator = new InsulatorBandSet( this, particleWidth );
        photoconductor = new PhotoconductorBandSet( this, particleWidth );
        setBandSet( conductor );
    }

    public MacroCircuit getCircuit() {
        return circuit;
    }

    public void doVoltageChanged() {
        double d = circuit.getBattery().getVoltage();
        double d1 = -0.0060000000000000001D;
        double d2 = 0.0D;
        double d3 = maxVolts - minVolts;
        double d4 = d1 - d2;
        double d5 = d4 / d3;
        double d6 = d2;
        recommendedSpeed = d * d5 + d6;
        double d7 = bandSet.voltageChanged( d, recommendedSpeed );
        for ( int i = 0; i < particles.size(); i++ ) {
            WireParticle wireparticle = (WireParticle) particles.get( i );
            wireparticle.setSpeed( d7 );
        }

    }

    public void stepInTime( double d ) {
        bandSet.stepInTime( d );
    }

    public void setBandSet( DefaultBandSet defaultbandset ) {
        bandSet = defaultbandset;
    }

    public void photonHit() {
        photoconductor.photonHit();
    }

    public ConductorBandSet getConductor() {
        return conductor;
    }

    public InsulatorBandSet getInsulator() {
        return insulator;
    }

    public PhotoconductorBandSet getPhotoconductor() {
        return photoconductor;
    }

    public void photonGotAbsorbed() {
        double d = circuit.getBattery().getVoltage();
        double d1 = -0.0060000000000000001D;
        double d2 = 0.0D;
        double d3 = maxVolts - minVolts;
        double d4 = d1 - d2;
        double d5 = d4 / d3;
        double d6 = d2;
        recommendedSpeed = d * d5 + d6;
        double d7 = bandSet.desiredSpeedToActualSpeed( recommendedSpeed );
        for ( int i = 0; i < particles.size(); i++ ) {
            WireParticle wireparticle = (WireParticle) particles.get( i );
            wireparticle.setSpeed( d7 );
        }

    }

    private MacroCircuit circuit;
    public ArrayList particles;
    private double minVolts;
    private double maxVolts;
    DefaultBandSet bandSet;
    private double recommendedSpeed;
    private ConductorBandSet conductor;
    private InsulatorBandSet insulator;
    private PhotoconductorBandSet photoconductor;
}
