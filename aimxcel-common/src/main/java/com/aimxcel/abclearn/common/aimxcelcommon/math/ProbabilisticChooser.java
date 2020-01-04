
package com.aimxcel.abclearn.common.aimxcelcommon.math;

import java.util.Random;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ProbabilisticChooser;


public class ProbabilisticChooser {

    private static final Random RANDOM = new Random();

    private Entry[] _entries;

    /**
     * Takes an array of ProbabilisticChooser.Entry instances. Each contains an object
     * and a number that defines the likelihood that it will be selected.
     * <p/>
     * The order of the entries can be in the array is immaterial.
     *
     * @param entries An array of ProbabilisticChooser.Entry objects
     */
    public ProbabilisticChooser( ProbabilisticChooser.Entry[] entries ) {
        _entries = new Entry[entries.length];

        // Get the normalization factor for the probabilities
        double pTotal = 0;
        for ( int i = 0; i < entries.length; i++ ) {
            pTotal += entries[i].getWeight();
        }
        double fNorm = 1 / pTotal;

        // Build the internal list that is used for choosing. Each choose-able object
        // is put in an array, with an associated probability that is the sum of
        // its own probability plus the cummulative probability of all objects before
        // it in the list. This makes the choosing process work properly in get( double p );
        double p = 0;
        for ( int i = 0; i < entries.length; i++ ) {
            p += entries[i].getWeight() * fNorm;
            _entries[i] = new Entry( entries[i].getObject(), p );
        }
    }

    /**
     * Choose an object from the entries
     *
     * @return An object from the entries
     */
    public Object get() {
        return get( RANDOM.nextDouble() );
    }

    /**
     * Get an object from the collection of entries, given a specified selector
     * probability. This method is provided mostly for testing
     *
     * @param p
     * @return An object from the entries
     */
    public Object get( double p ) {
        Object result = null;
        for ( int i = 0; i < _entries.length && result == null; i++ ) {
            Entry entry = _entries[i];
            if ( p <= entry.getWeight() ) {
                result = entry.getObject();
            }
        }
        return result;
    }

    /**
     * Gets the internal entries, which are normalized version of
     * the entries that were provided in the constructor.
     * This method is useful primarily for testing and debugging.
     *
     * @return Entry[]
     */
    public Entry[] getEntries() {
        return _entries;
    }

    /**
     * An entry for a ProbabilisticChooser
     */
    public static class Entry {
        private Object object;
        private double weight;

        /**
         * @param object An object to be put in the chooser
         * @param weight The likelihood that the object should be chosen, or count of the object
         *               instances in the total population
         */
        public Entry( Object object, double weight ) {
            this.object = object;
            this.weight = weight;
        }

        public Object getObject() {
            return object;
        }

        public double getWeight() {
            return weight;
        }
    }
}
