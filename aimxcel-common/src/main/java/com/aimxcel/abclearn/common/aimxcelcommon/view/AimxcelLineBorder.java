
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.border.LineBorder;


public class AimxcelLineBorder extends LineBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int CORNER_RADIUS = 8; // radius of the rounded corners (aka arc width)

    public AimxcelLineBorder() {
        this( Color.black );
    }

    public AimxcelLineBorder( Color color ) {
        super( color, 1, true );
    }

    @Override
    public void paintBorder( Component c, Graphics g, int x, int y, int width, int height ) {
        //Overrides to add antialiasing (otherwise looks terrible on Windows) and to curve the edges
        Graphics2D g2 = (Graphics2D) g;
        Object oldAntialiasHint = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING );
        Color oldColor = g2.getColor();
        g2.setColor( getLineColor() );
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g2.drawRoundRect( x, y, width - 1, height - 1, CORNER_RADIUS, CORNER_RADIUS );
        g2.setColor( oldColor );
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, oldAntialiasHint );
    }
}
