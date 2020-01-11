
package edu.colorado.phet.theramp.view;

import java.awt.*;


public class RampLookAndFeel {

    
    private final Color myGreen = new Color( 0.0f, 0.8f, 0.1f );
    private final Color lightBlue = new Color( 160, 220, 255 );
        private final Color drabYellow = new Color( 190, 190, 0 );


    private static final Color MY_ORANGE = new Color( 236, 153, 55 );
    private Color appliedForceColor = MY_ORANGE;
        private Color netForceColor = myGreen;
    private Color frictionForceColor = Color.red;
        private Color weightColor = new Color( 50, 130, 215 );
    private Color normalColor = Color.magenta;
            private Color wallForceColor = drabYellow;

    private Color accelColor = Color.black;
    private Color velColor = Color.black;
    private Color positionColor = Color.black;

    private Color appliedWorkColor = appliedForceColor;
    private Color frictionWorkColor = frictionForceColor;
    private Color gravityWorkColor = weightColor;
    private Color totalWorkColor = myGreen;



    private Color totalEnergyColor = appliedWorkColor;
    private Color kineticEnergyColor = totalWorkColor;
    private Color potentialEnergyColor = gravityWorkColor;
    private Color thermalEnergyColor = frictionWorkColor;

    public Color getAppliedForceColor() {
        return appliedForceColor;
    }

    public Color getNetForceColor() {
        return netForceColor;
    }

    public Color getFrictionForceColor() {
        return frictionForceColor;
    }

    public Color getWeightColor() {
        return weightColor;
    }

    public Color getNormalColor() {
        return normalColor;
    }

    public Color getWallForceColor() {
        return wallForceColor;
    }

    public Color getTotalEnergyColor() {
        return totalEnergyColor;
    }

    public Color getKineticEnergyColor() {
        return kineticEnergyColor;
    }

    public Color getPotentialEnergyColor() {
        return potentialEnergyColor;
    }

    public Color getThermalEnergyColor() {
        return thermalEnergyColor;
    }

    public Color getAppliedWorkColor() {
        return appliedWorkColor;
    }

    public Color getFrictionWorkColor() {
        return frictionWorkColor;
    }

    public Color getGravityWorkColor() {
        return gravityWorkColor;
    }

    public Color getTotalWorkColor() {
        return totalWorkColor;
    }
}
