package com.aimxcel.abclearn.platetectonics.view.labels;

import java.awt.geom.AffineTransform;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants;
import com.aimxcel.abclearn.platetectonics.model.labels.RangeLabel;
import com.aimxcel.abclearn.platetectonics.util.MortalSimpleObserver;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Matrix4F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.ValueNotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.VoidNotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.nodes.GLNode;
import com.aimxcel.abclearn.lwjgl.nodes.ThreadedPlanarCoreNode;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.vertex3f;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class RangeLabelNode extends BaseLabelNode {

    // dimensions for the collapsed view
    public static final float COLLAPSED_DIAGONAL_SEGMENT_LENGTH = 10;
    public static final float COLLAPSED_HORIZONTAL_SEGMENT_LENGTH = 20;

    // dimensions for the normal view
    public static final float BAR_WIDTH = 7;
    public static final Vector3F NORMAL = Vector3F.Z_UNIT;

    // basic properties from the model label
    private final Property<Vector3F> top;
    private final Property<Vector3F> bottom;
    private final String label;

    // where the label is positioned (this can be overridden)
    private Property<Vector3F> labelLocation;

    private ThreadedPlanarCoreNode labelNode;
    private GLNode labelNodeContainer;
    private Property<Float> scale;
    private PText labelPNode;

    // whether the label itself should be rotated to be orthogonal to the top-bottom line
    private boolean shouldRotate = true;

    // may be uninitialized if not hooked to a range label instance
    // TODO: only attach these to range label instances?
    private final RangeLabel rangeLabel;

    /**
     * @param top       Top of the range (3d point)
     * @param bottom    Bottom of the range (3d point)
     * @param label     The label to show at labelLocation
     * @param scale     General scale parameter, so we can scale it properly
     * @param colorMode What is the current color mode
     * @param isDark    Whether we are dark for the Density color mode, or the opposite
     */
    public RangeLabelNode( final RangeLabel rangeLabel, final Property<Vector3F> top, final Property<Vector3F> bottom, String label, Property<Float> scale, Property<ColorMode> colorMode, boolean isDark ) {
        // label is centered between the top and bottom
        this( rangeLabel, top, bottom, label, scale, colorMode, isDark, new Property<Vector3F>( top.get().plus( bottom.get() ).times( 0.5f ) ) {{
            // by default, place the label perfectly between the top and bottom
            SimpleObserver recenterLabelPosition = new SimpleObserver() {
                public void update() {
                    set( top.get().plus( bottom.get() ).times( 0.5f ) );
                }
            };
            top.addObserver( recenterLabelPosition );
            bottom.addObserver( recenterLabelPosition );
        }} );
    }

    /**
     * @param top           Top of the range (3d point)
     * @param bottom        Bottom of the range (3d point)
     * @param label         The label to show at labelLocation
     * @param scale         General scale parameter, so we can scale it properly
     * @param colorMode     What is the current color mode
     * @param isDark        Whether we are dark for the Density color mode, or the opposite
     * @param labelLocation The position of the label (3d point). This can update over time, and is used so we can change the position from the center
     */
    public RangeLabelNode( final RangeLabel rangeLabel, final Property<Vector3F> top, final Property<Vector3F> bottom, String label, final Property<Float> scale, final Property<ColorMode> colorMode, final boolean isDark, final Property<Vector3F> labelLocation ) {
        super( colorMode, isDark );
        this.top = top;
        this.bottom = bottom;
        this.label = label;
        this.labelLocation = labelLocation;
        this.scale = scale;
        this.rangeLabel = rangeLabel;

        final ValueNotifier<?> killNotifier = rangeLabel == null ? new VoidNotifier() : rangeLabel.disposed;

        labelPNode = new PText( label ) {{
            setFont( PlateTectonicsConstants.LABEL_FONT );
            scale( PIXEL_SCALE );
            colorMode.addObserver( new MortalSimpleObserver( colorMode, killNotifier ) {
                public void update() {
                    setTextPaint( getColor() );
                    repaint();
                }
            } );
        }};
        labelNode = new ThreadedPlanarCoreNode( new PNode() {{
            addChild( labelPNode );

            final PNode nodeRef = this;

            SimpleObserver updateRotation = new SimpleObserver() {
                public void update() {
                    setTransform( new AffineTransform() );
                    if ( shouldRotate ) {
                        rotateAboutPoint( top.get().minus( bottom.get() ).angleBetween( new Vector3F( 0, 1, 0 ) ) * ( top.get().x > bottom.get().x ? 1 : -1 ),
                                          labelPNode.getFullBounds().getWidth() / 2, labelPNode.getFullBounds().getHeight() / 2 );
                    }
                    // rescale so we draw correctly in the canvas. we will be centered later
                    ZeroOffsetNode.zeroNodeOffset( nodeRef );
                }
            };

            top.addObserver( updateRotation );
            bottom.addObserver( updateRotation );
        }} ) {{
            final ThreadedPlanarCoreNode me = this;
            colorMode.addObserver( new MortalSimpleObserver( colorMode, killNotifier ) {
                public void update() {
                    me.repaint();
                }
            } );
            top.addObserver( new SimpleObserver() {
                public void update() {
                    me.repaint();
                }
            } );
            bottom.addObserver( new SimpleObserver() {
                public void update() {
                    me.repaint();
                }
            } );
        }};
        labelNodeContainer = new GLNode();
        labelNodeContainer.addChild( labelNode );
        addChild( labelNodeContainer );

        requireDisabled( GL_DEPTH_TEST );
        requireEnabled( GL_BLEND );
    }

    @Override
    public void renderSelf( GLOptions options ) {
        super.renderSelf( options );

        Vector3F topToBottom = bottom.get().minus( top.get() ).normalized();
        Vector3F crossed = topToBottom.cross( NORMAL ).normalized();
        Vector3F perpendicular = crossed.times( scale.get() * BAR_WIDTH / 2 );
        Vector3F middle = labelLocation.get();
        float labelAllowance = (float) ( labelPNode.getFullBounds().getHeight() / 2 * LABEL_SCALE * 1.3f * scale.get() );

        Vector3F topMiddle = middle.minus( topToBottom.times( labelAllowance ) );
        Vector3F bottomMiddle = middle.plus( topToBottom.times( labelAllowance ) );

        boolean labelFits = topMiddle.minus( top.get() ).dot( topToBottom ) >= 0;

        shouldRotate = labelFits;

        labelNode.transform.set( Matrix4F.translation( labelLocation.get().x,
                                                       labelLocation.get().y,
                                                       labelLocation.get().z ) );
        labelNode.transform.append( Matrix4F.scaling( LABEL_SCALE * scale.get() ) );
        labelNode.transform.append( Matrix4F.translation( -labelNode.getComponentWidth() / 2,
                                                          -labelNode.getComponentHeight() / 2,
                                                          0 ) );

        if ( labelFits ) {
            labelNodeContainer.transform.set( Matrix4F.IDENTITY );

            glBegin( GL_LINES );
            LWJGLUtils.color4f( getColor() );
            vertex3f( top.get().plus( perpendicular ) );
            vertex3f( top.get().minus( perpendicular ) );

            vertex3f( bottom.get().plus( perpendicular ) );
            vertex3f( bottom.get().minus( perpendicular ) );

            vertex3f( top.get() );
            vertex3f( topMiddle );

            vertex3f( bottomMiddle );
            vertex3f( bottom.get() );
            glEnd();
        }
        else {
            final float labelOffset = (float) ( labelPNode.getFullBounds().getWidth() / 2 ) * LABEL_SCALE * 1.1f * scale.get();
            labelNodeContainer.transform.set( Matrix4F.translation( ( COLLAPSED_DIAGONAL_SEGMENT_LENGTH + COLLAPSED_HORIZONTAL_SEGMENT_LENGTH ) * scale.get() + labelOffset,
                                                                    COLLAPSED_DIAGONAL_SEGMENT_LENGTH * scale.get(), 0 ) );

            glBegin( GL_LINE_STRIP );
            LWJGLUtils.color4f( getColor() );
            vertex3f( middle );
            vertex3f( middle.plus( new Vector3F( COLLAPSED_DIAGONAL_SEGMENT_LENGTH, COLLAPSED_DIAGONAL_SEGMENT_LENGTH, 0 ).times( scale.get() ) ) );
            vertex3f( middle.plus( new Vector3F( COLLAPSED_DIAGONAL_SEGMENT_LENGTH + COLLAPSED_HORIZONTAL_SEGMENT_LENGTH,
                                                 COLLAPSED_DIAGONAL_SEGMENT_LENGTH, 0 ).times( scale.get() ) ) );
            glEnd();
        }
    }
}
