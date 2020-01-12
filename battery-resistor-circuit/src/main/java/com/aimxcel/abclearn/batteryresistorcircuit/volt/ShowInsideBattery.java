package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ShowInsideBattery implements ActionListener {
    private JCheckBox source;
    private BatteryPainter bp;

    public ShowInsideBattery( JCheckBox source, BatteryPainter bp ) {
        this.source = source;
        this.bp = bp;
    }

    public void actionPerformed( ActionEvent e ) {
        bp.setTransparent( source.isSelected() );
    }
}
