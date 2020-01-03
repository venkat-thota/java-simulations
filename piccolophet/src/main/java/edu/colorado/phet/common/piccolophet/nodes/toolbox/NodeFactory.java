
package edu.colorado.phet.common.piccolophet.nodes.toolbox;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.abclearncommon.model.property.Property;
import com.aimxcel.abclearn.common.abclearncommon.view.graphics.transforms.ModelViewTransform;
import edu.colorado.phet.common.piccolophet.nodes.ToolNode;

/**
 * Used in ToolboxNode to create nodes when a ToolIconNode is dragged out of the toolbox.
 *
 * @author Sam Reid
 */
public interface NodeFactory {
    //Creates a ToolNode that will be dragged out of the toolbox
    ToolNode createNode( ModelViewTransform transform, Property<Boolean> visible, Point2D location );
}
