package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import static com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas.STAGE_SIZE;

import java.awt.Color;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.common.ForceArrowNode;
import com.aimxcel.abclearn.forcesandmotionbasics.common.TextLocation;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
public class ForcesNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color APPLIED_FORCE_COLOR = new Color( 230, 110, 35 );
    public static final Color SUM_OF_FORCES_COLOR = new Color( 150, 200, 60 );

    public void setForces( boolean transparent, final double leftForce, final double rightForce, final boolean showSumOfForces, final Boolean showValues ) {
        removeAllChildren();
        addChild( new ForceArrowNode( transparent, Vector2D.v( STAGE_SIZE.width / 2 - 2, 225 ), leftForce, Strings.LEFT_FORCE, APPLIED_FORCE_COLOR, TextLocation.SIDE, showValues ) );
        addChild( new ForceArrowNode( transparent, Vector2D.v( STAGE_SIZE.width / 2 + 2, 225 ), rightForce, Strings.RIGHT_FORCE, APPLIED_FORCE_COLOR, TextLocation.SIDE, showValues ) );

        if ( showSumOfForces ) {
            addChild( new ForceArrowNode( transparent, Vector2D.v( STAGE_SIZE.width / 2, 150 ), leftForce + rightForce, Strings.SUM_OF_FORCES, SUM_OF_FORCES_COLOR, TextLocation.TOP, showValues ) );
        }
    }
}