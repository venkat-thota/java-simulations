package javasound;

import javax.sound.sampled.*;
import java.io.IOException;


public class OscillatorPlayer {
    private static final int BUFFER_SIZE = 128000;
    private static boolean DEBUG = false;

   
    private static int getWaveformType( String strWaveformType ) {
        int nWaveformType = Oscillator.WAVEFORM_SINE;
        strWaveformType = strWaveformType.trim().toLowerCase();
        if( strWaveformType.equals( "sine" ) ) {
            nWaveformType = Oscillator.WAVEFORM_SINE;
        }
        else if( strWaveformType.equals( "square" ) ) {
            nWaveformType = Oscillator.WAVEFORM_SQUARE;
        }
        else if( strWaveformType.equals( "triangle" ) ) {
            nWaveformType = Oscillator.WAVEFORM_TRIANGLE;
        }
        else if( strWaveformType.equals( "sawtooth" ) ) {
            nWaveformType = Oscillator.WAVEFORM_SAWTOOTH;
        }
        return nWaveformType;
    }


    private static void printUsageAndExit() {
        out( "OscillatorPlayer: usage:" );
        out( "\tjava OscillatorPlayer [-t <waveformtype>] [-f <signalfrequency>] [-r <samplerate>]" );
        System.exit( 1 );
    }


    private static void out( String strMessage ) {
        System.out.println( strMessage );
    }


    public static void play( float frequency, float amplitude, float samplerate )
            throws IOException {
        byte[] abData;
        AudioFormat audioFormat;
        int nWaveformType = Oscillator.WAVEFORM_SINE;
        float fSampleRate = 44100.0F;
        float fSignalFrequency = 1000.0F;
        float fAmplitude = 0.7F;

        fSampleRate = (float)samplerate;
        fSignalFrequency = (float)frequency;
        fAmplitude = (float)amplitude;

        audioFormat = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED,
                                       fSampleRate, 16, 2, 4, fSampleRate, false );
        AudioInputStream oscillator = new Oscillator( nWaveformType,
                                                      fSignalFrequency,
                                                      fAmplitude,
                                                      audioFormat,
                                                      AudioSystem.NOT_SPECIFIED );
        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info( SourceDataLine.class,
                                                audioFormat );
        try {
            line = (SourceDataLine)AudioSystem.getLine( info );
            line.open( audioFormat );
        }
        catch( LineUnavailableException e ) {
            e.printStackTrace();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        line.start();


        abData = new byte[BUFFER_SIZE];
        while( true ) {
            if( DEBUG ) {
                out( "OscillatorPlayer.main(): trying to read (bytes): " + abData.length );
            }
            int nRead = oscillator.read( abData );
            if( DEBUG ) {
                out( "OscillatorPlayer.main(): in loop, read (bytes): " + nRead );
            }
            int nWritten = line.write( abData, 0, nRead );
            if( DEBUG ) {
                out( "OscillatorPlayer.main(): written: " + nWritten );
            }
        }
    }

}

/*** OscillatorPlayer.java ***/
