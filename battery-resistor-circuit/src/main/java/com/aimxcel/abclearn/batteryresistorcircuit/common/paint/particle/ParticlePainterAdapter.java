package com.aimxcel.abclearn.batteryresistorcircuit.common.paint.particle;

import java.awt.*;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Particle;

public class ParticlePainterAdapter implements Painter {
    ParticlePainter p;
    Particle part;

    public ParticlePainterAdapter( ParticlePainter p, Particle part ) {
        this.p = p;
        this.part = part;
    }

    public String toString() {
        return getClass().getName() + ", Particle=" + part + ", Paint=" + p;
    }

    public void paint( Graphics2D g ) {
        p.paint( part, g );
    }

}
