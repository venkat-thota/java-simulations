
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.aimxcel.abclearn.core.aimxcelcore.help.DefaultWiggleMe;
import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.activities.PActivity;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;


public class EatingAndExerciseWiggleMe extends DefaultWiggleMe {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PActivity activity;
    private PNode target;
    private PCanvas canvas;

    public EatingAndExerciseWiggleMe( final PCanvas canvas, PNode target ) {
        super( canvas, EatingAndExerciseResources.getString( "diet.choose" ) );
        this.target = target;
        this.canvas = canvas;

        setBackground( Color.green );
        setOffset( 1000, 1000 );
        setArrowTailPosition( MotionHelpBalloon.TOP_LEFT );
        setArrowLength( 60 );

        // Clicking on the canvas makes the wiggle me go away.
        canvas.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                setEnabled( false );
                getParent().removeChild( EatingAndExerciseWiggleMe.this );
                canvas.removeMouseListener( this );
            }
        } );
    }

    public void updateWiggleMeTarget() {
        //todo: remove this workaround for when this wiggle me has been removed
        if ( getParent() != null ) {
            PBounds bounds = target.getGlobalFullBounds();
            getParent().globalToLocal( bounds );
            if ( activity != null ) {
                activity.terminate();
                setOffset( 800, 600 );
            }
            activity = animateTo( bounds.getCenterX(), bounds.getMaxY() );
        }
    }

}
