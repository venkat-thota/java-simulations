package com.aimxcel.abclearn.lwjgl.nodes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.math.PlaneF;
import com.aimxcel.abclearn.common.aimxcelcommon.math.Ray3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.Notifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;
import com.aimxcel.abclearn.lwjgl.CoreImage;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;

import static org.lwjgl.opengl.GL11.*;

public class ThreadedPlanarCoreNode extends AbstractGraphicsNode {

    // the node that we are displaying
    private final PNode node;

    // and that node wrapped so that it has zero offset
    private final PNode wrappedNode;

    private Cursor cursor = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
    private CoreImage piccoloImage;

    private List<Notifier<?>> repaintNotifiers = new ArrayList<Notifier<?>>(  );
    private UpdateListener repaintListener = new UpdateListener() {
        public void update() {
            repaint();
        }
    };

    public ThreadedPlanarCoreNode( final PNode node ) {
        this.node = node;
        wrappedNode = new ZeroOffsetNode( node );

        // initialize this correctly
        repaint();
    }

    // handles initialization and everything needed to do a full repaint (resizing actual texture if necessary)
    public void repaint() {
        Dimension currentSize = getCurrentSize();
        if ( piccoloImage == null || !size.equals( currentSize ) ) {
            size = currentSize;
            rebuildComponentImage();
            onResize.updateListeners();
        }
        piccoloImage.repaint();
    }

    private Dimension getCurrentSize() {
        PBounds bounds = node.getFullBounds();
        return new Dimension( (int) Math.ceil( bounds.getWidth() ), (int) Math.ceil( bounds.getHeight() ) );
    }

    public <T> void addRepaintNotifier( Notifier<T> notifier ) {
        repaintListener = new UpdateListener() {
            public void update() {
                repaint();
            }
        };
        notifier.addUpdateListener( repaintListener, false );
        repaintNotifiers.add( notifier );
    }

    public void recycle() {
        for ( Notifier<?> repaintNotifier : repaintNotifiers ) {
            repaintNotifier.removeListener( repaintListener );
        }
        repaintNotifiers.clear();
    }

    public boolean doesLocalRayHit( Ray3F ray ) {
        // find out where the ray hits the z=0 plane
        Vector3F planeHitPoint = PlaneF.XY.intersectWithRay( ray );

        // check for actual intersection, not just bounds intersection
        return intersects( new Vector2F( planeHitPoint.x, getComponentHeight() - planeHitPoint.y ) );
    }

    public PBounds get2DBounds() {
        return new PBounds( 0, 0, node.getFullBounds().getWidth(), node.getFullBounds().getHeight() );
    }

    @Override public void renderSelf( GLOptions options ) {
        if ( piccoloImage == null ) {
            return;
        }

        glColor4f( 1, 1, 1, 1 );

        // TODO: don't tromp over these settings?
        glEnable( GL_TEXTURE_2D );
        glShadeModel( GL_FLAT );

        piccoloImage.useTexture();

        // texture coordinates reversed compared to OrthoComponentNode
        glBegin( GL_QUADS );

        float left = 0;
        float right = piccoloImage.getWidth();
        float top = size.height;
        float bottom = size.height - piccoloImage.getHeight();

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

    // if necessary, creates a new CoreImage of a different size to display our component
    @Override protected synchronized void rebuildComponentImage() {
        // how large our CoreImage needs to be as a raster to render all of our content
        final int imageWidth = LWJGLUtils.toPowerOf2( size.width );
        final int imageHeight = LWJGLUtils.toPowerOf2( size.height );

        if ( piccoloImage != null ) {
            piccoloImage.dispose();
        }

        // create the new image within the EDT
        final CoreImage newCoreImage = new CoreImage( imageWidth, imageHeight, true, GL_LINEAR, GL_LINEAR, new AffineTransform(), wrappedNode );

        // hook up new CoreImage.
        piccoloImage = newCoreImage;
    }

    // intersection "hit" test for the underlying node. the point needs to be in piccolo coordinates for proper intersection (check for flipped y)
    public boolean intersects( Vector2F piccoloPosition ) {
        return getNode().fullPick( new PPickPath( new PCamera(), new PBounds( piccoloPosition.x, piccoloPosition.y, 0.1, 0.1 ) ) );
    }

    @Override public int getWidth() {
        return piccoloImage.getWidth();
    }

    @Override public int getHeight() {
        return piccoloImage.getHeight();
    }

    public PNode getNode() {
        return node;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor( Cursor cursor ) {
        this.cursor = cursor;
    }
}
