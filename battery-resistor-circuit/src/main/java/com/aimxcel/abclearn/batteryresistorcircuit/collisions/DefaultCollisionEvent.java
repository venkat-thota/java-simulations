package com.aimxcel.abclearn.batteryresistorcircuit.collisions;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;
import com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d.Core;
import com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d.Oscillate;
import com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d.OscillateFactory;

public class DefaultCollisionEvent implements CollisionEvent, Law {
    double distThreshold;
    double amplitudeThreshold;
    OscillateFactory of;
    double velocityToZero;//greater than this means you stop, even if the core is moving.

    public DefaultCollisionEvent( double distThreshold, double amplitudeThreshold, OscillateFactory of )//,double velocityToZero)
    {
        this.velocityToZero = Double.POSITIVE_INFINITY;
        this.of = of;
        this.distThreshold = distThreshold;
        this.amplitudeThreshold = amplitudeThreshold;
    }

    double time;

    public void iterate( double dt, System2D sys ) {
        time += dt;
        //System.err.println("time="+time);
    }

    public void collide( Core c, Electron wp ) {
        double dx = c.getScalarPosition() - wp.getPosition();
        Oscillate osc = (Oscillate) c.getPropagator();
        //System.err.println("DX="+dx);
        double v = wp.getVelocity();
        if ( Math.abs( dx ) < distThreshold ) {
            if ( osc.getAmplitude() < amplitudeThreshold ) {
                if ( wp.getLastCollision() != c ) {
                    //wp.setAcceleration(0);
                    //wp.setPosition(10);
                    //System.err.println("Collision!");
                    Oscillate o = of.newOscillate( v, c );
                    c.setPropagator( o );
                    //wp.setVelocity(0);
                    //o.reset();
                    wp.setCollided( true );
                    wp.setLastCollision( c, time );
                }
            }
            else if ( v >= velocityToZero ) {
                //wp.setVelocity(0);
                wp.setCollided( true );
            }
        }
    }

    public double currentTime() {
        return time;
    }
}
