
package com.aimxcel.abclearn.core.aimxcelcore.nodes.toolbox;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public interface ToolboxCanvas {

    //Add a child to the canvas
    void addChild( PNode node );

    //Remove a child from the canvas
    void removeChild( PNode node );

    //Get the root node used in the stage of the canvas
    PNode getRootNode();
}
