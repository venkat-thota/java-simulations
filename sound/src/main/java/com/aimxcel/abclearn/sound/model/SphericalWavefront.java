package com.aimxcel.abclearn.sound.model;

public class SphericalWavefront implements WavefrontType {

    public double computeAmplitudeAtDistance( Wavefront wavefront,
                                              double amplitude,
                                              double distance ) {
        double[] amplitudes = wavefront.getAmplitude();
        double factor = 1.0f - (double)( 0.05 * distance / amplitudes.length );
        //        double factor = 1.0 - ( (double)wavefront.getPropagationSpeed()) / ( amplitudes.length - (int)distance );

        if( factor < 0 ) {
            System.out.println( "***" );
        }
        return amplitude * factor;
    }
}
