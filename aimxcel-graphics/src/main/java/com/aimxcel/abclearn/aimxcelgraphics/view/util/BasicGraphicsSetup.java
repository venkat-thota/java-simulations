
 package com.aimxcel.abclearn.aimxcelgraphics.view.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BasicGraphicsSetup implements GraphicsSetup {
    RenderingHints renderingHints;

    public BasicGraphicsSetup() {
        this.renderingHints = new RenderingHints( null );
        setAntialias( true );
        setBicubicInterpolation();
    }

    public void setAntialias( boolean antialias ) {
        if ( antialias ) {
            renderingHints.put( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        }
        else {
            renderingHints.put( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
        }
    }

    public void setBicubicInterpolation() {
        renderingHints.put( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
    }

    public void setNearestNeighborInterpolation() {
        renderingHints.put( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR );
    }

    public void setBilinearInterpolation() {
        renderingHints.put( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
    }

    public void setRenderQuality( boolean quality ) {
        if ( quality ) {
            renderingHints.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        }
        else {
            renderingHints.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED );
        }
    }

    public void setup( Graphics2D graphics ) {
        graphics.setRenderingHints( renderingHints );
    }

}
