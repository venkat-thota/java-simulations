
 package com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation;

import java.awt.event.MouseEvent;
import java.util.EventObject;


public class TranslationEvent extends EventObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MouseEvent event;
    private int x;
    private int y;
    private int dx;
    private int dy;

    public TranslationEvent( Object source, MouseEvent event, int x, int y, int dx, int dy ) {
        super( source );
        this.event = event;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public MouseEvent getMouseEvent() {
        return event;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
