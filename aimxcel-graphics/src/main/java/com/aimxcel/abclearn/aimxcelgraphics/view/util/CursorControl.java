
 package com.aimxcel.abclearn.aimxcelgraphics.view.util;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;


public class CursorControl implements MouseInputListener {
    private Cursor cursor;
    private Cursor exitCursor;

    public CursorControl() {
        this( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
    }

    public CursorControl( Cursor cursor ) {
        this( cursor, Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }

    public CursorControl( Cursor cursor, Cursor exitCursor ) {
        this.cursor = cursor;
        this.exitCursor = exitCursor;
    }

    public void mouseClicked( MouseEvent e ) {
    }

    public void mousePressed( MouseEvent e ) {
    }

    public void mouseReleased( MouseEvent e ) {
    }

    public void mouseEntered( MouseEvent e ) {
        e.getComponent().setCursor( cursor );
    }

    public void mouseExited( MouseEvent e ) {
        e.getComponent().setCursor( exitCursor );
    }

    public void mouseDragged( MouseEvent e ) {
    }

    public void mouseMoved( MouseEvent e ) {
    }

}
