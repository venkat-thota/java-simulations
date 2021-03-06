
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.geom.AffineTransform;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;


public class EatingAndExerciseRenderingSizeStrategy implements AimxcelPCanvas.TransformStrategy {
    private EatingAndExerciseCanvas canvas;

    public EatingAndExerciseRenderingSizeStrategy( EatingAndExerciseCanvas canvas ) {
        this.canvas = canvas;
    }

    //todo: remove magic numbers in layout
    public AffineTransform getTransform() {
        double availableHeight = canvas.getAvailableWorldHeight();
        double availableWidth = canvas.getAvailableWorldWidth();
        double maxVisibleHeight = EatingAndExerciseUnits.feetToMeters( 7.5 );//extra padding for scale node
        double maxVisibleWidth = EatingAndExerciseUnits.feetToMeters( 6 );//extra padding for scale node
        double scaleVert = availableHeight / maxVisibleHeight;
        double scaleHoriz = availableWidth / maxVisibleWidth;
//        System.out.println( "scaleVert = " + scaleVert );
//        System.out.println( "scaleHoriz = " + scaleHoriz );

        double scale = Math.min( scaleHoriz, scaleVert );
//        System.out.println( "scale = " + scale );

        AffineTransform transform = AffineTransform.getScaleInstance( scale, scale );
        transform.translate( maxVisibleHeight * 0.36,//center of human graphic is at x=0, translate onscreen
                             EatingAndExerciseUnits.feetToMeters( 6.5 ) );//translate down a bit to keep the scale onscreen
        return transform;
    }
}
