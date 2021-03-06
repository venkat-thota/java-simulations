
package com.aimxcel.abclearn.signalcircuit;

import javax.swing.*;

import com.aimxcel.abclearn.signalcircuit.paint.Painter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectableArrow implements Painter, ActionListener {
    Painter p;
    JCheckBox source;
    Component repaint;

    public SelectableArrow( JCheckBox source, Painter p, Component repaint ) {
        this.source = source;
        this.p = p;
        this.repaint = repaint;
    }

    public void actionPerformed( ActionEvent ae ) {
        repaint.repaint();
    }

    public void paint( Graphics2D g ) {
        if( source.isSelected() ) {
            p.paint( g );
        }
    }
}
