
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class HeadNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AimxcelPPath headPath;
    private Human human;
    private PNode eyeGraphics;
    private AimxcelPPath leftEye;
    private AimxcelPPath rightEye;

    public HeadNode( final Human human, Color fill, BasicStroke basicStroke, Color stroke ) {
        this.human = human;
        headPath = new AimxcelPPath( fill, basicStroke, stroke );
        addChild( headPath );

        eyeGraphics = new PNode();
        float STROKE_WIDTH = 0.015f;
        leftEye = new AimxcelPPath( createEyePath(), new BasicStroke( STROKE_WIDTH ), Color.black );
        rightEye = new AimxcelPPath( createEyePath(), new BasicStroke( STROKE_WIDTH ), Color.black );
        human.addListener( new Human.Adapter() {
            public void aliveChanged() {
                updateEyesVisible();
            }
        } );
        updateEyesVisible();
        eyeGraphics.addChild( leftEye );
        eyeGraphics.addChild( rightEye );

        addChild( eyeGraphics );
    }

    private Shape createEyePath() {
        double dx = 0.02;
        DoubleGeneralPath path = new DoubleGeneralPath();
        path.moveTo( -dx, -dx );
        path.lineTo( dx, dx );
        path.moveTo( -dx, dx );
        path.lineTo( dx, -dx );
        return path.getGeneralPath();
    }

    private void updateEyesVisible() {
        eyeGraphics.setVisible( !human.isAlive() );
    }

    public void setPathTo( Shape headShape ) {
        headPath.setPathTo( headShape );
        updateLayout();
    }

    private void updateLayout() {
        double EYE_OFFSET = 0.03;
        double FRACTION_EYES_DOWN_HEAD = 0.4;
        leftEye.setOffset( headPath.getFullBounds().getCenterX() - EYE_OFFSET, headPath.getFullBounds().getY() + headPath.getFullBounds().getHeight() * FRACTION_EYES_DOWN_HEAD );
        rightEye.setOffset( headPath.getFullBounds().getCenterX() + EYE_OFFSET, headPath.getFullBounds().getY() + headPath.getFullBounds().getHeight() * FRACTION_EYES_DOWN_HEAD );
    }
}
