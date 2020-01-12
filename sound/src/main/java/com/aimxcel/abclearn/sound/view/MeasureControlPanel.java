package com.aimxcel.abclearn.sound.view;

import javax.swing.*;

import com.aimxcel.abclearn.sound.SingleSourceMeasureModule;
import com.aimxcel.abclearn.sound.SoundResources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeasureControlPanel extends SoundControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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