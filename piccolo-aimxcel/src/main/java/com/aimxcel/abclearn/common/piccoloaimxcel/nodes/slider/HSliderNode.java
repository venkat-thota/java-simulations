
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.slider;

import static com.aimxcel.abclearn.common.piccoloaimxcel.nodes.slider.VSliderNode.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.AimxcelPPath;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.AimxcelPText;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.kit.ZeroOffsetNode;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.PFrame;

/**
 * Horizontal slider that uses only Piccolo, i.e. there is no Swing involved.
 * Implementation note: This uses composition on a rotated VSliderNode for its implementation so there is no duplication of code.
 *
 * @author Sam Reid
 */
public class HSliderNode extends SliderNode {

    private final VSliderNode node;
    public final double trackLength;

    public HSliderNode( final IUserComponent userComponent, final double min, final double max, final SettableProperty<Double> value ) {
        this( userComponent, min, max, value, new Property<Boolean>( true ) );
    }

    public HSliderNode( final IUserComponent userComponent, final double min, final double max, final SettableProperty<Double> value, final ObservableProperty<Boolean> enabled ) {
        this( userComponent, min, max, DEFAULT_TRACK_THICKNESS, DEFAULT_TRACK_LENGTH, value, enabled );
    }

    public HSliderNode( final IUserComponent userComponent, final double min, final double max, double trackThickness, double trackLength,
                        final SettableProperty<Double> value, final ObservableProperty<Boolean> enabled ) {
        this( userComponent, min, max, trackThickness, trackLength, DEFAULT_KNOB_WIDTH, value, enabled );
    }

    public HSliderNode( final IUserComponent userComponent, final double min, final double max, double trackThickness, double trackLength,
                        double knobWidth, final SettableProperty<Double> value, final ObservableProperty<Boolean> enabled ) {
        super( userComponent, min, max, value );
        this.trackLength = trackLength;

        //Create the vertical node that will be rotated by 90 degrees and delegated to
        node = new VSliderNode( userComponent, min, max, trackThickness, trackLength, knobWidth, value, enabled ) {

            protected Point2D.Double createEndPoint() {
                return new Point2D.Double( trackNode.getFullBounds().getWidth(), 0 );
            }

            {
                rotate( Math.PI / 2 );
            }
        };
        addChild( new ZeroOffsetNode( node ) );
    }

    @Override public void setTrackFillPaint( final Paint paint ) {
        node.setTrackFillPaint( paint );
    }

    public AimxcelPPath getTrackNode() { return node.getTrackNode();}

    //Adds a label to the slider node and return the created tick
    public AimxcelPPath addLabel( double value, PNode labelNode ) {
        return addLabel( value, labelNode, 15, 8 );
    }

    //Adds a label to the slider node and return the created tick
    //Note: the usage of this method and esp. the last two parameters is subject to change
    public AimxcelPPath addLabel( double value, PNode labelNode, double tickLength, double tickOffset ) {

        //Wrap in a zero offset node for rotating the label
        //Wrap in another layer for setting the offset independently of the rotation
        final ZeroOffsetNode label = new ZeroOffsetNode( new ZeroOffsetNode( labelNode ) {{
            rotate( -Math.PI / 2 );
        }} );
        node.rootNode.addChild( label );
        label.setOffset( node.knobNode.getFullBounds().getWidth() / 2 + node.trackThickness / 2,
                         node.getViewY( value ) - label.getFullBounds().getHeight() / 2 );

        //Add the tick mark, At discussion on 10/6/2011 we decided every label should have a tick mark that extends to the track but is also visible when the knob is over it
        float tickStrokeWidth = 1.5f;
        final AimxcelPPath tickMark = new AimxcelPPath( new Line2D.Double( label.getCenterX() - tickOffset, label.getCenterY(),
                                                                     label.getCenterX() - tickOffset - tickLength, label.getCenterY() ),
                                                  new BasicStroke( tickStrokeWidth ), Color.darkGray );
        node.rootNode.addChild( tickMark );

        //Make the tick mark appear behind the track and knob
        tickMark.moveToBack();

        node.adjustOrigin();

        return tickMark;
    }

    public static void main( String[] args ) {

        //Init sim sharing manager so it doesn't exception when trying to record messages
        String[] myArgs = { "-study" };
        SimSharingManager.init( new AimxcelApplicationConfig( myArgs, "myProject" ) );
//        PDebug.debugBounds = true;

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                new PFrame( "test", false, new PCanvas() {{

                    //Emulate sim environment as closely as possible for debugging.
                    setInteractingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
                    setDefaultRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
                    setAnimatingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );

                    Property<Double> sliderValue = new Property<Double>( 0.0 ) {{
                        addObserver( new VoidFunction1<Double>() {
                            public void apply( Double newValue ) {
                                System.out.println( "sliderValue = " + newValue );
                            }
                        } );
                    }};

                    final HSliderNode sliderNode = new HSliderNode( new UserComponent( "mySlider" ), 0, 100,
                                                                    DEFAULT_TRACK_THICKNESS, DEFAULT_TRACK_LENGTH, sliderValue, new Property<Boolean>( true ) ) {{
                        addLabel( 0.0, new AimxcelPText( "None" ) );
                        addLabel( 100.0, new AimxcelPText( "Lots" ) );
                        setOffset( 150, 250 );
                    }};
                    getLayer().addChild( sliderNode );

                    setPanEventHandler( null );
                }} ) {{
                    setSize( 800, 600 );
                    setDefaultCloseOperation( EXIT_ON_CLOSE );
                }}.setVisible( true );
            }
        } );
    }
}