package com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.paint;

import java.awt.*;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.particle.ParticlePainter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.DoublePoint;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Particle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WirePatch;

public class WireParticlePainter implements Painter {
    Particle temp = new Particle();
    ParticlePainter pp;
    WireParticle wp;

    public WireParticlePainter( WireParticle wp, ParticlePainter pp ) {
        this.pp = pp;
        this.wp = wp;
    }

    public void paint( Graphics2D g ) {
        WirePatch converter = wp.getWirePatch();
        if ( converter == null ) {
            throw new RuntimeException( "Null wire patch." );
        }
        DoublePoint dp = converter.getPosition( wp.getPosition() );
        if ( dp == null ) {
            throw new RuntimeException( "WirePatch returned a null position." );
            //return;
        }
        //util.Debug.traceln("Got position="+p.getPosition()+"->"+dp+"\n");
        temp.setPosition( dp );
        pp.paint( temp, g );
    }
}
