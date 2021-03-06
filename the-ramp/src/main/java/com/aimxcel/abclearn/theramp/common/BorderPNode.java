
package com.aimxcel.abclearn.theramp.common;

import java.awt.*;

import javax.swing.border.Border;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;



public class BorderPNode extends PNode {
    private Border border;
    private Rectangle borderRectangle;
    private Component component;

    public BorderPNode( Component component, Border border, Rectangle borderRectangle ) {
        this.component = component;
        this.border = border;
        setBorderRectangle( borderRectangle );
    }

    protected void paint( PPaintContext paintContext ) {
        super.paint( paintContext );
        Rectangle rect = getBorderRectangle();
        border.paintBorder( component, paintContext.getGraphics(), rect.x, rect.y, rect.width, rect.height );
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder( Border border ) {
        this.border = border;
        changed();
    }

    public Rectangle getBorderRectangle() {
        return borderRectangle;
    }

    public void setBorderRectangle( Rectangle borderRectangle ) {
        this.borderRectangle = borderRectangle;
        changed();
    }

    private void changed() {
        setBounds( getBorderRectangle() );
        repaint();
    }
}
