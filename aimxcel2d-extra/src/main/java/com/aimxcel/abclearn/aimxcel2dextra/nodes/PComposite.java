package com.aimxcel.abclearn.aimxcel2dextra.nodes;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;


public class PComposite extends PNode {

    /*
     * public boolean setBounds(double x, double y, double width, double height)
     * { PBounds childBounds = getUnionOfChildrenBounds(null);
     * 
     * double dx = x - childBounds.x; double dy = y - childBounds.y; double sx =
     * width / childBounds.width; double sy = height / childBounds.height;
     * double scale = sx > sy ? sx : sy;
     * 
     * Iterator i = getChildrenIterator(); while (i.hasNext()) { PNode each =
     * (PNode) i.next(); each.offset(dx, dy); each.scaleAboutPoint(scale,
     * each.getBoundsReference().x, each.getBoundsReference().y); }
     * 
     * return super.setBounds(x, y, width, height); }
     * 
     * protected void layoutChildren() {
     * getBoundsReference().setRect(getUnionOfChildrenBounds(null)); }
     */

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Return true if this node or any pickable descendants are picked. If a
     * pick occurs the pickPath is modified so that this node is always returned
     * as the picked node, event if it was a descendant node that initially
     * reported the pick.
     * 
     * @param pickPath the pick path to add the nodes to if they are picked
     * @return true if this node or one of its descendants was picked
     */
    public boolean fullPick(final PPickPath pickPath) {
        if (super.fullPick(pickPath)) {
            PNode picked = pickPath.getPickedNode();

            // this code won't work with internal cameras, because it doesn't
            // pop the cameras view transform.
            while (picked != this) {
                pickPath.popTransform(picked.getTransformReference(false));
                pickPath.popNode(picked);
                picked = pickPath.getPickedNode();
            }

            return true;
        }
        return false;
    }
}
