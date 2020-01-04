
package com.aimxcel.abclearn.signalcircuit.paint.particle;

import java.awt.*;

import com.aimxcel.abclearn.signalcircuit.paint.Painter;
import com.aimxcel.abclearn.signalcircuit.phys2d.Particle;

public class ParticlePainterAdapter implements Painter {
    ParticlePainter p;
    Particle part;

    public ParticlePainterAdapter( ParticlePainter p, Particle part ) {
        this.p = p;
        this.part = part;
    }

    public void paint( Graphics2D g ) {
        p.paint( part, g );
    }

}
