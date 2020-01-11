
package edu.colorado.phet.theramp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.ModelSlider;
import edu.colorado.phet.theramp.model.Block;
import edu.colorado.phet.theramp.model.Ramp;



public class PositionController {
    private RampModule rampModule;
    private ModelSlider modelSlider;

    public PositionController( final RampModule rampModule ) {
        this.rampModule = rampModule;
        this.modelSlider = new ModelSlider( TheRampStrings.getString( "property.position" ), TheRampStrings.getString( "units.abbr.meters" ), -getGroundLength(), rampModule.getRampPhysicalModel().getRamp().getLength(), getBlockPosition() );
        rampModule.getRampPhysicalModel().getBlock().addListener( new Block.Adapter() {
            public void positionChanged() {
                modelSlider.setValue( getBlockPosition() );
            }
        } );
        modelSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                setBlockPosition();
            }
        } );
        modelSlider.setModelTicks( new double[] { -getGroundLength(), 0, rampModule.getRampPhysicalModel().getRamp().getLength() } );
    }

    private double getGroundLength() {
        return rampModule.getRampPhysicalModel().getGround().getLength();
    }

    private void setBlockPosition() {
        if ( modelSlider.getValue() >= 0 ) {
            rampModule.getBlock().setSurface( rampModule.getRampPhysicalModel().getRamp() );
            rampModule.getBlock().setPositionInSurface( modelSlider.getValue() );
        }
        else {
            double distAlongSurface = getGroundLength() + modelSlider.getValue();

            rampModule.getBlock().setSurface( rampModule.getRampPhysicalModel().getGround() );
            rampModule.getBlock().setPositionInSurface( distAlongSurface );
        }
    }

    private double getBlockPosition() {
        double val = rampModule.getRampPhysicalModel().getBlock().getPositionInSurface();
        if ( rampModule.getBlock().getSurface() instanceof Ramp ) {
            return val;
        }
        else {
            return val - getGroundLength();
        }
    }

    public JComponent getComponent() {
        return modelSlider;
    }
}
