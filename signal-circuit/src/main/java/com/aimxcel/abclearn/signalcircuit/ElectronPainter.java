
package com.aimxcel.abclearn.signalcircuit;

import javax.swing.*;

import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WireParticle;
import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WirePatch;
import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WireSystem;
import com.aimxcel.abclearn.signalcircuit.paint.Painter;
import com.aimxcel.abclearn.signalcircuit.paint.particle.ParticlePainter;
import com.aimxcel.abclearn.signalcircuit.paint.particle.ParticlePainterAdapter;
import com.aimxcel.abclearn.signalcircuit.phys2d.DoublePoint;
import com.aimxcel.abclearn.signalcircuit.phys2d.Particle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElectronPainter implements ActionListener, SwitchListener, Painter {
    WireParticle taggedParticle;
    JCheckBox box;
    WireSystem ws;
    ParticlePainter normal;
    ParticlePainter tagged;
    Component paint;
    WirePatch wp;

    public ElectronPainter( JCheckBox box, WireSystem ws, ParticlePainter normal, ParticlePainter tagged, Component paint, WirePatch wp ) {
        this.wp = wp;
        this.box = box;
        this.ws = ws;
        this.normal = normal;
        this.tagged = tagged;
        this.paint = paint;
    }

    public void paint( Graphics2D g ) {
        if( taggedParticle != null && box.isSelected() ) {
            double pt = taggedParticle.getPosition();
            double len = wp.getLength();
            if( pt > len ) {
                pt -= len;
            }
            else if( pt < 0 ) {
                pt += len;
            }
            DoublePoint draw = wp.getPosition( pt );
            if( draw == null ) {
                //return;//My Fudge.!!!
                System.out.println( "TaggedParticle=" + taggedParticle + ", pt=" + pt + ", wire length=" + len + ", draw=" + draw );
                throw new RuntimeException( "Set a null position." );
            }
            Particle p = new Particle();
            p.setPosition( draw );
            new ParticlePainterAdapter( tagged, p ).paint( g );
        }
    }

    public void actionPerformed( ActionEvent ae ) {
        paint.repaint();
    }

    public void setSwitchClosed( boolean c ) {
        //Find the edu.colorado.aimxcel.electron to paint.
        if( !c ) {
            if( taggedParticle != null ) {
                taggedParticle.setPainter( normal );
            }
            taggedParticle = null;
        }
        else {
            double lowest = Double.MAX_VALUE;
            WireParticle low = null;
            for( int i = ws.numParticles() - 1; i >= 0; i-- ) {
                WireParticle wp = ws.particleAt( i );
                if( wp.getPosition() <= lowest ) {
                    lowest = wp.getPosition();
                    low = wp;
                }
            }
            //low.setPainter(tagged);
            taggedParticle = low;
        }
    }
}
