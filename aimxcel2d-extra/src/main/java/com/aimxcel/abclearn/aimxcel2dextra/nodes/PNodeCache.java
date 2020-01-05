package com.aimxcel.abclearn.aimxcel2dextra.nodes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Dimension2D;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;

public class PNodeCache extends PNode {
    private static final long serialVersionUID = 1L;
    private transient Image imageCache;
    private boolean validatingCache;

    /**
     * Override this method to customize the image cache creation process. For
     * example if you want to create a shadow effect you would do that here.
     * Fill in the cacheOffsetRef if needed to make your image cache line up
     * with the nodes children.
     * 
     * @param cacheOffsetRef output parameter that can be changed to make the
     *            cached offset line up with the node's children
     * @return an image representing this node
     */
    public Image createImageCache(final Dimension2D cacheOffsetRef) {
        return toImage();
    }

    /**
     * Returns an image that is a cached representation of its children.
     * 
     * @return image representation of its children
     */
    public Image getImageCache() {
        if (imageCache == null) {
            final PDimension cacheOffsetRef = new PDimension();
            validatingCache = true;
            resetBounds();
            imageCache = createImageCache(cacheOffsetRef);
            final PBounds b = getFullBoundsReference();
            setBounds(b.getX() + cacheOffsetRef.getWidth(), b.getY() + cacheOffsetRef.getHeight(), imageCache
                    .getWidth(null), imageCache.getHeight(null));
            validatingCache = false;
        }
        return imageCache;
    }

    /**
     * Clears the cache, forcing it to be recalculated on the next call to
     * getImageCache.
     */
    public void invalidateCache() {
        imageCache = null;
    }

    /**
     * Intercepts the normal invalidatePaint mechanism so that the node will not
     * be repainted unless it's cache has been invalidated.
     */
    public void invalidatePaint() {
        if (!validatingCache) {
            super.invalidatePaint();
        }
    }

    /**
     * Handles a repaint event issued from a node in this node's tree.
     * 
     * @param localBounds local bounds of this node that need repainting
     * @param childOrThis the node that emitted the repaint notification
     */
    public void repaintFrom(final PBounds localBounds, final PNode childOrThis) {
        if (!validatingCache) {
            super.repaintFrom(localBounds, childOrThis);
            invalidateCache();
        }
    }

    /**
     * Repaints this node, using the cached result if possible.
     * 
     * @param paintContext context in which painting should occur
     */
    public void fullPaint(final PPaintContext paintContext) {
        if (validatingCache) {
            super.fullPaint(paintContext);
        }
        else {
            final Graphics2D g2 = paintContext.getGraphics();
            g2.drawImage(getImageCache(), (int) getX(), (int) getY(), null);
        }
    }

    /**
     * By always returning false, makes the PNodeCache instance NOT pickable.
     * 
     * @param pickPath path which this node is being tested for inclusion
     * @return always returns false
     */
    protected boolean pickAfterChildren(final PPickPath pickPath) {
        return false;
    }
}
