package javasound;

import javax.sound.sampled.*;
import java.io.IOException;


public class MyOscillatorPlayer extends Thread {
    private static final int BUFFER_SIZE = 32000;
    private static boolean DEBUG = false;
    private Oscillator oscillator;
    private float fAmplitude;
    private float fSignalFrequency;
    private float fSampleRate;
    private SourceDataLine line;
    private Object lineMonitor = new Object();
    private AudioFormat audioFormat;

    private boolean enabled = false;
    private boolean active;

    public MyOscillatorPlayer() {

        fSampleRate = 44100.0F;
        //        fSampleRate = 22050.0F;
        fSignalFrequency = 1.0F; // Oscillator class doesn't like a frequency of 0;
        fAmplitude = 0.0F;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    /**
     * Runs the oscillator player
     */
    public void run() {

        if( true ) {
            return;
        }
        setActive( true );

        // TODO: make the priority setable from the outside
        this.setPriority( Thread.NORM_PRIORITY );
        //        this.setPriority( Thread.MAX_PRIORITY );
        try {
            setup( (float)fSignalFrequency, (float)fAmplitude, (float)fSampleRate );
            byte[] abData;
            while( active ) {
                //            while( true ) {

                if( enabled ) {
                    synchronized( lineMonitor ) {
                        if( line != null ) {

                            // Note: we must allocate a new buffer every time. I tried reusing the buffer and
                            // had all sorts of problems
                            abData = new byte[BUFFER_SIZE];
                            if( DEBUG ) {
                                out( "OscillatorPlayer.main(): trying to read (bytes): " + abData.length );
                            }

                            // Here is where we get the bytes that are to be played. It looks like we get somewhere
                            // between 1/2 and 1 sec of sound at a time.
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

                try {
                    Thread.sleep( /* 50 */ 100 );
                }
                catch( InterruptedException e ) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }

        }
        catch( IOException e ) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private static void out( String strMessage ) {
        System.out.println( strMessage );
    }

    public synchronized float getFrequency() {
        return fSignalFrequency;
    }

    public synchronized void setFrequency( float frequency ) {

        // Oscillator chokes if given a frequency of 0
        frequency = Math.max( frequency, 1 );

        fSignalFrequency = (float)frequency;
        try {
            this.setup( (float)fSignalFrequency, (float)fAmplitude, (float)fSampleRate );
        }
        catch( IOException e ) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    public synchronized float getAmplitude() {
        return fAmplitude;
    }

    public synchronized void setAmplitude( float amplitude ) {

        // Oscillator clips if you give it an amplitude of 1
        amplitude = (float)Math.min( amplitude, 0.95 );
        fAmplitude = (float)amplitude;
        try {
            this.setup( (float)fSignalFrequency, (float)fAmplitude, (float)fSampleRate );
        }
        catch( IOException e ) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    /**
     * Creates an oscillator and sets it up for frequency and amplitude.
     *
     * @param frequency
     * @param amplitude
     * @param samplerate
     * @throws IOException
     */
    public synchronized void setup( float frequency, float amplitude, float samplerate )
            throws IOException {

        audioFormat = null;
        int nWaveformType = Oscillator.WAVEFORM_SINE;

        if( audioFormat == null ) {
            try {
                audioFormat = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED,
                                               fSampleRate, 16, 2, 4, fSampleRate, false );
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }

        if( true ) {
            //        if( oscillator == null ) {
            oscillator = new Oscillator( nWaveformType,
                                         fSignalFrequency,
                                         fAmplitude,
                                         audioFormat,
                                         AudioSystem.NOT_SPECIFIED );
        }
        else {
            oscillator.setParams( nWaveformType,
                                  fSignalFrequency,
                                  fAmplitude,
                                  audioFormat,
                                  AudioSystem.NOT_SPECIFIED );
        }

        synchronized( lineMonitor ) {
            if( line != null ) {
                line.stop();
            }

            line = null;
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
        }
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}