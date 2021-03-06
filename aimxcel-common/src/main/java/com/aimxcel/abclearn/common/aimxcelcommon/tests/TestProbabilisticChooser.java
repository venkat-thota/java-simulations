
 
package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ProbabilisticChooser;


public class TestProbabilisticChooser {

    private static final int NUMBER_OF_ENTRIES = 11;
    private static final int NUMBER_OF_TESTS = 1000000;

    public static final void main( String[] args ) {

        System.out.println( "ProbabilisticChooser test harness" );

        // Make a set of entries that are Integers, each with a weight equal to 10x the Integer's value.
        System.out.println();
        System.out.println( "Entries provided to chooser:" );
        ProbabilisticChooser.Entry[] entries = new ProbabilisticChooser.Entry[NUMBER_OF_ENTRIES];
        int[] counts = new int[entries.length];
        double weightSum = 0;
        for ( int i = 0; i < entries.length; i++ ) {
            Integer object = new Integer( i );
            double weight = 10 * i;
            entries[i] = new ProbabilisticChooser.Entry( object, weight );
            weightSum += weight;
            System.out.println( "object=" + object.intValue() + " weight=" + weight );
        }

        // Create the chooser
        ProbabilisticChooser chooser = new ProbabilisticChooser( entries );

        // Examine its entries
        System.out.println();
        System.out.println( "Entries normalized by chooser:" );
        ProbabilisticChooser.Entry[] normalizedEntries = chooser.getEntries();
        for ( int i = 0; i < normalizedEntries.length; i++ ) {
            Integer object = (Integer) normalizedEntries[i].getObject();
            double weight = normalizedEntries[i].getWeight();
            System.out.println( "object=" + object.intValue() + " weight=" + weight );
        }

        // Get objects from the chooser and keep track of how many times each object is retrieved.
        System.out.println();
        System.out.println( "performing " + NUMBER_OF_TESTS + " test..." );
        for ( int i = 0; i < 1000000; i++ ) {
            Object object = chooser.get();
            int countIndex = ( (Integer) object ).intValue();
            counts[countIndex]++;
        }

        // Report the results.
        int countSum = 0;
        double probabilitySum = 0;
        for ( int i = 0; i < counts.length; i++ ) {
            Integer object = (Integer) entries[i].getObject();
            double weight = entries[i].getWeight();
            double probability = weight / weightSum;
            System.out.println( "object=" + object.intValue() + " weight=" + weight + " probability=" + probability + " count=" + counts[i] );
            countSum += counts[i];
            probabilitySum += probability;
        }
        System.out.println( "probabilitySum=" + probabilitySum + " (should equal 1.0)" );
        System.out.println( "countSum=" + countSum + " (should equal " + NUMBER_OF_TESTS + ")" );
    }
}
