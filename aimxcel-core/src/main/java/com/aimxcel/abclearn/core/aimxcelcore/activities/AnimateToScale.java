 
package com.aimxcel.abclearn.core.aimxcelcore.activities;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function.LinearFunction;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.activities.PInterpolatingActivity;


public class AnimateToScale extends PInterpolatingActivity {
    private final PNode node;
    private final LinearFunction linearFunction;

    public AnimateToScale( double toScale, PNode node, long duration ) {
        this( node, new LinearFunction( 0, 1, node.getScale(), toScale ), duration );
    }

    private AnimateToScale( final PNode node, final LinearFunction linearFunction, long duration ) {
        super( duration );
        this.node = node;
        this.linearFunction = linearFunction;
    }

    @Override public void setRelativeTargetValue( final float zeroToOne ) {
        node.setScale( linearFunction.evaluate( zeroToOne ) );
    }
}