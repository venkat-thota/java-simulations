
package com.aimxcel.abclearn.motion.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;



public class GraphicsUtil {
    public static boolean antialias( Graphics g, boolean antialias ) {
        Graphics2D g2 = (Graphics2D) g;
        boolean aa = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING ) == RenderingHints.VALUE_ANTIALIAS_ON;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF );
        return aa;
    }
}
