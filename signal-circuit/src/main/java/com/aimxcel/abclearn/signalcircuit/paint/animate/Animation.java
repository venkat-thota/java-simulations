
package com.aimxcel.abclearn.signalcircuit.paint.animate;

import com.aimxcel.abclearn.signalcircuit.paint.Painter;

/*Can return new Painters, or just modify a single one...*/

public interface Animation {
    public int numFrames();

    public Painter frameAt( int i );
}
