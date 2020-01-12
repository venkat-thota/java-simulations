
package com.aimxcel.abclearn.conductivity.macro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.conductivity.ConductivityResources;
import com.aimxcel.abclearn.conductivity.common.ArrowShape;
import com.aimxcel.abclearn.conductivity.common.TransformGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

public class EnergyTextGraphic extends TransformGraphic {

    public EnergyTextGraphic( ModelViewTransform2D modelviewtransform2d, MutableVector2D aimxcelvector ) {
        super( modelviewtransform2d );
        loc = aimxcelvector;
        text = ConductivityResources.getString( "EnergyTextGraphic.EnergyText" );
        font = new AimxcelFont( Font.PLAIN, 36 );
        smallFont = new AimxcelFont( Font.PLAIN, 18 );
    }

    public void paint( Graphics2D graphics2d ) {
        graphics2D = graphics2d;
        if ( trfShape == null ) {
            recompute();
        }
        graphics2d.setColor( Color.blue );
        graphics2d.fill( trfShape );
        graphics2d.fill( arrowShape );
        graphics2d.setColor( Color.black );
        graphics2d.fill( lowShape );
        graphics2d.fill( highShape );
    }

    private MutableVector2D getTopCenter( Rectangle2D rectangle2d ) {
        return new MutableVector2D( rectangle2d.getX() + rectangle2d.getWidth() / 2D, rectangle2d.getY() );
    }

    public void recompute() {
        GlyphVector glyphvector = font.createGlyphVector( graphics2D.getFontRenderContext(), text );
        Shape shape = glyphvector.getOutline();
        Point point = getTransform().modelToView( loc );
        AffineTransform affinetransform = new AffineTransform();
        affinetransform.translate( point.x - 15, point.y );
        affinetransform.rotate( -1.5707963267948966D );
        trfShape = affinetransform.createTransformedShape( shape );
        AbstractVector2D aimxcelvector = getTopCenter( trfShape.getBounds2D() );
        aimxcelvector = aimxcelvector.minus( 0.0D, 40D );
        Vector2D aimxcelvector1 = aimxcelvector.plus( 0.0D, -200D );
        arrowShape = ( new ArrowShape( aimxcelvector, aimxcelvector1, 50D, 50D, 20D ) ).getArrowPath();
        highShape = smallFont.createGlyphVector( graphics2D.getFontRenderContext(),
                                                 ConductivityResources.getString( "EnergyTextGraphic.HighText" ) ).getOutline( (float) aimxcelvector1.getX() - 20F,
                                                                                                                               (float) aimxcelvector1.getY() - 20F );
        lowShape = smallFont.createGlyphVector( graphics2D.getFontRenderContext(),
                                                ConductivityResources.getString( "EnergyTextGraphic.LowText" ) ).getOutline( (float) aimxcelvector.getX() - 20F,
                                                                                                                             (float) aimxcelvector.getY() + 20F );
    }

    public void update() {
        trfShape = null;
    }

    private Font font;
    private String text;
    private MutableVector2D loc;
    private Shape trfShape;
    private Shape arrowShape;
    private Graphics2D graphics2D;
    private Font smallFont;
    private Shape highShape;
    private Shape lowShape;
}
