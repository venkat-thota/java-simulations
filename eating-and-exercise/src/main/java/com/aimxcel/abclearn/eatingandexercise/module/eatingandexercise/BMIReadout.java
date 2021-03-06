
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class BMIReadout extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PText pText;
    private Human human;
    private AimxcelPPath background;

    public BMIReadout( Human human ) {
        this.pText = new PText();
        pText.setFont( new AimxcelFont( 12, true ) );
        this.human = human;

        human.addListener( new Human.Adapter() {
            public void bmiChanged() {
                updateReadout();
            }
        } );

        background = new AimxcelPPath( new Color( 0.8f, 0.2f, 0.3f ) );
        addChild( background );
        addChild( pText );
        updateReadout();
    }

    private void updateReadout() {
        pText.setText( "BMI: " + new DecimalFormat( "0.0" ).format( human.getBMI() ) + " " + EatingAndExerciseResources.getString( "units.bmi" ) );
        Rectangle2D v = RectangleUtils.expand( pText.getFullBounds(), 2, 2 );
//        background.setPathTo( v );
        background.setPathTo( new RoundRectangle2D.Double( v.getX(), v.getY(), v.getWidth(), v.getHeight(), 12, 12 ) );
    }
}
