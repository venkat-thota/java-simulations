package com.aimxcel.abclearn.batteryresistorcircuit.collisions;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d.Core;

public interface CollisionEvent {
    public void collide( Core c, Electron e );
}
