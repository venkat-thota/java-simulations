package com.aimxcel.abclearn.platetectonics.view.labels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aimxcel.abclearn.platetectonics.model.PlateTectonicsModel;
import com.aimxcel.abclearn.platetectonics.model.Sample;
import com.aimxcel.abclearn.platetectonics.model.labels.BoundaryLabel;
import com.aimxcel.abclearn.platetectonics.util.Side;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.math.LWJGLTransform;

import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.color4f;
import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.vertex3f;
import static org.lwjgl.opengl.GL11.*;


public class BoundaryLabelNode extends BaseLabelNode {

    private BoundaryLabel boundaryLabel;
    private LWJGLTransform modelViewTransform;

    public BoundaryLabelNode( final BoundaryLabel boundaryLabel, final LWJGLTransform modelViewTransform,
                              Property<ColorMode> colorMode ) {
        super( colorMode, true );
        this.boundaryLabel = boundaryLabel;
        this.modelViewTransform = modelViewTransform;
        requireDisabled( GL_DEPTH_TEST );
        requireEnabled( GL_BLEND );
    }

    @Override
    public void renderSelf( GLOptions options ) {
        super.renderSelf( options );

        List<Sample> samples = boundaryLabel.boundary.samples;

        // if we are on the left side, reverse the samples, so the line drawing works nicely
        if ( ( boundaryLabel.side == Side.LEFT ) != boundaryLabel.isReversed() ) {
            samples = new ArrayList<Sample>( samples );
            Collections.reverse( samples );
        }


        glEnable( GL_LINE_STIPPLE );
//        glLineWidth( 1.5f );
        glLineStipple( 1, (short) 0xFF00 );

        glBegin( GL_LINE_STRIP );
        color4f( getColor() );
        Sample lastSample = null;
        for ( Sample sample : boundaryLabel.boundary.samples ) {
            // TODO: convert to tab-simplified LWJGL transform?
            Vector3F position = sample.getPosition();

            // important not to draw points outside of the minX / maxX bounds
            if ( position.getX() < boundaryLabel.minX.get() ) {

            }
            else if ( position.getX() > boundaryLabel.maxX.get() ) {
                // cap it off early
                float lastSampleX = lastSample.getPosition().getX();
                float ratio = ( boundaryLabel.maxX.get() - lastSampleX ) / ( position.getX() - lastSampleX );
                vertex3f( modelViewTransform.transformPosition( PlateTectonicsModel.convertToRadial( position.times( ratio ).plus( lastSample.getPosition().times( 1 - ratio ) ) ) ) );
            }
            else {
                if ( lastSample != null && lastSample.getPosition().getX() < boundaryLabel.minX.get() ) {
                    // add an initial point here that is between two
                    float lastSampleX = lastSample.getPosition().getX();
                    float ratio = ( boundaryLabel.minX.get() - lastSampleX ) / ( position.getX() - lastSampleX );
                    vertex3f( modelViewTransform.transformPosition( PlateTectonicsModel.convertToRadial( position.times( ratio ).plus( lastSample.getPosition().times( 1 - ratio ) ) ) ) );
                }
                vertex3f( modelViewTransform.transformPosition( PlateTectonicsModel.convertToRadial( position ) ) );
            }

            lastSample = sample;
        }
        glEnd();

        glDisable( GL_LINE_STIPPLE );
        glLineWidth( 1 );
    }

    public BoundaryLabel getBoundaryLabel() {
        return boundaryLabel;
    }
}
