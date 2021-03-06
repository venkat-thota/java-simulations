
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class SparseStackedBarChartAxisNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Function function;
    private double majorTickSpacing;
    private double max;

    private static final double MINOR_TICK_WIDTH = 8;
    private static final double MAJOR_TICK_WIDTH = 10;

    public SparseStackedBarChartAxisNode( String title, Function function, double minorTickSpacing, double majorTickSpacing, double max ) {
        this.function = function;
        this.majorTickSpacing = majorTickSpacing;
        this.max = max;
        for ( double y = 0; y <= max; y += minorTickSpacing ) {
            if ( !isMajorTick( y ) ) {
                MinorTick minorTick = new MinorTick( MINOR_TICK_WIDTH, y );
                addChild( minorTick );
            }
        }

        for ( double y = 0; y <= max; y += majorTickSpacing ) {
            MajorTick majorTick = new MajorTick( MAJOR_TICK_WIDTH, y );
            addChild( majorTick );
        }
    }

    //todo: remove duplicate code with major tick generation, convert to straightforward calculation
    private boolean isMajorTick( double yValue ) {
        for ( double y = 0; y <= max; y += majorTickSpacing ) {
            if ( Math.abs( yValue - y ) < 1E-6 ) {
                return true;
            }
        }
        return false;
    }

    public class MinorTick extends PNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MinorTick( double width, double y ) {
            AimxcelPPath path = new AimxcelPPath( new Line2D.Double( -width / 2, -modelToView( y ), width / 2, -modelToView( y ) ), new BasicStroke( 1 ), Color.black );
            addChild( path );
        }
    }

    private double modelToView( double y ) {
        return function.evaluate( y );
    }

    public class MajorTick extends PNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MajorTick( double width, double y ) {
            AimxcelPPath path = new AimxcelPPath( new Line2D.Double( -width / 2, -modelToView( y ), width / 2, -modelToView( y ) ), new BasicStroke( 2 ), Color.black );
            addChild( path );
            PText textLabel = new EatingAndExercisePText( new DecimalFormat( "0" ).format( y ) );
            textLabel.setFont( new AimxcelFont( 12, true ) );
            addChild( textLabel );
            textLabel.setOffset( path.getFullBounds().getCenterX() - textLabel.getFullBounds().getWidth() / 2,
                                 path.getFullBounds().getCenterY() - textLabel.getFullBounds().getHeight() / 2 );
            path.setVisible( false );
        }
    }

}