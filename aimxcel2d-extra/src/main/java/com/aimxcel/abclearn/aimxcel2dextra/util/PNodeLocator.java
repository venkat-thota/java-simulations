package com.aimxcel.abclearn.aimxcel2dextra.util;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class PNodeLocator extends PLocator {
    private static final long serialVersionUID = 1L;

    /** Node being located by this locator. */
    protected PNode node;

    /**
     * Constructs a locator responsible for locating the given node.
     * 
     * @param node node to be located
     */
    public PNodeLocator(final PNode node) {
        setNode(node);
    }

    /**
     * Returns the node being located by this locator.
     * 
     * @return node being located by this locator
     */
    public PNode getNode() {
        return node;
    }

    /**
     * Changes the node being located by this locator.
     * 
     * @param node new node to have this locator locate.
     */
    public void setNode(final PNode node) {
        this.node = node;
    }

    /**
     * Locates the left of the target node's bounds.
     * 
     * @return left of target node's bounds
     */
    public double locateX() {
        return node.getBoundsReference().getCenterX();
    }

    /**
     * Locates the top of the target node's bounds.
     * 
     * @return top of target node's bounds
     */
    public double locateY() {
        return node.getBoundsReference().getCenterY();
    }
}
