
package com.aimxcel.abclearn.signalcircuit.paint;

import java.awt.*;
import java.util.Vector;

public class CompositePainter implements Painter {
    Vector<Painter> v;

    public CompositePainter() {
        v = new Vector<Painter>();
    }

    public String toString() {
        return getClass().getName() + ": " + v;
    }

    public void addPainter( Painter p ) {
        v.add( p );
    }

    public Painter painterAt( int i ) {
        return (Painter)v.get( i );
    }

    public void paint( Graphics2D g ) {
        for( int i = 0; i < v.size(); i++ ) {
            painterAt( i ).paint( g );
        }
    }
}


