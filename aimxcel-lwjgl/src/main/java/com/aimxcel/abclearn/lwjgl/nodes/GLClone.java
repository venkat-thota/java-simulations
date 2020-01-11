// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.lwjgl.nodes;

import com.aimxcel.abclearn.lwjgl.GLOptions;

/**
 * Creates a clone of a node that can be duplicated
 */
public class GLClone extends GLNode {
    public final GLNode clone;

    public GLClone( GLNode clone ) {
        this.clone = clone;
    }

    @Override protected void renderChildren( GLOptions options ) {
        super.renderChildren( options );

        clone.render( options );
    }
}
