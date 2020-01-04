
package com.aimxcel.abclearn.signalcircuit.electron.wire1d.paint;

import java.awt.*;

import com.aimxcel.abclearn.electron.wire1d.WirePatch;
import com.aimxcel.abclearn.electron.wire1d.WireSegment;
import com.aimxcel.abclearn.signalcircuit.paint.Painter;
import com.aimxcel.abclearn.signalcircuit.phys2d.DoublePoint;

public class WirePatchPainter implements Painter {
    Stroke s;
    Color c;
    WirePatch wp;

    public WirePatchPainter( Stroke s, Color c, WirePatch wp ) {
        this.s = s;
        this.c = c;
        this.wp = wp;
    }

    public void paint( Graphics2D g ) {
        for( int i = 0; i < wp.numSegments(); i++ ) {
            WireSegment ws = wp.segmentAt( i );
            g.setStroke( s );
            g.setColor( c );
            DoublePoint start = ws.getStart();
            DoublePoint end = ws.getFinish();
            g.drawLine( (int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY() );
        }
    }
}
