package jass.contact;

import jass.engine.Out;

public class StickSlip0 extends Out {

    private float srate; // sampling rate in Hertz
    private float k_damp; // damping of model/srate
    private float mu_static; // static friction coeff.
    private float mu_dynamic; // dynamic friction coeff.
    private float v_crit; // critical velocity where static friction kicks in/srate
    private float qt_1, qt_2; // last state
    private float v = 1; // external slidevelocity in m/s / srate
    private float fn = 1; // external; normal force/srate

    
    public StickSlip0( float srate, int bufferSize ) {
        super( bufferSize );
        this.srate = srate;
        reset();
    }

    
    public void setStickSlipParameters( float k_damp, float dummy, float mu_static, float mu_dynamic, float v_crit ) {
        this.k_damp = k_damp / srate;
        this.mu_static = mu_static;
        this.mu_dynamic = mu_dynamic;
        this.v_crit = v_crit / srate;
    }

   
    public void setNormalForce( float val ) {
        fn = val / srate;
    }

    
    public void setSpeed( float v ) {
        this.v = v / srate;
    }

    /**
     * Reset state.
     */
    public void reset() {
        qt_1 = qt_2 = 0;
    }

   
    public void computeBuffer() {
        float factor = ( 1 - k_damp );
        int bufsz = getBufferSize();
        for( int k = 0; k < bufsz; k++ ) {
            float f_mu_sign;
            float tmp = v - qt_1 + qt_2;
            int signOf = ( tmp >= 0 ? 1 : -1 );
            if( (float)( Math.abs( tmp ) ) > v_crit ) {
                f_mu_sign = fn * mu_dynamic * signOf;
            }
            else {
                f_mu_sign = fn * mu_static * signOf;
            }
            buf[k] = factor * qt_1 + f_mu_sign;
            qt_2 = qt_1;
            qt_1 = buf[k];
        }
    }

}


