package com.aimxcel.abclearn.theramp.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.aimxcel.abclearn.theramp.RampModule;


public class UserAddingEnergyHandler extends MouseAdapter {
    private RampModule module;

    public UserAddingEnergyHandler( RampModule module ) {
        this.module = module;
    }

    public void mousePressed( MouseEvent e ) {
        module.getRampPhysicalModel().setUserIsAddingEnergy( true );
    }

    public void mouseReleased( MouseEvent e ) {
        module.getRampPhysicalModel().setUserIsAddingEnergy( false );
    }
}
