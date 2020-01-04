 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import edu.umd.cs.piccolo.PNode;

import static com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources.PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_SPEED;
import static com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources.PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_UNKNOWN;

public class VelocitySensorNode extends DraggableSensorNode<Vector2D> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final VelocitySensor velocitySensor;

    public VelocitySensorNode( final ModelViewTransform transform, final VelocitySensor velocitySensor, final double arrowScale, final ObservableProperty<Function1<Vector2D, String>> formatter,
                               Vector2D valueWithLongestString ) {
        this( transform, velocitySensor, arrowScale, formatter, new Function1.Identity<Point2D>(), PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_UNKNOWN, valueWithLongestString );
    }

    public VelocitySensorNode( final ModelViewTransform transform,
                               final VelocitySensor velocitySensor,

                               //Scale to use for the vector--the length of the vector is the view value times this scale factor
                               final double arrowScale,
                               final ObservableProperty<Function1<Vector2D, String>> formatter,
                               final Function1<Point2D, Point2D> boundedConstraint,

                               //Text to display when the value is None
                               final String unknownDisplayString,
                               final Vector2D valueWithLongestString ) {
        super( transform, velocitySensor, formatter, boundedConstraint, unknownDisplayString, PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_SPEED, valueWithLongestString );
        this.velocitySensor = velocitySensor;

        //Add an arrow that points in the direction of the velocity, with a magnitude proportional to the speed
        //Set the fractionalHeadHeight to 0.75 so that when the arrow gets small (so that the arrowhead is 75% of the arrow itself), the head will start to shrink and so will the tail
        addChild( new ArrowNode( new Point2D.Double(), new Point2D.Double( 100, 100 ), 20, 20, 10, 0.75, true ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( Color.blue );
            setStrokePaint( Color.black );
            setStroke( new BasicStroke( 1 ) );
            final PropertyChangeListener updateArrow = new PropertyChangeListener() {
                public void propertyChange( PropertyChangeEvent evt ) {
                    final Option<Vector2D> value = velocitySensor.value.get();
                    if ( value.isNone() ) {
                        setVisible( false );
                    }
                    else {
                        Vector2D v = transform.modelToViewDelta( value.get() ).times( arrowScale );

                        //Show speed vector at the tail instead of centered
                        setTipAndTailLocations( velocityPointNode.getFullBounds().getCenterX() + v.getX(), velocityPointNode.getFullBounds().getMaxY() + v.getY(),
                                                velocityPointNode.getFullBounds().getCenterX(), velocityPointNode.getFullBounds().getMaxY() );
                        setVisible( true );
                    }
                }
            };
            velocityPointNode.addPropertyChangeListener( PNode.PROPERTY_FULL_BOUNDS, updateArrow );
            velocitySensor.value.addObserver( new SimpleObserver() {
                public void update() {
                    updateArrow.propertyChange( null );
                }
            } );
        }} );
    }
}