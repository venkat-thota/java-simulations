
package com.aimxcel.abclearn.theramp.model;


public class Collision {
    private Block copy;
    private Block block;
    private RampPhysicalModel rampPhysicalModel;
    private double dt;

    public Collision( Block copy, Block block, RampPhysicalModel rampPhysicalModel, double dt ) {
        this.copy = copy;
        this.block = block;
        this.rampPhysicalModel = rampPhysicalModel;
        this.dt = dt;
    }

    public Block getCopy() {
        return copy;
    }

    public Block getBlock() {
        return block;
    }

    public RampPhysicalModel getRampPhysicalModel() {
        return rampPhysicalModel;
    }

    public double getAbsoluteMomentumChange() {
        return Math.abs( getMomentumChange() );
    }

    public double getMomentumChange() {
        return block.getMomentum() - copy.getMomentum();
    }

    public double getDt() {
        return dt;
    }
}
