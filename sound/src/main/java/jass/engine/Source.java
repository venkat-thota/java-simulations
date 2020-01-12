package jass.engine;



public interface Source {
    
    float[] getBuffer( long t ) throws BufferNotAvailableException;

    /**
     * Get current time.
     *
     * @return current time.
     */
    long getTime();

    /**
     * Set current time.
     *
     * @param t current time.
     */
    void setTime( long t );

    /**
     * Get buffer size.
     *
     * @return buffer size in samples.
     */
    int getBufferSize();

    /**
     * Set buffer size.
     *
     * @param bufferSize buffer size.
     */
    void setBufferSize( int bufferSize );

    /**
     * Clears buffer to zero.
     */
    void clearBuffer();
}

