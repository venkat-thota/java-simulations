
package com.aimxcel.abclearn.signalcircuit;

import javax.swing.*;

import com.aimxcel.abclearn.signalcircuit.paint.Painter;

import java.awt.*;

public class ShowElectrons implements Painter {
    JCheckBox jcb;
    Painter p;

    public ShowElectrons( JCheckBox jcb, Painter p ) {
        this.jcb = jcb;
        this.p = p;
    }

    public void paint( Graphics2D g ) {
        if( jcb.isSelected() ) {
            p.paint( g );
        }
    }
}
