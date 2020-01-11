package com.aimxcel.abclearn.theramp.view.arrows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.theramp.view.BlockGraphic;
import com.aimxcel.abclearn.theramp.view.RampLookAndFeel;
import com.aimxcel.abclearn.theramp.view.RampPanel;


public class YArrowSet extends AbstractArrowSet {

    public YArrowSet( final RampPanel component, BlockGraphic blockGraphic ) {
        super( component, blockGraphic );
        RampLookAndFeel ralf = new RampLookAndFeel();
        String sub = TheRampStrings.getString( "coordinates.y" );
        final RampPhysicalModel rampPhysicalModel = component.getRampModule().getRampPhysicalModel();
        ForceArrowGraphic forceArrowGraphic = new ForceArrowGraphic( component, APPLIED, ralf.getAppliedForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector appliedForce = rampPhysicalModel.getAppliedForce();
                return appliedForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic totalArrowGraphic = new ForceArrowGraphic( component, TOTAL, ralf.getNetForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getTotalForce();
                return totalForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic frictionArrowGraphic = new ForceArrowGraphic( component, FRICTION, ralf.getFrictionForceColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getFrictionForce();
                return totalForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic gravityArrowGraphic = new ForceArrowGraphic( component, WEIGHT, ralf.getWeightColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getGravityForce();
                return totalForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic normalArrowGraphic = new ForceArrowGraphic( component, NORMAL, ralf.getNormalColor(), 0, new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getNormalForce();
                return totalForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        ForceArrowGraphic wallArrowGraphic = new ForceArrowGraphic( component, WALL, ralf.getWallForceColor(), getDefaultOffsetDY(), new ForceComponent() {
            public MutableVector2D getForce() {
                RampPhysicalModel.ForceVector totalForce = rampPhysicalModel.getWallForce();
                return totalForce.toYVector();
            }
        }, getBlockGraphic(), sub );

        addForceArrowGraphic( gravityArrowGraphic );
        addForceArrowGraphic( normalArrowGraphic );
        addForceArrowGraphic( frictionArrowGraphic );
        addForceArrowGraphic( forceArrowGraphic );
        addForceArrowGraphic( wallArrowGraphic );
        addForceArrowGraphic( totalArrowGraphic );

        setPickable( false );
        setChildrenPickable( false );
    }


    private Paint createYPaint( ForceArrowGraphic arrowGraphic ) {
        int imageWidth = 6;
        int imageHeight = imageWidth;
        BufferedImage texture = new BufferedImage( imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB );//todo could fail for mac.
        Graphics2D graphics2D = texture.createGraphics();
        Color background = new Color( 255, 255, 255 );

        graphics2D.setColor( background );
        graphics2D.fillRect( 0, 0, imageWidth, imageHeight );

        graphics2D.setColor( arrowGraphic.getBaseColor() );
        int stripeSize = 2;
        graphics2D.fillRect( 0, 0, imageWidth, stripeSize );
        return new TexturePaint( texture, new Rectangle2D.Double( 0, 0, texture.getWidth(), texture.getHeight() ) );
    }

    protected void addForceArrowGraphic( ForceArrowGraphic forceArrowGraphic ) {
        super.addForceArrowGraphic( forceArrowGraphic );
        forceArrowGraphic.setPaint( createYPaint( forceArrowGraphic ) );
    }

}
