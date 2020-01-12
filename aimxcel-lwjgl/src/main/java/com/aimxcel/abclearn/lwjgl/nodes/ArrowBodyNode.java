package com.aimxcel.abclearn.lwjgl.nodes;

import java.util.ArrayList;
import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Triangle3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.math.Arrow2F;

import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.vertex3f;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
public class ArrowBodyNode extends GLNode {

    private final Arrow2F arrow;

    public ArrowBodyNode( Arrow2F arrow ) {
        this.arrow = arrow;
    }

    @Override public void renderSelf( GLOptions options ) {
        glBegin( GL_TRIANGLES );
        for ( Triangle3F triangle : getTriangles() ) {
            vertex3f( triangle.a );
            vertex3f( triangle.b );
            vertex3f( triangle.c );
        }
        glEnd();
    }

    public List<Triangle3F> getTriangles() {
        ArrayList<Triangle3F> result = new ArrayList<Triangle3F>();

        // head
        result.add( new Triangle3F(
                new Vector3F( arrow.getRightFlap() ),
                new Vector3F( arrow.getTipLocation() ),
                new Vector3F( arrow.getLeftFlap() ) ) );

        // and two for the body
        result.add( new Triangle3F(
                new Vector3F( arrow.getLeftPin() ),
                new Vector3F( arrow.getLeftTail() ),
                new Vector3F( arrow.getRightTail() ) ) );
        result.add( new Triangle3F(
                new Vector3F( arrow.getRightPin() ),
                new Vector3F( arrow.getLeftPin() ),
                new Vector3F( arrow.getRightTail() ) ) );

        return result;
    }
}
