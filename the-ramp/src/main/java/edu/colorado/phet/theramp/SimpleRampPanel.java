
package edu.colorado.phet.theramp;

import edu.colorado.phet.theramp.view.RampPanel;



public class SimpleRampPanel extends RampPanel {
    private SimpleRampModule simpleRampModule;

    public SimpleRampPanel( SimpleRampModule simpleRampModule ) {
        super( simpleRampModule );
        this.simpleRampModule = simpleRampModule;

        getRampPlotSet().minimizeAllPlots();
        setAllBarsMinimized( true );
        addWiggleMe();
        super.maximizeForcePlot();
    }

    public void resetBarStates() {
        setAllBarsMinimized( true );
    }

    protected void resetPlotStates() {
        getRampPlotSet().setPlotsMaximized( true, false, false );
    }
}
