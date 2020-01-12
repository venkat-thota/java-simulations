package jass.patches;

import jass.engine.BufferNotAvailableException;
import jass.engine.InOut;
import jass.engine.SinkIsFullException;
import jass.engine.Source;
import jass.generators.Delay;
import jass.generators.Mixer;


public class AllPass extends InOut {

    protected float srate;
    protected float del; // delay in seonds
    protected float a = 0; // Filter parameter
    protected Mixer mixer;
    protected Delay delay1;
    protected Delay delay2;

    /**
     * Create. For derived classes.
     *
     * @param bufferSize Buffer size used for real-time rendering.
     */
    public AllPass( int bufferSize ) {
        super( bufferSize );
    }

    /**
     * Create. For derived classes.
     *
     * @param bufferSize Buffer size used for real-time rendering.
     * @param srate      sampling rate in Hz
     */
    public AllPass( int bufferSize, float srate ) {
        super( bufferSize );
        this.srate = srate;
        init();
    }

    /**
     * Init and allocate.
     */
    protected void init() {
        mixer = new Mixer( bufferSize, 3 );
        delay1 = new Delay( bufferSize, srate );
        delay2 = new Delay( bufferSize, srate );
        try {
            mixer.addSource( delay1 );
            mixer.setGain( 0, -a );
            mixer.addSource( delay2 );
            mixer.setGain( 1, 1 );
            delay1.addSource( mixer );
        }
        catch( SinkIsFullException e ) {
            System.out.println( this + " " + e );
        }
        long t = getTime();
        mixer.setTime( t );
        delay1.setTime( t );
        delay2.setTime( t );
    }

    /**
     * Add source to Sink. Override to allow one input only and add to mixer with
     * gain coefficient a and to delay 2.
     * This will be called after init() so mixer will already have 2 inputs
     *
     * @param s Source to add.
     * @return object representing Source in Sink (may be null).
     */
    public Object addSource( Source s ) throws SinkIsFullException {
        if( getSources().length > 0 ) {
            throw new SinkIsFullException();
        }
        else {
            mixer.addSource( s );
            mixer.setGain( 2, a );
            delay2.addSource( s );
            // add to the superclass. THis is for administrative reasons only,
            // the source cache is not used here.
            return super.addSource( s );
        }
    }

    /**
     * Set delay del. (=M/srate)
     *
     * @param del delay in seconds
     */
    public void setM( float del ) throws IllegalArgumentException {
        this.del = del;
        delay1.setRecursiveDelay( del );
        delay2.setRawDelay( del );
    }

    /**
     * Set filter parameter a.
     *
     * @param a allpas parameter
     */
    public void setA( float a ) {
        this.a = a;
        mixer.setGain( 0, -a );
        mixer.setGain( 2, a );
    }

    /**
     * Compute the next buffer and store in member float[] buf.
     */
    protected void computeBuffer() {
        try {
            buf = mixer.getBuffer( getTime() );
        }
        catch( BufferNotAvailableException e ) {
            System.out.println( this + " " + e );
        }
    }

}
