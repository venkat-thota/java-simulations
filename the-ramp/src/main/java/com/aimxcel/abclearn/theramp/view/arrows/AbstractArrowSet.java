package com.aimxcel.abclearn.theramp.view.arrows;

import java.awt.Component;
import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.view.BlockGraphic;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;



public class AbstractArrowSet extends PNode {
    public static final String APPLIED = TheRampStrings.getString( "forces.applied" );
    public static final String TOTAL = TheRampStrings.getString( "forces.total" );
    public static final String FRICTION = TheRampStrings.getString( "controls.friction" );
    public static final String WEIGHT = TheRampStrings.getString( "forces.weight" );
    public static final String NORMAL = TheRampStrings.getString( "forces.normal" );
    public static final String WALL = TheRampStrings.getString( "forces.Wall" );

    private ArrayList graphics = new ArrayList();

    private int totalForceDY = 45;
    private BlockGraphic blockGraphic;

    public int getDefaultOffsetDY() {
        return totalForceDY;
    }

    public AbstractArrowSet( Component component, BlockGraphic blockGraphic ) {
        super();
        this.blockGraphic = blockGraphic;
    }

    public BlockGraphic getBlockGraphic() {
        return blockGraphic;
    }

    protected void addForceArrowGraphic( ForceArrowGraphic forceArrowGraphic ) {
        addChild( forceArrowGraphic );
        graphics.add( forceArrowGraphic );
    }

    public void updateGraphics() {
        if ( getVisible() ) {
            for ( int i = 0; i < graphics.size(); i++ ) {
                ForceArrowGraphic forceArrowGraphic = (ForceArrowGraphic) graphics.get( i );
                forceArrowGraphic.update();
            }
        }
    }

    public void setForceVisible( String force, boolean selected ) {
        for ( int i = 0; i < graphics.size(); i++ ) {
            ForceArrowGraphic forceArrowGraphic = (ForceArrowGraphic) graphics.get( i );
            String name = forceArrowGraphic.getName();
            if ( name.toLowerCase().indexOf( force.toLowerCase() ) >= 0 ) {
                forceArrowGraphic.setUserVisible( selected );
            }
        }
    }

    public static interface ForceComponent {
        MutableVector2D getForce();
    }

}
