
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;

import com.aimxcel.abclearn.core.aimxcelcore.test.CoreTestFrame;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class HealthIndicator extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndicatorHealthBar heartStrengthIndicator;
    private IndicatorHealthBar heartStrainIndicator;
    private Human human;
    private static final int INDICATOR_BAR_HEIGHT = 150;

    public HealthIndicator( final Human human ) {
        this.human = human;
        heartStrengthIndicator = new HeartStrengthIndicatorBar( human );
        heartStrainIndicator = new HeartStrainIndicatorBar( human );

        addChild( heartStrengthIndicator );
        addChild( heartStrainIndicator );

        updateLayout();
    }

    private void updateLayout() {
        double inset = 4;
        heartStrainIndicator.setOffset( heartStrengthIndicator.getFullBounds().getMaxX() + inset, 0 );
    }

    private static class HeartStrengthIndicatorBar extends IndicatorHealthBar {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Human human;

        public HeartStrengthIndicatorBar( final Human human ) {
            super( "<html>" + EatingAndExerciseResources.getString( "heart.strength" ) + "</html>", 0, 1, 250 / 1000.0, 1000 / 1000.0, INDICATOR_BAR_HEIGHT, Color.red, Color.green );
            this.human = human;
            human.addListener( new Human.Adapter() {
                public void heartStrengthChanged() {
                    updateValue();
                }
            } );
            updateValue();
        }

        private void updateValue() {
            setValue( human.getHeartStrength() );
        }
    }

    private static class HeartStrainIndicatorBar extends IndicatorHealthBar {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Human human;

        public HeartStrainIndicatorBar( Human human ) {
            super( "<html>" + EatingAndExerciseResources.getString( "heart.strain" ) + "</html>", 0, 1, 16 / 100.0, 31 / 100.0, INDICATOR_BAR_HEIGHT, Color.green, Color.red );
            this.human = human;
            human.addListener( new Human.Adapter() {
                public void heartStrainChanged() {
                    updateValue();
                }
            } );
            updateValue();
        }

        private void updateValue() {
            setValue( human.getHeartStrain() );
        }
    }

    public static void main( String[] args ) {
        CoreTestFrame piccoloTestFrame = new CoreTestFrame( HealthIndicator.class.getName() );
        HealthIndicator indicator = new HealthIndicator( new Human() );
        indicator.setOffset( 200, 200 );
        piccoloTestFrame.addNode( indicator );
        piccoloTestFrame.setVisible( true );
    }
}
