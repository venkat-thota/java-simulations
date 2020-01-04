

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.util.PPaintContext;


public class BufferedPNode extends AimxcelPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PNode managedNode; // the node we're buffering
    private RescaledNode rescaledNode; // a rescaled version of the managed node
    private boolean buffered; // is buffering on?

    /**
     * Constructor.
     *
     * @param canvas      the canvas, from which we get the transform using to draw rescaled node
     * @param managedNode the node that we'll be buffering
     */
    public BufferedPNode( final AimxcelPCanvas canvas, final PNode managedNode ) {
        super();

        this.managedNode = managedNode;
        rescaledNode = new RescaledNode( canvas, managedNode.toImage() );
        buffered = true;

        addChild( managedNode );
        managedNode.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent event ) {
                // if the managed node's children change, we need a new scaling node
                if ( event.getPropertyName() == PNode.PROPERTY_CHILDREN ) {//todo: should this use .equals comparison?
                    rescaledNode = new RescaledNode( canvas, managedNode.toImage() );
                    update();
                }
            }
        } );

        update();
    }

    /**
     * Turns buffering on and off.
     *
     * @param buffered true of false
     */
    public void setBuffered( boolean buffered ) {
        this.buffered = buffered;
        update();
    }

    /**
     * Is buffering turned on?
     *
     * @return true or false
     */
    public boolean isBuffered() {
        return buffered;
    }

    /*
     * Sets the correct child based on the buffering state.
     */
    private void update() {
        removeAllChildren();
        if ( buffered ) {
            addChild( rescaledNode );
        }
        else {
            addChild( managedNode );
        }
    }

    /*
     * RescaledNode is a PImage that is a rescaled version of the managed node.
     */
    private static class RescaledNode extends PImage {

        private AimxcelPCanvas canvas;
        BufferedImage rescaledImage;
        BufferedImage originalImage;

        public RescaledNode( AimxcelPCanvas canvas, Image image ) {
            super( image );
            this.canvas = canvas;
            originalImage = BufferedImageUtils.toBufferedImage( image );
            updateImage( image.getWidth( null ) );
        }

        private void updateImage( int width ) {
            rescaledImage = BufferedImageUtils.rescaleXMaintainAspectRatio( BufferedImageUtils.copyImage( originalImage ), width );
        }

        protected void paint( PPaintContext paintContext ) {
            if ( rescaledImage != null ) {
                Rectangle2D bounds = getGlobalFullBounds();
                canvas.getAimxcelRootNode().globalToScreen( bounds );

                int desiredWidth = (int) bounds.getWidth();
//                System.out.println( "desiredWidth = " + desiredWidth + ", bounds.width=" + ( (int) bounds.getWidth() ) );
                if ( desiredWidth != rescaledImage.getWidth() ) {
                    updateImage( desiredWidth );
                }
                Graphics2D g2 = paintContext.getGraphics();
                AffineTransform originalTransform = g2.getTransform();
//                 System.out.println( "canvas.getG2().getTransform() = " + canvas.getAffineTransform() );
                g2.setTransform( canvas.getTransform() );
                g2.drawRenderedImage( rescaledImage, AffineTransform.getTranslateInstance( bounds.getX(), bounds.getY() ) );
                g2.setTransform( originalTransform );
            }
        }
    }
}