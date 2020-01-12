
package com.aimxcel.abclearn.conductivity.macro.circuit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import com.aimxcel.abclearn.conductivity.common.StretchedBufferedImage;
import com.aimxcel.abclearn.conductivity.macro.battery.Battery;
import com.aimxcel.abclearn.conductivity.oldphetgraphics.Graphic;
import com.aimxcel.abclearn.conductivity.oldphetgraphics.ShapeGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.TransformListener;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;

public class MacroCircuitGraphic {

    public MacroCircuitGraphic( MacroCircuit macrocircuit, ModelViewTransform2D modelviewtransform2d )
            throws IOException {
        wireGraphics = new ArrayList();
        circuit = macrocircuit;
        transform = modelviewtransform2d;
        init();
    }

    void init()
            throws IOException {
        for ( int i = 0; i < circuit.numBranches(); i++ ) {
            final LinearBranch b = circuit.wireAt( i );
            if ( b instanceof Wire ) {
                java.awt.geom.Line2D.Double double1 = new java.awt.geom.Line2D.Double( b.getStartPosition().toPoint2D(), b.getEndPosition().toPoint2D() );
                Color color = new Color( 240, 130, 150 );
                final ShapeGraphic sg = new ShapeGraphic( double1, color, new BasicStroke( getParticleImage().getWidth() + 4 ) );
                wireGraphics.add( sg );
                final java.awt.geom.Line2D.Double sh1 = double1;
                transform.addTransformListener( new TransformListener() {

                    public void transformChanged( ModelViewTransform2D modelviewtransform2d ) {
                        Shape shape = transform.getAffineTransform().createTransformedShape( sh1 );
                        sg.setShape( shape );
                    }

                } );
            }
            else if ( b instanceof Resistor ) {
                java.awt.geom.Line2D.Double double2 = new java.awt.geom.Line2D.Double( b.getStartPosition().toPoint2D(), b.getEndPosition().toPoint2D() );
                resistorGraphic = new ShapeGraphic( double2, Color.yellow, new BasicStroke( getParticleImage().getWidth() * 4 ) );
                final java.awt.geom.Line2D.Double sh1 = double2;
                transform.addTransformListener( new TransformListener() {

                    public void transformChanged( ModelViewTransform2D modelviewtransform2d ) {
                        Shape shape = transform.getAffineTransform().createTransformedShape( sh1 );
                        resistorGraphic.setShape( shape );
                    }

                } );
            }
            else if ( b instanceof Battery ) {
                BufferedImage bufferedimage = getBatteryImage();
                final StretchedBufferedImage sbi = new StretchedBufferedImage( bufferedimage, new Rectangle( 0, 0, 100, 100 ) );
                battGraphic = sbi;
                transform.addTransformListener( new TransformListener() {

                    public void transformChanged( ModelViewTransform2D modelviewtransform2d ) {
                        MutableVector2D phetvector = b.getEndPosition();
                        MutableVector2D phetvector1 = b.getStartPosition();
                        int height = (int) ( batteryImage.getHeight() * 0.8 );
                        int x = transform.modelToViewX( phetvector.getX() );
                        int w = transform.modelToViewX( phetvector1.getX() ) - x;
                        int y0 = transform.modelToViewY( phetvector.getY() );
                        Rectangle rectangle = new Rectangle( x, y0 - height / 2, w, height );
                        sbi.setOutputRect( rectangle );
                    }

                } );
            }
        }

    }

    private BufferedImage getBatteryImage()
            throws IOException {
        if ( batteryImage != null ) {
            return batteryImage;
        }
        else {
            loader.setAimxcelLoader();
//            batteryImage = loader.loadBufferedImage( "images/AA-battery.gif" );
            batteryImage = loader.loadBufferedImage( "conductivity/images/battery.png" );
            return batteryImage;
        }
    }

    public static BufferedImage getParticleImage()
            throws IOException {
        if ( particleImage != null ) {
            return particleImage;
        }
        else {
            loader.setAimxcelLoader();
            ImageLoader _tmp = loader;
            particleImage = ImageLoader.loadBufferedImage( "conductivity/images/particle-blue-sml.gif" );
            return particleImage;
        }
    }

    public int numWireGraphics() {
        return wireGraphics.size();
    }

    public Graphic wireGraphicAt( int i ) {
        return (Graphic) wireGraphics.get( i );
    }

    public ShapeGraphic getResistorGraphic() {
        return resistorGraphic;
    }

    public StretchedBufferedImage getBatteryGraphic() {
        return battGraphic;
    }

    private MacroCircuit circuit;
    private ModelViewTransform2D transform;
    ArrayList wireGraphics;
    ShapeGraphic resistorGraphic;
    StretchedBufferedImage battGraphic;
    private BufferedImage batteryImage;
    private static BufferedImage particleImage;
    private static ImageLoader loader = new ImageLoader();


}
