
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;


public class Core3DCanvas extends PSwingCanvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PNode node;

    /**
     * @param node The node to wrap within the canvas
     */
    public Core3DCanvas( PNode node ) {
        this.node = node;

        // make it see-through by default
        setOpaque( false );

        // disable the normal event handlers
        removeInputEventListener( getZoomEventHandler() );
        removeInputEventListener( getPanEventHandler() );

        // don't compromise quality by default, as this is a drop in the bucket now
        setAnimatingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        setInteractingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );

        // wrap it to maintain component bounds
        getLayer().addChild( new ZeroOffsetNode( node ) );

        // if our node changes bounds, update the underlying canvas size (so we can forward those events)
        node.addPropertyChangeListener( PNode.PROPERTY_FULL_BOUNDS, new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                updateSize();
            }
        } );

        updateSize();
    }

    // this keeps the updateSize() from being called by its own change callbacks
    private int loopcount = 0;

    // called when the PNode changes full bounds. triggers (under the hood) a transfer to a new texture
    public void updateSize() {
        if ( loopcount > 0 ) {
            return;
        }
        loopcount += 1;

        PBounds bounds = node.getFullBounds();

        // make extra-sure our canvas size changes
        // TODO: how to handle bounds that don't have origin of 0,0?
        // TODO: once JME is phased out, use the ceil version
//        setPreferredSize( new Dimension( (int) Math.ceil( bounds.width ), (int) Math.ceil( bounds.height ) ) );
        if ( !Double.isNaN( bounds.getX() ) ) {
            setPreferredSize( new Dimension( (int) bounds.width, (int) bounds.height ) );
            setSize( getPreferredSize() );
        }
        else {
            throw new RuntimeException( "Nullity!" );
        }

        loopcount -= 1;
    }
}
