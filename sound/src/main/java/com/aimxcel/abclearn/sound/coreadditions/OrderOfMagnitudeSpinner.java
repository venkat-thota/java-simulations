package com.aimxcel.abclearn.sound.coreadditions;

import javax.swing.*;
public class OrderOfMagnitudeSpinner extends JSpinner {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderOfMagnitudeSpinner( float minFactor, float maxFactor ) {
        super();
        final SpinnerModel model = new OrderOfMagnitudeListModel( minFactor, maxFactor );
        setModel( model );
    }

    //
    // Inner classes
    //

    /**
     * The model class for the OrderOfMagnitudeSpinner
     */
    private class OrderOfMagnitudeListModel extends SpinnerNumberModel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private float maxFactor;
        private float minFactor;

        OrderOfMagnitudeListModel( float minFactor, float maxFactor ) {
            super( 1.0, minFactor, maxFactor, 1 );
            this.maxFactor = maxFactor;
            this.minFactor = minFactor;
        }

        public Object getNextValue() {
            Double currValue = (Double)getValue();
            if( currValue.floatValue() >= maxFactor ) {
                return currValue;
            }
            else {
                return new Double( ( (Double)getValue() ).floatValue() * 10 );
            }
        }

        public Object getPreviousValue() {
            Double currValue = (Double)getValue();
            if( currValue.floatValue() <= minFactor ) {
                return currValue;
            }
            else {
                return new Double( ( (Double)getValue() ).floatValue() / 10 );
            }
        }
    }
}
