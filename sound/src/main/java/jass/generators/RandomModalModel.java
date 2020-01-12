package jass.generators;


public class RandomModalModel extends ModalModel {

    
    public RandomModalModel( int nf, int np, float fmin, float fmax, float mmcmin, float mmcmax, float amin, float amax ) {
        super( nf, np );
        randomize( fmin, fmax, mmcmin, mmcmax, amin, amax );
    }

    private void randomize( float fmin, float fmax, float mmcmin, float mmcmax, float amin, float amax ) {
        float x = (float)Math.random();
        float mmc = (float)( mmcmin + x * ( mmcmax - mmcmin ) );
        for( int i = 0; i < nf; i++ ) {
            x = (float)Math.random();
            f[i] = fmin + x * ( fmax - fmin );
            d[i] = (float)( mmc * f[i] );
            for( int k = 0; k < np; k++ ) {
                float y = (float)Math.random();
                a[k][i] = amin + y * ( amax - amin );
            }
        }
    }

}


