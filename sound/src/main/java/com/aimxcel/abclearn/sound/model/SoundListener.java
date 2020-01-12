package com.aimxcel.abclearn.sound.model;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

/**
 * This is a placeholder class in the model for the person listening to the sound
 */
public class SoundListener extends SimpleObservable implements ModelElement {
    private Point2D.Double location = new Point2D.Double();
    private Point2D.Double origin;
    private SoundModel model;
    private double frequencyHeard;
    private double amplitudeHeard;
    private double octaveAmplitudeHeard;

    public SoundListener( SoundModel model, Point2D.Double soundOrigin ) {
        this.model = model;
        model.addModelElement( this );
        this.origin = soundOrigin;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public void setLocation( Point2D.Double location ) {
        this.location.setLocation( location );
        notifyObservers();
    }

    public void stepInTime( double dt ) {
        int distFromSource = (int)this.location.distance( origin );
        double currentFrequency = model.getPrimaryWavefront().getFrequencyAtTime( distFromSource );
        double currentAmplitude = model.getPrimaryWavefront().getMaxAmplitudeAtTime( distFromSource );
        double currentOctaveAmplitude = model.getOctaveWavefront().getMaxAmplitudeAtTime( distFromSource );
        boolean notifyFlag = false;

        if( currentFrequency != frequencyHeard
            || currentAmplitude != amplitudeHeard
            || currentOctaveAmplitude != octaveAmplitudeHeard ) {
            notifyFlag = true;
        }
        frequencyHeard = currentFrequency;
        amplitudeHeard = currentAmplitude;
        octaveAmplitudeHeard = currentOctaveAmplitude;
        if( notifyFlag ) {
            notifyObservers();
        }
    }

    public double getFrequencyHeard() {
        return frequencyHeard;
    }

    public double getAmplitudeHeard() {
        return amplitudeHeard;
    }
}
