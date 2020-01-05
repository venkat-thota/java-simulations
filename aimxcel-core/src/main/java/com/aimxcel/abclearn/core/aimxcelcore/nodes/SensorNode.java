
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import static com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication.RESOURCES;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.RichSimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PDimension;


public class SensorNode<T> extends ToolNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final ModelViewTransform transform;
    public final PointSensor<T> pointSensor;
    public final ThreeImageNode bodyNode;
    public final PImage velocityPointNode;
    public final BufferedImage velocityPoint;

    public SensorNode( final ModelViewTransform transform,
                       final PointSensor<T> pointSensor,
                       final ObservableProperty<Function1<T, String>> formatter,

                       //Text to display when the value is None
                       final String unknownDisplayString, String title,

                       //The text area remains constant size based on the max of the width with this string or the title or the unknown value
                       //Ignored safely if null
                       final T valueWithLongestString ) {
        this.transform = transform;
        this.pointSensor = pointSensor;
        final int titleOffsetY = 7;
        final int readoutOffsetY = 38;

        //Add the body of the sensor, which is composed of 3 images
        bodyNode = new ThreeImageNode( RESOURCES.getImage( "velocity_left.png" ), RESOURCES.getImage( "velocity_center.png" ), RESOURCES.getImage( "velocity_right.png" ) );
        addChild( bodyNode );

        //Add the title of the sensor, which remains centered in the top of the body
        final PText titleNode = new PText( title ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( new AimxcelFont( 22 ) );
            bodyNode.addCenterWidthObserver( new SimpleObserver() {
                public void update() {
                    setOffset( bodyNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, titleOffsetY );
                }
            } );
        }};
        addChild( titleNode );

        //Add the text readout in the body of the sensor, which reads out the value of the VelocitySensor
        addChild( new PText() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( new AimxcelFont( 26 ) );
            final SimpleObserver updateTextLocation = new SimpleObserver() {
                public void update() {
                    setOffset( bodyNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, readoutOffsetY );
                }
            };
            bodyNode.addCenterWidthObserver( updateTextLocation );

            //Size the text area so that it can accommodate the largest string (be it unknown or from a value), or the title, whichever is longer
            new RichSimpleObserver() {
                @Override public void update() {
                    double largestValueWidth = 0;
                    if ( valueWithLongestString != null ) {
                        setText( formatter.get().apply( valueWithLongestString ) );
                        largestValueWidth = getFullBounds().getWidth();
                    }

                    setText( unknownDisplayString );
                    double unknownValueWidth = getFullBounds().getWidth();

                    final double titleWidth = titleNode.getFullBounds().getWidth();

                    bodyNode.setCenterWidth( Math.max( Math.max( titleWidth, largestValueWidth ), unknownValueWidth ) + 10 );
                }
            }.observe( formatter );

            new RichSimpleObserver() {
                public void update() {
                    final Option<T> value = pointSensor.value.get();

                    setText( ( value.isNone() ) ? unknownDisplayString : formatter.get().apply( value.get() ) );
                    updateTextLocation.update();
                }
            }.observe( formatter, pointSensor.value );
        }} );

        //Show a triangular tip that points to the hot spot of the sensor, i.e. where the values are read from
        velocityPoint = RESOURCES.getImage( "velocity_point.png" );
        velocityPointNode = new PImage( velocityPoint ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final PropertyChangeListener updatePosition = new PropertyChangeListener() {
                public void propertyChange( PropertyChangeEvent evt ) {
                    setOffset( bodyNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, bodyNode.getFullBounds().getMaxY() );
                }
            };
            bodyNode.addPropertyChangeListener( PROPERTY_FULL_BOUNDS, updatePosition );
            updatePosition.propertyChange( null );
        }};
        addChild( velocityPointNode );

        //Update the entire location of this node based on the location of the model ViewSensor, keeping the hot spot at the specified location.
        final SimpleObserver updateEntireLocation = new SimpleObserver() {
            public void update() {
                final Point2D.Double viewPoint = transform.modelToView( pointSensor.position.get() ).toPoint2D();
                setOffset( viewPoint.getX() - bodyNode.getFullBounds().getWidth() / 2, viewPoint.getY() - bodyNode.getFullBounds().getHeight() - velocityPoint.getHeight() );
            }
        };
        pointSensor.position.addObserver( updateEntireLocation );
        bodyNode.addCenterWidthObserver( updateEntireLocation );
    }

    //Drags all components of the velocity sensor--there is only one component, so it just translates the entire node
    @Override public void dragAll( PDimension delta ) {
        pointSensor.translate( transform.viewToModelDelta( delta ) );
    }

    //Gets the PNode for the main body of the sensor, for intersection with the toolbox
    public ThreeImageNode getBodyNode() {
        return bodyNode;
    }
}