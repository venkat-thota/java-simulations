package jass.engine;




public interface Sink {
    /**
     * Add a Source
     *
     * @param s Source to add
     * @return object representing source in Sink
     */
    Object addSource( Source s ) throws SinkIsFullException;

    /**
     * Remove a Source
     *
     * @param s Source to remove
     */
    void removeSource( Source s );

    /**
     * Get array of sources.
     *
     * @return array of the Sources, null if there are none.
     */
    Object[] getSources();
}
