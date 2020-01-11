
package com.aimxcel.abclearn.eatingandexercise.control;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExercisePText;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class SummaryItemNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CaloricItem item;

    public SummaryItemNode( CaloricItem item, int count ) {
        this.item = item;
        PNode imageNode;
        if ( item.getImage() != null && item.getImage().trim().length() > 0 ) {
            imageNode = new PImage( BufferedImageUtils.multiScaleToHeight( EatingAndExerciseResources.getImage( item.getImage() ), 30 ) );
        }
        else {
            imageNode = new EatingAndExercisePText( item.getName() );
        }
        addChild( imageNode );
        PText textNode = new EatingAndExercisePText( "=" + EatingAndExerciseStrings.KCAL_PER_DAY_FORMAT.format( item.getCalories() ) + " " + EatingAndExerciseStrings.KCAL_PER_DAY );
        addChild( textNode );
        textNode.setOffset( imageNode.getFullBounds().getWidth(), imageNode.getFullBounds().getCenterY() - textNode.getFullBounds().getHeight() / 2 );
        if ( count != 1 ) {
            PText countNode = new EatingAndExercisePText( "" + count + " x" );
            countNode.setOffset( imageNode.getFullBounds().getX() - countNode.getFullBounds().getWidth(), imageNode.getFullBounds().getCenterY() - countNode.getFullBounds().getHeight() / 2 );
            addChild( countNode );
        }
    }

    public CaloricItem getItem() {
        return item;
    }
}
