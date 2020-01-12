package com.aimxcel.abclearn.sound.model;

public class SineWaveFunction implements WaveFunction {

    private Wavefront wavefront;

    public SineWaveFunction( Wavefront wavefront ) {
        this.wavefront = wavefront;
    }
    public double waveAmplitude( double time ) {
        double amplitude = 0.0;
        if( wavefront.getFrequency() != 0 ) {
            amplitude = Math.sin( wavefront.getFrequency() * time ) * wavefront.getMaxAmplitude();
        }
        else {
            amplitude = 0;
        }
        return amplitude;
    }
}
