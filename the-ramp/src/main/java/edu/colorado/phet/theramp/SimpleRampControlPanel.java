
package edu.colorado.phet.theramp;




public class SimpleRampControlPanel extends RampControlPanel {


    public SimpleRampControlPanel( SimpleRampModule simpleRampModule ) {
        super( simpleRampModule );


        addControlFullWidth( new ObjectSelectionPanel( simpleRampModule, simpleRampModule.getRampObjects() ) );
        addControl( getFrictionlessCheckBox() );
        super.addPositionAngleControls();
        finishInit();
    }
}
