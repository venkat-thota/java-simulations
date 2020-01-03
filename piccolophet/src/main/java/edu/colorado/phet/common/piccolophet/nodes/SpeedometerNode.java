// Copyright 2002-2012, University of Colorado
package edu.colorado.phet.common.piccolophet.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.abclearncommon.math.Function;
import com.aimxcel.abclearn.common.abclearncommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.Option;
import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import edu.umd.cs.piccolo.PNode;

/**
 * TODO: Document after design finalized
 *
 * @author Sam Reid
 */
public class SpeedometerNode extends PNode {
    public final PNode underTicksLayer = new PNode(); // TODO: JO should remove this hack for plate tectonics

    private final double maxSpeed;
    private final double anglePerTick = Math.PI * 2 / 4 / 8;
    private final int numTicks = ( 8 + 2 ) * 2;

    public SpeedometerNode( String title, final double width, final ObservableProperty<Option<Double>> speed, final double maxSpeed ) {
        this.maxSpeed = maxSpeed;
        addChild( new AbcLearnPPath( new Ellipse2D.Double( 0, 0, width, width ), Color.white, new BasicStroke( 2 ), Color.gray ) );
        addChild( underTicksLayer );
        addChild( new AbcLearnPText( title ) {{
            setFont( new AbcLearnFont( 16 ) );

            //Scale the text to fit within the speedometer if it is too big
            final double MAX_WIDTH = 65.0;
            if ( getFullWidth() > MAX_WIDTH ) {
                scale( MAX_WIDTH / getFullWidth() );
            }
            setOffset( width / 2 - getFullBounds().getWidth() / 2, width * 0.2 );
        }} );
        addChild( new AbcLearnPPath( new BasicStroke( 2 ), Color.red ) {{
            speed.addObserver( new VoidFunction1<Option<Double>>() {
                public void apply( Option<Double> speed ) {
                    if ( speed.isSome() ) {
                        double angle = speedToAngle( speed.get() );
                        Vector2D center = new Vector2D( width / 2, width / 2 );
                        Vector2D delta = Vector2D.createPolar( 1.0, angle );
                        Vector2D tail = center.minus( delta.times( width / 10 ) );
                        Vector2D tip = center.plus( delta.times( width / 2 ) );
                        if ( Double.isNaN( angle ) || Double.isInfinite( angle ) ) {
                            setPathTo( new Rectangle2D.Double( 0, 0, 0, 0 ) );
                        }
                        else {
                            setPathTo( new Line2D.Double( tail.toPoint2D(), tip.toPoint2D() ) );
                        }
                    }
                    else {
                        setPathTo( new Rectangle2D.Double( 0, 0, 0, 0 ) );
                    }
                }
            } );
        }} );
        double dotWidth = 2;
        addChild( new AbcLearnPPath( new Ellipse2D.Double( width / 2 - dotWidth / 2, width / 2 - dotWidth / 2, dotWidth, dotWidth ), Color.blue ) );

        double ds = maxSpeed / numTicks;
        for ( int i = 0; i < numTicks + 1; i++ ) {
            double angle = speedToAngle( i * ds );
            Vector2D center = new Vector2D( width / 2, width / 2 );
            Vector2D delta = Vector2D.createPolar( 1.0, angle );
            Vector2D tail = center.plus( delta.times( width / 2 * ( i % 2 == 0 ? 0.9 : 0.93 ) ) );
            Vector2D tip = center.plus( delta.times( width / 2 ) );
            final AbcLearnPPath tick = new AbcLearnPPath( new Line2D.Double( tail.toPoint2D(), tip.toPoint2D() ), new BasicStroke( i % 2 == 0 ? 1 : 0.5f ), Color.black );
            addChild( tick );
        }
    }

    public double speedToAngle( double speed ) {
        final Function.LinearFunction fun = new Function.LinearFunction( 0, maxSpeed, -Math.PI - anglePerTick * 2, anglePerTick * 2 );
        return fun.evaluate( speed );
    }
}