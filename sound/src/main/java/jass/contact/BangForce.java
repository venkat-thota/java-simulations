package jass.contact;

import jass.generators.OneShotBuffer;

public class BangForce extends OneShotBuffer {
    /**
     * Duration in seconds of impact in buffer.
     */
    private float durBang;

    
    public BangForce( float srate, int bufferSize, String fn ) {
        super( srate, bufferSize, fn );
        durBang = loopBufferLength / srateLoopBuffer;
    }

   
    public BangForce( float srate, int bufferSize, float[] loopBuffer ) {
        super( srate, bufferSize, loopBuffer );
    }

    
    public void bang( float force, float dur ) {
        if( dur < 2 / srate ) {
            dur = 2 / srate;
        }
        setVolume( force );
        setSpeed( durBang / dur );
        hit();
    }

}


