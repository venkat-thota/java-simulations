
package com.aimxcel.abclearn.signalcircuit.phys2d.laws;

import java.awt.*;

import com.aimxcel.abclearn.signalcircuit.phys2d.Law;
import com.aimxcel.abclearn.signalcircuit.phys2d.System2D;

public class Validate implements Law {
    Container p;

    public Validate( Container p ) {
        this.p = p;
    }

    public void iterate( double time, System2D system ) {
        p.validate();
    }
}
