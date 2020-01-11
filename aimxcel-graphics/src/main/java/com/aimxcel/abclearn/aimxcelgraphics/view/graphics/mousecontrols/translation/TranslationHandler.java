
 package com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class TranslationHandler implements MouseInputListener {

    TranslationListener translationListener;
    private Point last;

    public TranslationHandler( TranslationListener translationListener ) {
        this.translationListener = translationListener;
    }

    public void mouseDragged( MouseEvent event ) {
        if ( last == null ) {
            mousePressed( event );
            return;
        }
        Point modelLoc = event.getPoint();
        Point dx = new Point( modelLoc.x - last.x, modelLoc.y - last.y );
        TranslationEvent trEvent = new TranslationEvent( this, event, event.getX(), event.getY(), dx.x, dx.y );
        translationListener.translationOccurred( trEvent );
        last = modelLoc;
    }

    public void mouseMoved( MouseEvent e ) {
    }

    public void mouseClicked( MouseEvent e ) {
    }

    public void mousePressed( MouseEvent event ) {
        last = event.getPoint();
    }

    public void mouseReleased( MouseEvent e ) {
    }

    public void mouseEntered( MouseEvent e ) {
    }

    public void mouseExited( MouseEvent e ) {
    }

    //////////////////////////////////////////////////////////
    // Persistence support
    //
    public TranslationHandler() {
    }

    public TranslationListener getTranslationListener() {
        return translationListener;
    }

    public void setTranslationListener( TranslationListener translationListener ) {
        this.translationListener = translationListener;
    }

    public Point getLast() {
        return last;
    }

    public void setLast( Point last ) {
        this.last = last;
    }
}
