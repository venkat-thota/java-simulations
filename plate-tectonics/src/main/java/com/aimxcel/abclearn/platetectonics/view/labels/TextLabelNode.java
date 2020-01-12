package com.aimxcel.abclearn.platetectonics.view.labels;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Matrix4F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.math.LWJGLTransform;
import com.aimxcel.abclearn.lwjgl.nodes.ThreadedPlanarCoreNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants;
import com.aimxcel.abclearn.platetectonics.model.PlateTectonicsModel;
import com.aimxcel.abclearn.platetectonics.model.labels.TextLabel;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;

public class TextLabelNode extends BaseLabelNode {

    private TextLabel textLabel;
    private LWJGLTransform modelViewTransform;
    private final Property<ColorMode> colorMode;
    private final Property<Float> scale;

    private ThreadedPlanarCoreNode labelNode;

    public TextLabelNode( final TextLabel textLabel, final LWJGLTransform modelViewTransform,
                          final Property<ColorMode> colorMode, final Property<Float> scale ) {
        super( colorMode, true );
        this.textLabel = textLabel;
        this.modelViewTransform = modelViewTransform;
        this.colorMode = colorMode;
        this.scale = scale;
        requireDisabled( GL_DEPTH_TEST );
        requireEnabled( GL_BLEND );

        labelNode = new ThreadedPlanarCoreNode( new PText( textLabel.label ) {{
            setFont( PlateTectonicsConstants.LABEL_FONT );
            scale( PIXEL_SCALE );
            colorMode.addObserver( new SimpleObserver() {
                public void update() {
                    setTextPaint( getColor() );
                    repaint();
                }
            } );
        }} );

        addChild( labelNode );

        colorMode.addObserver( new SimpleObserver() {
            public void update() {
                labelNode.repaint();
            }
        } );
    }

    @Override
    public void renderSelf( GLOptions options ) {
        super.renderSelf( options );

        Vector3F labelLocation = modelViewTransform.transformPosition( PlateTectonicsModel.convertToRadial( textLabel.centerPosition.get() ) );
        labelNode.transform.set( Matrix4F.translation( labelLocation.x, labelLocation.y, labelLocation.z ) );
        labelNode.transform.append( Matrix4F.scaling( LABEL_SCALE * scale.get() ) );
        labelNode.transform.append( Matrix4F.translation( -labelNode.getComponentWidth() / 2,
                                                          -labelNode.getComponentHeight() / 2,
                                                          0 ) );
    }
}
