package com.aimxcel.abclearn.aimxcel2dcore.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.event.EventListenerList;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEventListener;

public class PPickPath implements PInputEventListener {
    /** Global pick path. */
    public static PPickPath CURRENT_PICK_PATH;

    /** Used when calculating the scale. */
    private static final double[] PTS = new double[4];

    /** Stack of nodes representing all picked nodes. */
    private PStack nodeStack;

    private final PCamera topCamera;
    private PStack transformStack;
    private PStack pickBoundsStack;
    private PCamera bottomCamera;
    private HashMap excludedNodes;

    /**
     * Creates a pick pack originating from the provided camera and with the
     * given screen pick bounds.
     * 
     * @param camera camera from which the pickpath originates
     * @param aScreenPickBounds bounds of pick area
     */
    public PPickPath(final PCamera camera, final PBounds aScreenPickBounds) {
        super();
        pickBoundsStack = new PStack();
        topCamera = camera;
        nodeStack = new PStack();
        transformStack = new PStack();
        pickBoundsStack.push(aScreenPickBounds);

        CURRENT_PICK_PATH = this;
    }

    /**
     * Returns the bounds of the entire PickPath taken as a whole.
     * 
     * @return bounds of the entire PickPath
     */
    public PBounds getPickBounds() {
        return (PBounds) pickBoundsStack.peek();
    }

    /**
     * Determines if the passed node has been excluded from being a member of
     * the pickpath.
     * 
     * @param node node being tested
     * @return true if node is acceptable to the path
     */
    public boolean acceptsNode(final PNode node) {
        return excludedNodes == null || !excludedNodes.containsKey(node);
    }

    // ****************************************************************
    // Picked Nodes
    // ****************************************************************

    /**
     * Pushes the provided node to the top of the pick path.
     * 
     * @param node node to be added to the pick path
     */
    public void pushNode(final PNode node) {
        nodeStack.push(node);
    }

    /**
     * Removes the topmost node from the node stack.
     * 
     * @param node completely unused in this method, but is passed in so that
     *            subclasses may be informed of it.
     */
    public void popNode(final PNode node) {
        nodeStack.pop();
    }

    /**
     * Get the bottom node on the pick path node stack. That is the last node to
     * be picked.
     * 
     * @return the bottom node on the pick path
     */
    public PNode getPickedNode() {
        return (PNode) nodeStack.peek();
    }

    // ****************************************************************
    // Iterating over picked nodes.
    // ****************************************************************

    /**
     * Return the next node that will be picked after the current picked node.
     * For instance of you have two overlapping children nodes then the topmost
     * child will always be picked first, use this method to find the covered
     * child. Return the camera when no more visual will be picked.
     * 
     * @return next node to picked after the picked node
     */
    public PNode nextPickedNode() {
        final PNode picked = getPickedNode();

        if (picked == topCamera) {
            return null;
        }
        if (excludedNodes == null) {
            excludedNodes = new HashMap();
        }

        // exclude current picked node
        excludedNodes.put(picked, picked);

        final Object screenPickBounds = pickBoundsStack.get(0);

        // reset path state
        pickBoundsStack = new PStack();
        nodeStack = new PStack();
        transformStack = new PStack();
        pickBoundsStack = new PStack();

        pickBoundsStack.push(screenPickBounds);

        // pick again
        topCamera.fullPick(this);

        // make sure top camera is pushed.
        if (getNodeStackReference().size() == 0) {
            pushNode(topCamera);
            pushTransform(topCamera.getTransformReference(false));
        }

        return getPickedNode();
    }

    /**
     * Get the top camera on the pick path. This is the camera that originated
     * the pick action.
     * 
     * @return the topmost camera of this pick pack
     */
    public PCamera getTopCamera() {
        return topCamera;
    }

    /**
     * Get the bottom camera on the pick path. This may be different then the
     * top camera if internal cameras are in use.
     * 
     * @return the camera closest to the picked node
     */
    public PCamera getBottomCamera() {
        if (bottomCamera == null) {
            bottomCamera = calculateBottomCamera();
        }
        return bottomCamera;
    }

    private PCamera calculateBottomCamera() {
        for (int i = nodeStack.size() - 1; i >= 0; i--) {
            final PNode each = (PNode) nodeStack.get(i);
            if (each instanceof PCamera) {
                return (PCamera) each;
            }
        }
        return null;
    }

    /**
     * Returns a reference to the node stack. Be Careful!
     * 
     * @return the node stack
     */
    public PStack getNodeStackReference() {
        return nodeStack;
    }

    // ****************************************************************
    // Path Transform
    // ****************************************************************

    /**
     * Returns the resulting scale of applying the transforms of the entire pick
     * path. In essence it gives you the scale at which interaction is
     * occurring.
     * 
     * @return scale at which interaction is occurring.
     */
    public double getScale() {
        // x1, y1, x2, y3
        PTS[0] = 0;
        PTS[1] = 0;
        PTS[2] = 1;
        PTS[3] = 0;

        final int count = transformStack.size();
        for (int i = 0; i < count; i++) {
            final PAffineTransform each = ((PTuple) transformStack.get(i)).transform;
            if (each != null) {
                each.transform(PTS, 0, PTS, 0, 2);
            }
        }

        return Point2D.distance(PTS[0], PTS[1], PTS[2], PTS[3]);
    }

