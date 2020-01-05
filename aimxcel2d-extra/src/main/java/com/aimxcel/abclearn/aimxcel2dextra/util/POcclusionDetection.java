package com.aimxcel.abclearn.aimxcel2dextra.util;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;


public class POcclusionDetection {

    
    public void detectOccusions(final PNode n, final PBounds parentBounds) {
        detectOcclusions(n, new PPickPath(null, parentBounds));
    }

    /**
     * Traverse the pick path determining which parent nodes are occluded by
     * their children nodes. Note that this is only detecting a subset of
     * occlusions (parent, child), others such as overlapping siblings or
     * cousins are not detected.
     * 
     * @param node node from which to detect occlusions
     * @param pickPath Pick Path to traverse
     */
    public void detectOcclusions(final PNode node, final PPickPath pickPath) {
        if (!node.fullIntersects(pickPath.getPickBounds())) {
            return;
        }

        pickPath.pushTransform(node.getTransformReference(false));

        final int count = node.getChildrenCount();
        for (int i = count - 1; i >= 0; i--) {
            final PNode each = node.getChild(i);
            if (node.getOccluded()) {
                // if n has been occluded by a previous descendant then
                // this child must also be occluded
                each.setOccluded(true);
            }
            else {
                // see if child each occludes n
                detectOcclusions(each, pickPath);
            }
        }

        if (nodeOccludesParents(node, pickPath)) {
            final PNode parent = node.getParent();
            while (parent != null && !parent.getOccluded()) {
                parent.setOccluded(true);
            }
        }

        pickPath.popTransform(node.getTransformReference(false));
    }

    /**
     * Calculate whether node occludes its parents.
     * 
     * @param n node to test
     * @param pickPath pickpath identifying the parents of the node
     * @return true if parents are occluded by the node
     */
    private boolean nodeOccludesParents(final PNode n, final PPickPath pickPath) {
        return !n.getOccluded() && n.intersects(pickPath.getPickBounds()) && n.isOpaque(pickPath.getPickBounds());
    }
}
