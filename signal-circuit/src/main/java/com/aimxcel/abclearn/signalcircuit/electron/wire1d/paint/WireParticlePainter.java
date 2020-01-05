
package com.aimxcel.abclearn.signalcircuit.electron.wire1d.paint;

import java.awt.*;

import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WireParticle;
import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WirePatch;
import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WireSystem;
import com.aimxcel.abclearn.signalcircuit.paint.Painter;
import com.aimxcel.abclearn.signalcircuit.phys2d.DoublePoint;
import com.aimxcel.abclearn.signalcircuit.phys2d.Particle;

public class WireParticlePainter implements Painter {
    WireSystem ws;
    WirePatch converter;

    public WireParticlePainter( WireSystem ws, WirePatch converter ) {
        this.ws = ws;
        this.converter = converter;
    }

    public void paint( Graphics2D g ) {
        for( int i = 0; i < ws.numParticles(); i++ ) {
            paint( ws.particleAt( i ), g );
        }
    }

    public void paint( WireParticle p, Graphics2D g ) {
        DoublePoint dp = converter.getPosition( p.getPosition() );
        if( dp == null ) {
            return;
        }
        //edu.colorado.phet.util.Debug.traceln("Got position="+p.getPosition()+"->"+dp+"\n");
        Particle px = new Particle();
        px.setPosition( dp );
        p.getPainter().paint( px, g );
    }
}