    /**
     * Adds the transform to the pick path's transform. This is used when
     * determining the context of the current interaction.
     * 
     * @param transform transform to be added to applied to the pickpath.
     */
    public void pushTransform(final PAffineTransform transform) {
        transformStack.push(new PTuple(getPickedNode(), transform));
        if (transform != null) {
            final Rectangle2D newPickBounds = (Rectangle2D) getPickBounds().clone();
            transform.inverseTransform(newPickBounds, newPickBounds);
            pickBoundsStack.push(newPickBounds);
        }
    }

    /**
     * Pops the top most transform from the pick path.
     * 
     * @param transform unused in this method
     */
    public void popTransform(final PAffineTransform transform) {
        transformStack.pop();
        if (transform != null) {
            pickBoundsStack.pop();
        }
    }

    /**
     * Calculates the context at which the given node is being interacted with.
     * 
     * @param nodeOnPath a node currently on the pick path. An exception will be
     *            thrown if the node cannot be found.
     * 
     * @return Transform at which the given node is being interacted with.
     */
    public PAffineTransform getPathTransformTo(final PNode nodeOnPath) {
        final PAffineTransform aTransform = new PAffineTransform();

        final int count = transformStack.size();
        for (int i = 0; i < count; i++) {
            final PTuple each = (PTuple) transformStack.get(i);
            if (each.transform != null) {
                aTransform.concatenate(each.transform);
            }
            if (nodeOnPath == each.node) {
                return aTransform;
            }
        }

        throw new RuntimeException("Node could not be found on pick path");
    }

    /**
     * Process Events - Give each node in the pick path, starting at the bottom
     * most one, a chance to handle the event.
     * 
     * @param event event to be processed
     * @param eventType the type of event being processed
     */
    public void processEvent(final PInputEvent event, final int eventType) {
        event.setPath(this);

        for (int i = nodeStack.size() - 1; i >= 0; i--) {
            final PNode each = (PNode) nodeStack.get(i);

            final EventListenerList list = each.getListenerList();

            if (list != null) {
                final Object[] listeners = list.getListeners(PInputEventListener.class);

                for (int j = 0; j < listeners.length; j++) {
                    final PInputEventListener listener = (PInputEventListener) listeners[j];
                    listener.processEvent(event, eventType);
                    if (event.isHandled()) {
                        return;
                    }
                }
            }
        }
    }

    // ****************************************************************
    // Transforming Geometry - Methods to transform geometry through
    // this path.
    // <p>
    // Note that this is different that just using the
    // PNode.localToGlobal (an other coord system transform methods).
    // The PNode coord system transform methods always go directly up
    // through their parents. The PPickPath coord system transform
    // methods go up through the list of picked nodes instead. And since
    // cameras can pick their layers in addition to their children these
    // two paths may be different.
    // ****************************************************************

    /**
     * Convert the given point from the canvas coordinates, down through the
     * pick path (and through any camera view transforms applied to the path) to
     * the local coordinates of the given node.
     * 
     * @param canvasPoint point to be transformed
     * @param nodeOnPath node into which the point is to be transformed
     *            iteratively through the pick path
     * 
     * @return transformed canvasPoint in local coordinates of the picked node
     */
    public Point2D canvasToLocal(final Point2D canvasPoint, final PNode nodeOnPath) {
        return getPathTransformTo(nodeOnPath).inverseTransform(canvasPoint, canvasPoint);
    }

    /**
     * Convert the given dimension from the canvas coordinates, down through the
     * pick path (and through any camera view transforms applied to the path) to
     * the local coordinates of the given node.
     * 
     * @param canvasDimension dimension to be transformed
     * @param nodeOnPath node into which the dimension is to be transformed
     *            iteratively through the stack
     * 
     * @return transformed canvasDimension in local coordinates of the picked
     *         node
     */
    public Dimension2D canvasToLocal(final Dimension2D canvasDimension, final PNode nodeOnPath) {
        return getPathTransformTo(nodeOnPath).inverseTransform(canvasDimension, canvasDimension);
    }

    /**
     * Convert the given rectangle from the canvas coordinates, down through the
     * pick path (and through any camera view transforms applied to the path) to
     * the local coordinates of the given node.
     * 
     * @param canvasRectangle rectangle to be transformed
     * @param nodeOnPath node into which the rectangle is to be transformed
     *            iteratively through the stack
     * @return transformed canvasRectangle in local coordinates of the picked
     *         node
     */
    public Rectangle2D canvasToLocal(final Rectangle2D canvasRectangle, final PNode nodeOnPath) {
        return getPathTransformTo(nodeOnPath).inverseTransform(canvasRectangle, canvasRectangle);
    }

    /**
     * Used to associated nodes with their transforms on the transform stack.
     */
    private static class PTuple {
        public PNode node;
        public PAffineTransform transform;

        public PTuple(final PNode n, final PAffineTransform t) {
            node = n;
            transform = t;
        }
    }
}
