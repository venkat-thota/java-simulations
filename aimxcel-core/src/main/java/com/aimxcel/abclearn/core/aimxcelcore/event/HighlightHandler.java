

package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.awt.Image;
import java.awt.Paint;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public abstract class HighlightHandler extends PBasicInputEventHandler {

    private boolean isMousePressed, isMouseInside;

   
    protected abstract void setHighlighted( boolean highlighted );

    @Override
    public void mouseEntered( PInputEvent event ) {
        isMouseInside = true;
        setHighlighted( true );
    }

    @Override
    public void mouseExited( PInputEvent event ) {
        isMouseInside = false;
        if ( !isMousePressed ) {
            setHighlighted( false );
        }
    }

    @Override
    public void mousePressed( PInputEvent event ) {
        isMousePressed = true;
        setHighlighted( true );
    }

    @Override
    public void mouseReleased( PInputEvent event ) {
        isMousePressed = false;
        if ( !isMouseInside ) {
            setHighlighted( false );
        }
    }

    /**
     * Highlights a PNode by changing its paint.
     */
    public static class PaintHighlightHandler extends HighlightHandler {

        private final PNode node;
        private Paint normal, highlight;

        public PaintHighlightHandler( PNode node, Paint normal, Paint highlight ) {
            this.node = node;
            this.normal = normal;
            this.highlight = highlight;
        }

        protected void setHighlighted( boolean highlighted ) {
            node.setPaint( highlighted ? highlight : normal );
        }

        public void setNormal( Paint normal ) {
            this.normal = normal;
        }

        public void setHighlight( Paint highlight ) {
            this.highlight = highlight;
        }
    }

    /**
     * Highlights a PImage by changing its image.
     */
    public static class ImageHighlightHandler extends HighlightHandler {

        private final PImage node;
        private Image normal, highlight;

        public ImageHighlightHandler( PImage node, Image normal, Image highlight ) {
            this.node = node;
            this.normal = normal;
            this.highlight = highlight;
        }

        protected void setHighlighted( boolean highlighted ) {
            node.setImage( highlighted ? highlight : normal );
        }

        public void setNormal( Image normal ) {
            this.normal = normal;
        }

        public void setHighlight( Image highlight ) {
            this.highlight = highlight;
        }
    }

    /**
     * Calls a function when highlighting changes.
     */
    public static class FunctionHighlightHandler extends HighlightHandler {

        private VoidFunction1<Boolean> function;

        public FunctionHighlightHandler( VoidFunction1<Boolean> function ) {
            this.function = function;
        }

        protected void setHighlighted( boolean highlighted ) {
            function.apply( highlighted );
        }
    }
}
