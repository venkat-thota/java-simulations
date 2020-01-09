package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Images;

import fj.F;
import fj.data.List;
class RopeImageMetrics {
    public static final List<Double> blueKnots = List.list( 10.0, 90.0, 170.0, 250.0 );
    public static final List<Double> redKnots = blueKnots.map( new F<Double, Double>() {
        @Override public Double f( final Double a ) {
            return Images.ROPE.getWidth() - a;
        }
    } );
}