package com.aimxcel.abclearn.lwjgl;
public class GLOptions implements Cloneable {
    public static enum RenderPass {
        REGULAR,

        // transparency is rendered after regular, generally with depth-write disabled
        TRANSPARENCY,
    }

    // whether we are just drawing for "picking" purposes.
    public boolean forSelection = false;
    public boolean forWireframe = false;

    public RenderPass renderPass = RenderPass.REGULAR;

    public boolean shouldSendNormals() {
        return !forSelection && !forWireframe;
    }

    public boolean shouldSendTexture() {
        return !forSelection && !forWireframe;
    }

    public GLOptions getCopy() {
        try {
            return (GLOptions) clone();
        }
        catch( CloneNotSupportedException e ) {
            throw new RuntimeException( e );
        }
    }
}
