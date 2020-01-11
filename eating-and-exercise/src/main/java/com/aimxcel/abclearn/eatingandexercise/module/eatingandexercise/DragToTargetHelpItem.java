
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.core.aimxcelcore.help.DefaultWiggleMe;
import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;
import com.aimxcel.abclearn.eatingandexercise.control.CaloricItem;
import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class DragToTargetHelpItem {
    private MotionHelpBalloon motionHelpBalloon;
    private EatingAndExerciseModule module;
    private EatingAndExerciseCanvas canvas;
    private PNode dropTarget;

    public DragToTargetHelpItem( final EatingAndExerciseModule module, EatingAndExerciseCanvas canvas, PNode dropTarget, String helpMessage, final CalorieSet selectionModel ) {
        this.module = module;
        this.canvas = canvas;
        this.dropTarget = dropTarget;
        motionHelpBalloon = new DefaultWiggleMe( canvas, helpMessage );
        motionHelpBalloon.setArrowTailPosition( MotionHelpBalloon.TOP_RIGHT );
        motionHelpBalloon.setOffset( 0, 0 );
        selectionModel.addListener( new CalorieSet.Adapter() {
            public void itemAdded( CaloricItem item ) {
                MotionHelpBalloon balloon = DragToTargetHelpItem.this.motionHelpBalloon;
                balloon.getParent().removeChild( balloon );
                balloon.setEnabled( false );

                selectionModel.removeListener( this );
            }
        } );
    }

    public void start() {
        module.getDefaultHelpPane().add( motionHelpBalloon );
        motionHelpBalloon.animateTo( dropTarget, 1500 );
    }
}
