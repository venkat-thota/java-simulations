
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.Arrow;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class DefaultStackedBarChartAxisNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Function function;
    private double minorTickSpacing;
    private double majorTickSpacing;
    private double max;

    private static final double MINOR_TICK_WIDTH = 8;
    private static final double MAJOR_TICK_WIDTH = 10;

    public DefaultStackedBarChartAxisNode( String title, Function function, double minorTickSpacing, double majorTickSpacing, double max ) {
        this.function = function;
        this.minorTickSpacing = minorTickSpacing;
        this.majorTickSpacing = majorTickSpacing;
        this.max = max;
        PPath arrowPath = new AimxcelPPath( createArrowShape(), Color.black );
        addChild( arrowPath );
        ArrayList minorTicks = new ArrayList();
        for ( double y = 0; y <= max; y += minorTickSpacing ) {
            MinorTick minorTick = new MinorTick( MINOR_TICK_WIDTH, y );
            addChild( minorTick );
            minorTicks.add( minorTick );
        }

        ArrayList majorTicks = new ArrayList();
        int count = 0;
        for ( double y = 0; y <= max; y += majorTickSpacing ) {
            MajorTick majorTick = new MajorTick( MAJOR_TICK_WIDTH, y, count % 2 == 0 );
            addChild( majorTick );
            majorTicks.add( majorTick );
            count++;
        }
        PText titleNode = new EatingAndExercisePText( title );
        titleNode.setFont( new AimxcelFont( 18, true ) );
        addChild( titleNode );

        PNode bottomMajor = (PNode) minorTicks.get( 0 );
        titleNode.setOffset( arrowPath.getFullBounds().getMinX() - titleNode.getFullBounds().getHeight(), bottomMajor.getFullBounds().getMinY() - titleNode.getFullBounds().getHeight() );
        titleNode.rotate( -Math.PI / 2 );
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

		public MajorTick( double width, double y, boolean textOnLeft ) {
            AimxcelPPath path = new AimxcelPPath( new Line2D.Double( -width / 2, -modelToView( y ), width / 2, -modelToView( y ) ), new BasicStroke( 2 ), Color.black );
            addChild( path );
            PText textLabel = new EatingAndExercisePText( new DecimalFormat( "0" ).format( y ) );
            addChild( textLabel );
            textLabel.setOffset( path.getFullBounds().getX() - textLabel.getFullBounds().getWidth(),
                                 path.getFullBounds().getCenterY() - textLabel.getFullBounds().getHeight() / 2 );
            if ( !textOnLeft ) {
                textLabel.setOffset( path.getFullBounds().getMaxX(),
                                     path.getFullBounds().getCenterY() - textLabel.getFullBounds().getHeight() / 2 );
            }
        }
    }

    private Shape createArrowShape() {
        return new Arrow( new Point2D.Double( 0, 0 ), new Point2D.Double( 0, -modelToView( max ) ), 20, 20, 5 ).getShape();
    }
}
