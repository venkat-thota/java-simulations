package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function.LinearFunction;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

public class WaterBucketNode extends StackableNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BufferedImage image;
    private final Property<Option<Double>> acceleration;
    private final AimxcelPPath water;
    private final ArrayList<Double> history = new ArrayList<Double>();
    private final StackableNodeContext context;

    public WaterBucketNode( IUserComponent component, final StackableNodeContext context, final BufferedImage image, final double mass, final int pusherOffset, BooleanProperty showMass, final Property<Option<Double>> acceleration ) {
        super( component, context, image, mass, pusherOffset, showMass, false, image, image );
        this.image = image;
        this.acceleration = acceleration;
        water = new AimxcelPPath( new Color( 9, 125, 159 ) );
        addChild( water );
        water.moveToBack();
        this.context = context;
    }

    public void stepInTime() {
        double acceleration = this.acceleration.get().get();
        history.add( acceleration );
        while ( history.size() > 7 ) {
            history.remove( 0 );
        }
        //Metrics based on original image size of 98 pixels wide.
        double padX = 4.5;
        double padY = 9;
        double s = ( (double) image.getWidth() ) / 98.0;
        LinearFunction leftLineX = new LinearFunction( 0, 1, ( 1 + padX ) * s, ( 10 + padX ) * s );
        LinearFunction leftLineY = new LinearFunction( 0, 1, ( 9 - padY ) * s, ( 102 - padY ) * s );

        LinearFunction rightLineX = new LinearFunction( 1, 0, ( 87 - padX ) * s, ( 96 - padX ) * s );
        LinearFunction rightLineY = new LinearFunction( 1, 0, ( 102 - padY ) * s, ( 9 - padY ) * s );

        double min = 0.5; //Water level when acceleration = 0
        double sum = 0.0;
        for ( Double aDouble : history ) {
            sum = sum + aDouble;
        }
        double composite = sum / history.size();
        double delta = context.isInStack( this ) ? -composite / 50 : 0;
        final DoubleGeneralPath path = new DoubleGeneralPath( leftLineX.evaluate( min + delta ), leftLineY.evaluate( min + delta ) );
        path.lineTo( leftLineX.evaluate( 1 ), leftLineY.evaluate( 1 ) );
        path.lineTo( rightLineX.evaluate( 1 ), rightLineY.evaluate( 1 ) );
        path.lineTo( rightLineX.evaluate( min - delta ), rightLineY.evaluate( min - delta ) );
        path.closePath();

        water.setPathTo( path.getGeneralPath() );
    }
}