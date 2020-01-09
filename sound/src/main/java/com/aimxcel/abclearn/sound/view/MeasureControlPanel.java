// Copyright 2002-2011, University of Colorado

/**
 * Class: MeasureControlPanel
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 9, 2004
 */
package com.aimxcel.abclearn.sound.view;

import javax.swing.*;

import com.aimxcel.abclearn.sound.SingleSourceMeasureModule;
import com.aimxcel.abclearn.sound.SoundResources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeasureControlPanel extends SoundControlPanel {

    public MeasureControlPanel(final SingleSourceMeasureModule module) {
        super(module);
        JButton button = new JButton(SoundResources.getString("MeasureMode.ClearWave")); //See #2374
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                module.clearWaves();
            }
        });
        addControl(button);
    }
}