

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractMappingStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearMappingStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LogarithmicMappingStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.TestMappingStrategies;


public class TestMappingStrategies {

    /* not intended for instantiation */
    private TestMappingStrategies() {
    }

    /*
    * Test harness.
    */
    public static void main( String[] args ) {
        TestMappingStrategies.runTests();
    }

    /*
    * Tests a strategy for a specified number of equally-spaced intervals.
    */
    private static void testStrategy( AbstractMappingStrategy strategy, int numberOfTests ) {

        int sliderMin = strategy.getSliderMin();
        int sliderMax = strategy.getSliderMax();
        double modelMin = strategy.getModelMin();
        double modelMax = strategy.getModelMax();
        System.out.println( "test: " + strategy.toString() );

        int sliderStep = ( sliderMax - sliderMin ) / ( numberOfTests - 1 );
        double modelStep = ( modelMax - modelMin ) / ( numberOfTests - 1 );

        System.out.println( "LinearStrategy.sliderToModel tests..." );
        for ( int i = 0; i < numberOfTests; i++ ) {
            int sliderValue = sliderMin + ( i * sliderStep );
            double modelValue = modelMin + ( i * modelStep );
            testStrategy( strategy, sliderValue, modelValue );
        }
    }

    /*
    * Tests a strategy for specific slider and model value.
    */
    private static void testStrategy( AbstractMappingStrategy strategy, int sliderValue, double modelValue ) {
        System.out.println( sliderValue + " -> " + strategy.sliderToModel( sliderValue ) + " (" + modelValue + ")" );
        System.out.println( modelValue + " -> " + strategy.modelToSlider( modelValue ) + " (" + sliderValue + ")" );
    }

    /*
    * Run a bunch of tests for each strategty type.
    */
    public static void runTests() {

        // Linear test
        {
            int numberOfTests = 10;
            System.out.println( "------------------------" );
            testStrategy( new LinearMappingStrategy( -1.0, 1.0, -100, 100 ), numberOfTests );
            System.out.println( "------------------------" );
            testStrategy( new LinearMappingStrategy( 1000.0, 2000.0, 0, 1000 ), numberOfTests );
            System.out.println( "------------------------" );
            testStrategy( new LinearMappingStrategy( 0, 1000, -1000, 0 ), numberOfTests );
        }

        // Logarithmic test
        {
            System.out.println( "------------------------" );
            LogarithmicMappingStrategy logarithmicStrategy = new LogarithmicMappingStrategy( 2E1, 2E5, -100, 100 );
            System.out.println( "test: " + logarithmicStrategy.toString() );
            testStrategy( logarithmicStrategy, -100, 2E1 );
            testStrategy( logarithmicStrategy, -50, 2E2 );
            testStrategy( logarithmicStrategy, 0, 2E3 );
            testStrategy( logarithmicStrategy, 50, 2E4 );
            testStrategy( logarithmicStrategy, 100, 2E5 );
        }

        // Logarithmic test
        {
            System.out.println( "------------------------" );
            LogarithmicMappingStrategy logarithmicStrategy = new LogarithmicMappingStrategy( 2E1, 2E5, 0, 1000 );
            System.out.println( "test: " + logarithmicStrategy.toString() );
            testStrategy( logarithmicStrategy, 0, 2E1 );
            testStrategy( logarithmicStrategy, 250, 2E2 );
            testStrategy( logarithmicStrategy, 500, 2E3 );
            testStrategy( logarithmicStrategy, 750, 2E4 );
            testStrategy( logarithmicStrategy, 1000, 2E5 );
        }

        // Logarithmic test
        {
            System.out.println( "------------------------" );
            LogarithmicMappingStrategy logarithmicStrategy = new LogarithmicMappingStrategy( -2E5, -2E1, 0, 1000 );
            System.out.println( "test: " + logarithmicStrategy.toString() );
            testStrategy( logarithmicStrategy, 0, -2E5 );
            testStrategy( logarithmicStrategy, 250, -2E4 );
            testStrategy( logarithmicStrategy, 500, -2E3 );
            testStrategy( logarithmicStrategy, 750, -2E2 );
            testStrategy( logarithmicStrategy, 1000, -2E1 );
        }

        // Logarithmic test
        {
            System.out.println( "------------------------" );
            LogarithmicMappingStrategy logarithmicStrategy = new LogarithmicMappingStrategy( 1E-6, 1E-2, 0, 1000 );
            System.out.println( "test: " + logarithmicStrategy.toString() );
            testStrategy( logarithmicStrategy, 0, 1E-6 );
            testStrategy( logarithmicStrategy, 250, 1E-5 );
            testStrategy( logarithmicStrategy, 500, 1E-4 );
            testStrategy( logarithmicStrategy, 750, 1E-3 );
            testStrategy( logarithmicStrategy, 1000, 1E-2 );
        }
    }
}
