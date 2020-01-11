package com.aimxcel.abclearn.aimxcel2dextra.nodes;

import java.awt.Image;
import java.awt.Paint;

import com.aimxcel.abclearn.aimxcel2dextra.util.ShadowUtils;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public final class PShadow extends PImage {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;


   
    public PShadow(final Image src, final Paint shadowPaint, final int blurRadius) {
        super(ShadowUtils.createShadow(src, shadowPaint, blurRadius));
    }
}