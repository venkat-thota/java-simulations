package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import fj.data.List;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Images;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty.DoubleProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;

import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.RESOURCES;
import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Images.PUSHER_STRAIGHT_ON;
import static com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils.flipX;
import static java.lang.Math.round;

class PusherNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lastNonzeroAppliedForce = 1E-6;
    private final PImage pusher;
    private final DoubleProperty appliedForce;

    private final BufferedImage pusherFallDownRight = flipX( Images.PUSHER_FALL_DOWN );
    private final BufferedImage pusherFallDownLeft = Images.PUSHER_FALL_DOWN;

    public PusherNode( final BooleanProperty fallen, final PNode skateboard, final double grassY, final DoubleProperty appliedForce,
                       final Property<List<StackableNode>> stack, final ObservableProperty<SpeedValue> speedValue, final ObservableProperty<Double> velocity,
                       final BooleanProperty playing, final BooleanProperty movedSliderOnce ) {
        this.appliedForce = appliedForce;
        pusher = new PImage( PUSHER_STRAIGHT_ON );
        pusher.scale( 0.8 * 0.9 );
        addChild( pusher );

        final SimpleObserver update = new SimpleObserver() {
            public void update() {

                //Don't show the pusher unless the user has moved the slider once
//                pusher.setVisible( movedSliderOnce.get() );

                double appliedForce = PusherNode.this.appliedForce.get();
                if ( appliedForce != 0.0 ) {
                    setOffset( 0, 0 );
                }
                if ( appliedForce != 0 ) {
                    lastNonzeroAppliedForce = appliedForce;
                }

                boolean behindSkateboard = MathUtil.getSign( lastNonzeroAppliedForce ) == MathUtil.getSign( velocity.get() );

                if ( appliedForce == 0 ) {
                    if ( behindSkateboard ) {
                        pusher.setImage( fallen.get() ? ( lastNonzeroAppliedForce > 0 ? pusherFallDownRight : pusherFallDownLeft ) :
                                         PUSHER_STRAIGHT_ON );

                        if ( lastNonzeroAppliedForce > 0 ) {
                            pusher.setOffset( skateboard.getFullBounds().getX() - pusher.getFullBounds().getWidth() + 0, grassY - pusher.getFullBounds().getHeight() );
                        }
                        else {
                            pusher.setOffset( skateboard.getFullBounds().getMaxX(), grassY - pusher.getFullBounds().getHeight() );
                        }
                    }

                    //If standing where the skateboard is moving, don't show as fallen because it could let students repeatedly run over the character and be distracting
                    else {
                        pusher.setImage( PUSHER_STRAIGHT_ON );
                        pusher.setOffset( skateboard.getFullBounds().getMaxX(), grassY - pusher.getFullBounds().getHeight() );
                    }
                }
                else {
                    if ( stack.get().length() > 0 ) {
                        final int offset = stack.get().index( 0 ).pusherOffset;
                        final BufferedImage image = RESOURCES.getImage( "pusher_" + getImageIndex( appliedForce ) + ".png" );
                        if ( appliedForce > 0 ) {
                            pusher.setImage( image );

                            //translate right another 15 for crate
                            pusher.setOffset( skateboard.getFullBounds().getX() - pusher.getFullBounds().getWidth() + offset, grassY - pusher.getFullBounds().getHeight() );
                        }
                        else {

                            pusher.setImage( flipX( image ) );//Could be sped up if necessary
                            pusher.setOffset( skateboard.getFullBounds().getMaxX() - offset, grassY - pusher.getFullBounds().getHeight() );
                        }
                    }
                }
            }
        };
        appliedForce.addObserver( update );
        speedValue.addObserver( update );
        fallen.addObserver( update );
        playing.addObserver( update );
    }

    private int getImageIndex( final Double appliedForce ) {
        int maxImageIndex = 14;
        return (int) round( Math.abs( appliedForce ) / AppliedForceSliderControl.MAX_APPLIED_FORCE * ( maxImageIndex - 0.5 ) );
    }
}