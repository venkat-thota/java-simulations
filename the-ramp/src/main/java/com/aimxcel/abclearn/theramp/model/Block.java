
package com.aimxcel.abclearn.theramp.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Block {
    private Surface surface;

    private double mass = 5;//kg
    private double positionInSurface = 10.0;
    private double velocity = 0.0;//m/s
    private double acceleration = 0.0;
    private double kineticFriction = 0.50;
    private double staticFriction = 0.80;
    private ArrayList listeners = new ArrayList();
    private boolean justCollided = false;

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public Block copyState( RampPhysicalModel original, RampPhysicalModel newPhysicalModel ) {
        Block dataBlock = new Block( surface instanceof Ramp ? newPhysicalModel.getRamp() : newPhysicalModel.getGround() );

        dataBlock.mass = mass;
        dataBlock.positionInSurface = positionInSurface;
        dataBlock.velocity = velocity;
        dataBlock.acceleration = acceleration;
        dataBlock.kineticFriction = kineticFriction;
        dataBlock.staticFriction = staticFriction;
        dataBlock.testRampOnly();
        return dataBlock;
    }

    private void testRampOnly() {
        return;

    }

    public void setState( Block state ) {
        setSurface( state.getSurface() );
        setMass( state.mass );
        setPositionInSurface( state.positionInSurface );
        setVelocity( state.velocity );
        setAcceleration( state.acceleration );
        setKineticFriction( state.kineticFriction );
        setStaticFriction( state.staticFriction );
        testRampOnly();
    }

    public void setSurface( Surface surface ) {
        if ( this.surface != surface ) {
            this.surface = surface;
            notifySurfaceChanged();
        }
        testRampOnly();
    }

    private void notifySurfaceChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.surfaceChanged();
        }
    }

    public Surface getSurface() {
        return surface;
    }

    public double getMomentum() {
        return mass * velocity;
    }

    public static interface Listener {
        void positionChanged();

        void staticFrictionChanged();

        void kineticFrictionChanged();

        void massChanged();

        void surfaceChanged();

        void collisionOccurred( Collision collision );

        void velocityChanged();
    }

    public static class Adapter implements Listener {

        public void positionChanged() {
        }

        public void staticFrictionChanged() {
        }

        public void kineticFrictionChanged() {
        }

        public void massChanged() {
        }

        public void surfaceChanged() {
        }

        public void collisionOccurred( Collision collision ) {
        }

        public void velocityChanged() {
        }
    }

    public Block( Surface ramp ) {
        this.surface = ramp;
        testRampOnly();
    }

    public double getPosition() {
        return getPositionInSurface() + getSurface().getDistanceOffset();
    }

    public double getPositionInSurface() {
        return positionInSurface;
    }

    public Point2D getLocation2D() {
        return surface.getLocation( positionInSurface );
    }

    public void setPositionInSurface( double positionInSurface ) {
        this.positionInSurface = positionInSurface;
        notifyPositionChanged();
    }

    private void notifyPositionChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.positionChanged();
        }
    }

    public double getMass() {
        return mass;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setAcceleration( double acceleration ) {
        this.acceleration = acceleration;
    }

    public void stepInTime( RampPhysicalModel rampPhysicalModel, double dt ) {
        Block copy = copyState( rampPhysicalModel, rampPhysicalModel );
        double origPosition = positionInSurface;
        double origVelocity = velocity;
        double accValue = acceleration;
        if ( Math.abs( accValue ) < 0.0000001 ) {
            accValue = 0.0;
        }
        velocity += accValue * dt;
        boolean changedVelSign = changedSign( origVelocity, velocity );
        if ( changedVelSign ) {
            velocity = 0;
        }
        positionInSurface += velocity * dt;


        applyBoundaryConditions( copy, rampPhysicalModel, dt );

        if ( positionInSurface != origPosition ) {
            notifyPositionChanged();
        }
        if ( velocity != origVelocity ) {
            notifyVelocityChanged();
        }

    }

    private void notifyVelocityChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.velocityChanged();
        }
    }

    private void applyBoundaryConditions( Block copy, RampPhysicalModel rampPhysicalModel, double dt ) {
        this.justCollided = false;
        boolean collided = surface.applyBoundaryConditions( rampPhysicalModel, this );
        if ( collided ) {
            Collision collision = new Collision( copy, this, rampPhysicalModel, dt );
            notifyCollision( collision );
            justCollided = true;
        }
    }


    public boolean isJustCollided() {
        return justCollided;
    }

    private void notifyCollision( Collision collision ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.collisionOccurred( collision );
        }
    }

    public double getKineticEnergy() {
        return 0.5 * getMass() * getVelocity() * getVelocity();
    }

    public void setVelocity( double v ) {
        this.velocity = v;
    }

    public void setMass( double mass ) {
        this.mass = mass;
        notifyMassChanged();
    }

    private void notifyMassChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.massChanged();
        }
    }

    public void setStaticFriction( double staticFriction ) {
        if ( this.staticFriction != staticFriction ) {
            this.staticFriction = staticFriction;
            notifyStaticFrictionChanged();
        }
    }

    private void notifyStaticFrictionChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.staticFrictionChanged();
        }
    }

    public void setKineticFriction( double kineticFriction ) {
        if ( this.kineticFriction != kineticFriction ) {
            this.kineticFriction = kineticFriction;
            notifyKineticFrictionChanged();
        }
    }

    private void notifyKineticFrictionChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.kineticFrictionChanged();

        }
    }

    static class Sign {
        static final Sign POSITIVE = new Sign( "+" );
        static final Sign NEGATIVE = new Sign( "-" );
        static final Sign ZERO = new Sign( "0" );
        private String s;

        public static Sign toSign( double value ) {
            if ( value > 0 ) {
                return POSITIVE;
            }
            else if ( value < 0 ) {
                return NEGATIVE;
            }
            else {
                return ZERO;
            }
        }

        public Sign( String s ) {
            this.s = s;
        }

        public boolean equals( Object obj ) {
            return obj instanceof Sign && ( (Sign) obj ).s.equals( s );
        }
    }

    private boolean changedSign( double origVelocity, double velocity ) {
        Sign origSign = Sign.toSign( origVelocity );
        Sign newSign = Sign.toSign( velocity );
        boolean leftChange = origSign.equals( Sign.POSITIVE ) && newSign.equals( Sign.NEGATIVE );
        boolean rightChange = origSign.equals( Sign.NEGATIVE ) && newSign.equals( Sign.POSITIVE );
        boolean changed = leftChange || rightChange;
        return changed;
    }

    public double getFrictionForce( double gravity, double otherForces ) {

        double N = this.getMass() * gravity * Math.cos( surface.getAngle() );
        if ( this.isMoving() ) {
            double sign = this.getVelocity() >= 0 ? -1 : 1;
            double kineticFrictionForce = sign * this.getKineticFriction() * N;
            return kineticFrictionForce;
        }
        else {
            double u = Math.max( this.getKineticFriction(), this.getStaticFriction() );
            double maxStaticFrictionForce = u * N;
            if ( Math.abs( maxStaticFrictionForce ) > Math.abs( otherForces ) ) {
                
                return -otherForces;
            }
            else { 
                double sign = otherForces >= 0 ? -1 : 1;
                double frictionForce = sign * u * N;
                return frictionForce; 
            }
        }
    }

    public double getStaticFriction() {
        return staticFriction;
    }

    public double getKineticFriction() {
        return kineticFriction;
    }

    public boolean isFrictionless() {
        return staticFriction == 0 && kineticFriction == 0;
    }

    private boolean isMoving() {
        return velocity != 0.0;
    }

}
