package com.aimxcel.abclearn.lwjgl.utils;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;

public class GLDisplayList implements Runnable {
    private int id;

    private boolean alive = false;
    private final Runnable body;

    public GLDisplayList( Runnable body ) {

        this.body = body;
    }

    public void run() {
        if ( !alive ) {
            alive = true;
            id = LWJGLUtils.getDisplayListName();

            glNewList( id, GL_COMPILE );
            body.run();
            glEndList();
        }
        glCallList( id );
    }

    public void delete() {
        if ( alive ) {
            glDeleteLists( id, 1 );
            alive = false;
        }
    }
}
