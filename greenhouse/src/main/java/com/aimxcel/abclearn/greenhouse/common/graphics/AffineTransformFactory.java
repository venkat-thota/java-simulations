package com.aimxcel.abclearn.greenhouse.common.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;

public interface AffineTransformFactory {
    AffineTransform getTx( Rectangle outputRectangle );
}
