package com.aimxcel.abclearn.aimxcel2dextra.swing;

import com.aimxcel.abclearn.aimxcel2dextra.nodes.PCacheCamera;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PLayer;
import com.aimxcel.abclearn.aimxcel2dcore.PRoot;


public class PCacheCanvas extends PCanvas {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a default scene with 1 root, 1 layer, and 1 PCacheCamera.
     * 
     * @return constructed scene with PCacheCamera
     */
    protected PCamera createDefaultCamera() {
        final PRoot r = new PRoot();
        final PLayer l = new PLayer();
        final PCamera c = new PCacheCamera();

        r.addChild(c);
        r.addChild(l);
        c.addLayer(l);

        return c;
    }
}
