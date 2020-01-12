package com.aimxcel.abclearn.batteryresistorcircuit;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.DoublePoint;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WirePatch;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireSystem;
import com.aimxcel.abclearn.batteryresistorcircuit.gui.VoltageListener;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.WireRegion;

public class AngelPaint implements Painter, VoltageListener {
    double v;
    WireRegion region;
    BufferedImage left;
    BufferedImage right;
    WireSystem ws;
    WirePatch converter;
    Point dx;
    JCheckBox jcb;
    Point dx2;

    public AngelPaint( WireRegion region, BufferedImage left, BufferedImage right, WireSystem ws, WirePatch converter, Point dx, Point dx2, JCheckBox jcb ) {
        this.dx2 = dx2;
        this.jcb = jcb;
        this.dx = dx;
        this.converter = converter;
        this.ws = ws;
        this.region = region;
        this.left = left;
        this.right = right;
    }

    public void paint( Graphics2D g ) {
        if ( !jcb.isSelected() ) {
            return;
        }
        for ( int i = 0; i < ws.numParticles(); i++ ) {
            WireParticle wp = ws.particleAt( i );
            if ( region.contains( wp ) ) {
                //Paint a pusher.
                DoublePoint loc = converter.getPosition( wp.getPosition() );
                if ( v > 0 ) {
                    AffineTransform at = AffineTransform.getTranslateInstance( loc.getX() + dx.x, loc.getY() + dx.y );
                    g.drawRenderedImage( right, at );
                }
                else if ( v < 0 ) {
                    AffineTransform at = AffineTransform.getTranslateInstance( loc.getX() + dx2.x, loc.getY() + dx2.y );
                    g.drawRenderedImage( left, at );
                }
            }
        }
    }

    public void valueChanged( double val ) {
        this.v = val;
    }
}
