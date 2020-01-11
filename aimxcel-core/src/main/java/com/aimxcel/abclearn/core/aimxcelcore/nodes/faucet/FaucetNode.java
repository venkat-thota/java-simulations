
package com.aimxcel.abclearn.core.aimxcelcore.nodes.faucet;

import static com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication.RESOURCES;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TexturePaint;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.Dimension2DDouble;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.VSliderNode;
import com.aimxcel.abclearn.core.aimxcelcore.simsharing.NonInteractiveEventHandler;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class FaucetNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final BufferedImage FAUCET_IMAGE = RESOURCES.getImage( "faucet_front.png" );
    private static final BufferedImage PIPE_IMAGE = RESOURCES.getImage( "faucet_pipe.png" );

    //TODO #3199, change image-specific constants when the faucet images are revised
    // Locations in the image file where the input pipe connects to the faucet.
    private static final double INPUT_PIPE_X = 0;
    private static final double INPUT_PIPE_Y1 = 32;
    private static final double INPUT_PIPE_Y2 = 78;

    // Locations in the image file where the fluid comes out of the faucet.
    private static final double OUTPUT_PIPE_X1 = 57;
    private static final double OUTPUT_PIPE_X2 = 109;
    private static final double OUTPUT_PIPE_Y = 133;

    // Faucet handle in the image file.  Empirically determined, must be
    // update if the image changes.
    private static final Point2D HANDLE_CENTER = new Point2D.Double( 47, 8 );
    public static final Dimension2D HANDLE_SIZE = new Dimension2DDouble( 94, 16 );
    private static final boolean SHOW_HANDLE_CENTER_AND_BOUNDS = false;

    private final PImage faucetNode;
    private final FaucetSliderNode sliderNode;

    // Variant that uses a flow rate "percentage" between 0 and 1.
    public FaucetNode( IUserComponent userComponent, final Property<Double> flowRatePercentage, final ObservableProperty<Boolean> enabled, final double faucetLength, boolean snapToZeroWhenReleased ) {
        this( userComponent, 1, flowRatePercentage, enabled, faucetLength, snapToZeroWhenReleased );
    }

    // Variant that assumes default knob width.
    public FaucetNode( IUserComponent userComponent, final double maxFlowRate, final Property<Double> flowRate, ObservableProperty<Boolean> enabled, double faucetLength, boolean snapToZeroWhenReleased ) {
        this(userComponent, maxFlowRate, flowRate, enabled, faucetLength, VSliderNode.DEFAULT_KNOB_WIDTH, snapToZeroWhenReleased );
    }

    /**
     * Constructor that adapts flow rate to a percentage of max flow rate, see #3193
     *
     * @param userComponent          sim-sharing user component
     * @param maxFlowRate            the maximum flow rate, in model units
     * @param flowRate               the flow rate, in model units
     * @param enabled                determines whether the slider is enabled
     * @param faucetLength           length of the input pipe
     * @param knobWidth              width of the slider know that controls the flow
     * @param snapToZeroWhenReleased does the knob snap back to zero when the user releases it?
     */
    public FaucetNode( IUserComponent userComponent, final double maxFlowRate, final Property<Double> flowRate, ObservableProperty<Boolean> enabled, double faucetLength, double knobWidth, boolean snapToZeroWhenReleased ) {

        //Scale up the faucet since it looks better at a larger size
        setScale( 1.2 ); //TODO #3199, make the image files larger instead of scaling up

        // faucet
        faucetNode = new PImage( FAUCET_IMAGE );
        addChild( faucetNode );

        // pipe, tiled to the left of the faucet. Sub-pixel overlap to prevent seam from showing at some scale factors.
        final Rectangle2D pipeRect = new Rectangle2D.Double( -faucetLength, INPUT_PIPE_Y1, faucetLength + 0.5, INPUT_PIPE_Y2 - INPUT_PIPE_Y1 );
        AimxcelPPath pipeNode = new AimxcelPPath( pipeRect, new TexturePaint( PIPE_IMAGE, new Rectangle2D.Double( 0, pipeRect.getY(), PIPE_IMAGE.getWidth(), PIPE_IMAGE.getHeight() ) ) );
        addChild( pipeNode );

        // faucet slider
        sliderNode = new FaucetSliderNode( UserComponentChain.chain( userComponent, UserComponents.slider ), enabled, VSliderNode.DEFAULT_TRACK_LENGTH,
                                           VSliderNode.DEFAULT_TRACK_THICKNESS, knobWidth, maxFlowRate, flowRate, snapToZeroWhenReleased ) {/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

										{
            scale( HANDLE_SIZE.getWidth() / getFullBounds().getWidth() ); //scale to fit into the handle portion of the faucet image
            centerFullBoundsOnPoint( HANDLE_CENTER.getX(), HANDLE_CENTER.getY() );
        }};
        addChild( sliderNode );

        if ( SHOW_HANDLE_CENTER_AND_BOUNDS ) {
            addChild( new AimxcelPPath( new Ellipse2D.Double( -2, -2, 4, 4 ), Color.PINK ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( HANDLE_CENTER );
            }} );
            addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, HANDLE_SIZE.getWidth(), HANDLE_SIZE.getHeight() ), new BasicStroke( 1 ), Color.GREEN ) );
        }

        //sim-sharing for non-interactive nodes
        faucetNode.addInputEventListener( new NonInteractiveEventHandler( UserComponents.faucetImage ) );
        pipeNode.addInputEventListener( new NonInteractiveEventHandler( UserComponents.faucetImage ) );
    }

    public void setSliderVisible( boolean visible ) {
        sliderNode.setVisible( visible );
    }

    // Gets the center of the input pipe, in global coordinates.
    public Point2D getGlobalInputCenter() {
        return faucetNode.localToGlobal( new Point2D.Double( INPUT_PIPE_X, ( INPUT_PIPE_Y1 + INPUT_PIPE_Y2 ) / 2 ) );
    }

    // Gets the size of the input pipe, in global coordinates.
    public Dimension2D getGlobalInputSize() {
        return faucetNode.localToGlobal( new Dimension2DDouble( 0, INPUT_PIPE_Y2 - INPUT_PIPE_Y1 ) );
    }

    // Gets the center of the output pipe, in global coordinates.
    public Point2D getGlobalOutputCenter() {
        return faucetNode.localToGlobal( new Point2D.Double( ( OUTPUT_PIPE_X2 + OUTPUT_PIPE_X1 ) / 2, OUTPUT_PIPE_Y ) );
    }

    // Gets the size of the output pipe, in global coordinates.
    public Dimension2D getGlobalOutputSize() {
        return faucetNode.localToGlobal( new Dimension2DDouble( OUTPUT_PIPE_X2 - OUTPUT_PIPE_X1, 0 ) );
    }

    // Gets the center of the handle, in global coordinates.
    public Point2D getGlobalHandleCenter() {
        return faucetNode.localToGlobal( HANDLE_CENTER );
    }

    // Gets the size of the faucet handle, in global coordinates.
    public Dimension2D getGlobalHandleSize() {
        return faucetNode.localToGlobal( HANDLE_SIZE );
    }

    public static void main( String[] args ) {
        double maxFlowRate = 20; // L/sec
        Property<Double> flowRate = new Property<Double>( 0d ) {{
            addObserver( new VoidFunction1<Double>() {
                public void apply( Double flowRate ) {
                    System.out.println( "flowRate = " + flowRate + " L/sec" );
                }
            } );
        }};
        final FaucetNode faucetNode = new FaucetNode( new UserComponent( "faucet" ), maxFlowRate, flowRate, new Property<Boolean>( true ), 50, VSliderNode.DEFAULT_KNOB_WIDTH, true ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( 100, 100 );
        }};
        final AimxcelPCanvas canvas = new AimxcelPCanvas() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            getLayer().addChild( faucetNode );
            setPreferredSize( new Dimension( 500, 500 ) );
        }};
        new JFrame() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setContentPane( canvas );
            pack();
            setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
            setVisible( true );
        }};
    }
}