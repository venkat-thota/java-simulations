package com.aimxcel.abclearn.lwjgl.nodes;

import java.awt.geom.AffineTransform;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.PlaneF;
import com.aimxcel.abclearn.common.aimxcelcommon.math.Ray3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.LWJGLCanvas;
import com.aimxcel.abclearn.lwjgl.SwingImage;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;

import static org.lwjgl.opengl.GL11.*;

public class PlanarSwingNode extends AbstractSwingGraphicsNode {

    public PlanarSwingNode( final JComponent component ) {
        super( component );

        // initialize this correctly
        rebuildComponentImage();
    }

    public boolean doesLocalRayHit( Ray3F ray ) {
        Vector3F planeHitPoint = PlaneF.XY.intersectWithRay( ray );
        return get2DBounds().contains( planeHitPoint.x, planeHitPoint.y );
    }

    @Override public void renderSelf( GLOptions options ) {
        if ( componentImage == null ) {
            return;
        }

        glColor4f( 1, 1, 1, 1 );

        // TODO: don't tromp over these settings?
        glEnable( GL_TEXTURE_2D );
        glShadeModel( GL_FLAT );

        componentImage.useTexture();

        // texture coordinates reversed compared to OrthoComponentNode
        glBegin( GL_QUADS );

        float left = 0;
        float right = componentImage.getWidth();
        float top = size.height;
        float bottom = size.height - componentImage.getHeight();

        // lower-left
        glTexCoord2f( 0, 1 );
        glVertex3f( left, bottom, 0 );

        // upper-left
        glTexCoord2f( 0, 0 );
        glVertex3f( left, top, 0 );

        // upper-right
        glTexCoord2f( 1, 0 );
        glVertex3f( right, top, 0 );

        // lower-right
        glTexCoord2f( 1, 1 );
        glVertex3f( right, bottom, 0 );
        glEnd();

        glShadeModel( GL_SMOOTH );
        glDisable( GL_TEXTURE_2D );
    }

    // if necessary, creates a new SwingImage of a different size to display our component
    @Override protected synchronized void rebuildComponentImage() {
        // how large our SwingImage needs to be as a raster to render all of our content
        final int imageWidth = LWJGLUtils.toPowerOf2( size.width );
        final int imageHeight = LWJGLUtils.toPowerOf2( size.height );

        // create the new image within the EDT
        final SwingImage newComponentImage = new SwingImage( imageWidth, imageHeight, true, GL_LINEAR, GL_LINEAR, new AffineTransform(), component );

        // do the rest of the work in the LWJGL thread
        LWJGLCanvas.addTask( new Runnable() {
            public void run() {
                // hook up new SwingImage.
                componentImage = newComponentImage;
                // TODO: why is this separated in the *ComponentNode classes?
            }
        } );
    }

}
