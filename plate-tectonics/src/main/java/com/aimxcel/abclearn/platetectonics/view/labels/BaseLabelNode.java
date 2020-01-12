package com.aimxcel.abclearn.platetectonics.view.labels;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.DARK_LABEL;
import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.LIGHT_LABEL;

import java.awt.*;

import com.aimxcel.abclearn.platetectonics.view.ColorMode;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.lwjgl.nodes.GLNode;

public class BaseLabelNode extends GLNode {
    private final Property<ColorMode> colorMode;
    private final boolean dark;

    public static final float PIXEL_SCALE = 3; // pixel up-scaling (for smoothness of text)
    public static final float TEXT_DISPLAY_SCALE = 0.45f; // factor for scaling the text

    // how large the text labels should be
    public static final float LABEL_SCALE = TEXT_DISPLAY_SCALE / PIXEL_SCALE;

    public BaseLabelNode( Property<ColorMode> colorMode, boolean dark ) {
        this.colorMode = colorMode;
        this.dark = dark;
    }

    private boolean hasDarkColor() {
        return dark == ( colorMode.get() == ColorMode.DENSITY || colorMode.get() == ColorMode.COMBINED );
    }

    public Color getColor() {
        return hasDarkColor() ? DARK_LABEL : LIGHT_LABEL;
    }
}
