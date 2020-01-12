package com.aimxcel.abclearn.lwjgl.nodes;

import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.vertex2fxy;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.math.Arrow2F;

/**
 * Renders the outline of an arrow (not the inside). Use ArrowNode for an entire arrow (with an outline).
 */
public class ArrowOutlineNode extends GLNode {

    private final Arrow2F arrow;

    public ArrowOutlineNode( Arrow2F arrow ) {
        this.arrow = arrow;
    }

    @Override public void renderSelf( GLOptions options ) {
        glBegin( GL_LINE_LOOP );
        vertex2fxy( arrow.getTipLocation() );
        vertex2fxy( arrow.getLeftFlap() );
        vertex2fxy( arrow.getLeftPin() );
        vertex2fxy( arrow.getLeftTail() );
        vertex2fxy( arrow.getRightTail() );
        vertex2fxy( arrow.getRightPin() );
        vertex2fxy( arrow.getRightFlap() );
        glEnd();
    }
}
