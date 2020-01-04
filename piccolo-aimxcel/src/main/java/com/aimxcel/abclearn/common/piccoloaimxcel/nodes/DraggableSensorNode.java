
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.common.piccoloaimxcel.event.CursorHandler;
import com.aimxcel.abclearn.common.piccoloaimxcel.event.RelativeDragHandler;

import static com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources.PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_UNKNOWN;

/**
 * Adds interactivity (mouse hand and dragging) to sensor node.
 *
 * @author Sam Reid
 */
public class DraggableSensorNode<T> extends SensorNode<T> {
    private final Function1<Point2D, Point2D> boundedConstraint;

    public DraggableSensorNode( final ModelViewTransform transform, final PointSensor<T> tPointSensor, final ObservableProperty<Function1<T, String>> formatter, String title,
                                T valueWithLongestString ) {
        this( transform, tPointSensor, formatter, new Function1.Identity<Point2D>(), PICCOLO_AIMXCEL_VELOCITY_SENSOR_NODE_UNKNOWN, title, valueWithLongestString );
    }

    public DraggableSensorNode( final ModelViewTransform transform, final PointSensor<T> tPointSensor, final ObservableProperty<Function1<T, String>> formatter, final Function1<Point2D, Point2D> boundedConstraint, final String unknownDisplayString, String title,
                                T valueWithLongestString ) {
        super( transform, tPointSensor, formatter, unknownDisplayString, title, valueWithLongestString );
        this.boundedConstraint = boundedConstraint;
        addInteraction();
    }

    private void addInteraction() {
        //Add interactivity
        addInputEventListener( new CursorHandler() );
        addInputEventListener( new RelativeDragHandler( this, transform, pointSensor.position, boundedConstraint ) {
            @Override protected void sendMessage( final Point2D modelPoint ) {
                DraggableSensorNode.this.sendMessage( modelPoint );
            }
        } );
    }

    //allows sending a sim sharing message before firing any model events
    protected void sendMessage( final Point2D modelPoint ) {
    }
}
