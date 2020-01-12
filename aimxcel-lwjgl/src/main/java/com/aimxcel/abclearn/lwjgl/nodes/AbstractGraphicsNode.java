package com.aimxcel.abclearn.lwjgl.nodes;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.ValueNotifier;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

public abstract class AbstractGraphicsNode extends GLNode {
    // notifier for when we resize
    public final ValueNotifier<AbstractGraphicsNode> onResize = new ValueNotifier<AbstractGraphicsNode>( this );

    // our current size
    protected Dimension size = new Dimension();

    public PBounds get2DBounds() {
        return new PBounds( 0, 0, size.width, size.height );
    }

    // width of the underlying graphic
    public int getComponentWidth() {
        return size.width;
    }

    // height of the underlying graphic
    public int getComponentHeight() {
        return size.height;
    }

    protected abstract void rebuildComponentImage();

    // width of the quad displaying this graphic
    public abstract int getWidth();

    // height of the quad displaying this graphic
    public abstract int getHeight();
}
