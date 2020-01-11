
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.SliderNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class RestrictedSliderNode extends SliderNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double dragmin;
    private double dragmax;
    private RestrictedRangeNode lowerRestrictedRange;
    private RestrictedRangeNode upperRestrictedRange;

    public RestrictedSliderNode( double min, double max, double value ) {
        super( max, value, min );
        this.dragmin = min;
        this.dragmax = max;
        lowerRestrictedRange = new RestrictedRangeNode( min, dragmin );
        upperRestrictedRange = new RestrictedRangeNode( dragmax, max );

        addChild( lowerRestrictedRange );
        addChild( upperRestrictedRange );

        updateKnob();
    }

    protected double clamp( double a ) {
        return MathUtil.clamp( dragmin, super.clamp( a ), dragmax );
    }

    private class RestrictedRangeNode extends PNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private AimxcelPPath path;
        private double min;
        private double max;

        public RestrictedRangeNode( double min, double max ) {
            this.min = min;
            this.max = max;
            path = new AimxcelPPath( Color.red, new BasicStroke( 1 ), Color.black );
            addChild( path );
            updatePath();
        }

        private void updatePath() {
            path.setPathTo( createTrackShape( min, max ) );
            path.setVisible( min != max );
        }

        public void setRange( double min, double max ) {
            this.min = min;
            this.max = max;
            updatePath();
        }
    }

    private void updateRestrictedRanges() {
        lowerRestrictedRange.setRange( super.getMin(), dragmin );
        System.out.println( "min = " + getMin() + ", dragmin=" + dragmin );
        upperRestrictedRange.setRange( dragmax, super.getMax() );
    }

    public void setDragRange( double dragmin, double dragmax ) {
        this.dragmin = dragmin;
        this.dragmax = dragmax;
        updateRestrictedRanges();
    }
}
