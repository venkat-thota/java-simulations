package jass.generators;
public interface Filter {
    public void filter( float[] output, float[] input, int nsamples, int inputOffset );
}
