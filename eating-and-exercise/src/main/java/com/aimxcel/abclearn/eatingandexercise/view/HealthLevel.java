
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;
import java.awt.geom.GeneralPath;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class HealthLevel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HealthBar bar;
    private AimxcelPPath path;

    public HealthLevel( HealthBar bar ) {
        this.bar = bar;
        path = new AimxcelPPath( createPath(), Color.blue );
        addChild( path );
    }

    public void setValue( double value ) {
        double viewY = bar.getViewY( value );
        setOffset( 0, viewY );
    }

    private GeneralPath createPath() {
        DoubleGeneralPath path = new DoubleGeneralPath();
        double leftX = 0 - 2;
        double pinPoint = 4 - 2;
        double rightX = 20;
        double topY = 3;
        double bottomY = -topY;

        path.moveTo( leftX, 0 );
        path.lineTo( pinPoint, topY );
        path.lineTo( rightX, 0 );
        path.lineTo( pinPoint, bottomY );
        path.lineTo( leftX, 0 );
        return path.getGeneralPath();
    }
}
