
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function;
import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;

public class HeartNode extends PImage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PImage heart;
    private Human human;
    private AimxcelPPath smilePath;

    public HeartNode( Human human ) {
        this.human = human;
        heart = new PImage( EatingAndExerciseResources.getImage( "heart.png" ) );
        addChild( heart );

        double eyeDX = 80;
        double eyeY = 50;
        double eyeScale = 0.4;

        PImage leftEye = new PImage( BufferedImageUtils.multiScale( EatingAndExerciseResources.getImage( "eye.png" ), eyeScale ) );
        leftEye.setOffset( heart.getFullBounds().getWidth() / 2 - leftEye.getFullBounds().getWidth() / 2 - eyeDX / 2, eyeY );
        addChild( leftEye );

        PImage rightEye = new PImage( BufferedImageUtils.multiScale( EatingAndExerciseResources.getImage( "eye.png" ), eyeScale ) );
        rightEye.setOffset( heart.getFullBounds().getWidth() / 2 - rightEye.getFullBounds().getWidth() / 2 + eyeDX / 2, eyeY );
        addChild( rightEye );

        smilePath = new AimxcelPPath( new BasicStroke( 14f ), Color.black );
        addChild( smilePath );
        human.addListener( new Human.Adapter() {
            public void heartHealthChanged() {
                updateSmile();
            }
        } );
        updateSmile();
    }

    private void updateSmile() {
        DoubleGeneralPath smile = new DoubleGeneralPath();
        double smileInsetScaleX = 0.3;
        double smileYFrac = 0.6;
        double happiness = new Function.LinearFunction( 0, 1, -3, 1 ).evaluate( human.getHeartHealth() );
        happiness = MathUtil.clamp( -0.5, happiness, 1 );
//        System.out.println( "human.getHeartHealth(); = " + human.getHeartHealth() + ", happiness=" + happiness );
        double controlPointDY = 50 * happiness;
        smile.moveTo( heart.getFullBounds().getWidth() * smileInsetScaleX, heart.getFullBounds().getHeight() * smileYFrac );
        smile.curveTo( heart.getFullBounds().getWidth() * 0.4, heart.getFullBounds().getHeight() * smileYFrac + controlPointDY,
                       heart.getFullBounds().getWidth() * 0.6, heart.getFullBounds().getHeight() * smileYFrac + controlPointDY,
                       heart.getFullBounds().getWidth() * ( 1 - smileInsetScaleX ), heart.getFullBounds().getHeight() * smileYFrac
        );
        smilePath.setOffset( 0, happiness < 0 ? Math.abs( happiness ) * 20 : 0 );
        smilePath.setPathTo( smile.getGeneralPath() );
    }
}
