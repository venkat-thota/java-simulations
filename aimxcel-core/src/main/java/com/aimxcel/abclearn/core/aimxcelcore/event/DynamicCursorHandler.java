

package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.awt.Cursor;

import javax.swing.JComponent;

import edu.umd.cs.piccolo.event.PInputEvent;


public class DynamicCursorHandler extends CursorHandler {
    private JComponent mouseOverComponent = null; // the component that the mouse is over

    public DynamicCursorHandler() {
        this( HAND );
    }

    
    public DynamicCursorHandler( int cursorType ) {
        this( Cursor.getPredefinedCursor( cursorType ) );
    }

   
    public DynamicCursorHandler( Cursor cursor ) {
        super( cursor );
    }

    
    public void setCursor( int cursor ) {
        this.cursor = Cursor.getPredefinedCursor( cursor );
        if ( mouseOverComponent != null ) {
            manager.lastEntered = this.cursor;
            mouseOverComponent.setCursor( this.cursor );
        }
    }

 
    public void mouseEntered( PInputEvent event ) {
        super.mouseEntered( event );
        mouseOverComponent = (JComponent) event.getComponent();
    }

    public void mouseExited( PInputEvent event ) {
        super.mouseExited( event );
        mouseOverComponent = null;
    }
}