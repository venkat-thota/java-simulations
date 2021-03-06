package com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d;


public class SystemRunner {
    System2D system;
    double dt;

    public SystemRunner( System2D system, double dt ) {
        this.system = system;
        this.dt = dt;
    }

    public void step() {
        system.iterate( dt );
    }

}



