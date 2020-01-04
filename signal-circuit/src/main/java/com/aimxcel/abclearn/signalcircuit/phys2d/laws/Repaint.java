
package com.aimxcel.abclearn.signalcircuit.phys2d.laws;

import java.awt.*;

import com.aimxcel.abclearn.signalcircuit.phys2d.Law;
import com.aimxcel.abclearn.signalcircuit.phys2d.System2D;

public class Repaint implements Law {
    Component p;

    public Repaint( Component p ) {
        this.p = p;
    }

    public void iterate( double time, System2D system ) {
        p.repaint();
    }
}
