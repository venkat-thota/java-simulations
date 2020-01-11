
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.control.valuenode.LinearValueControlNode;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.CaloricFoodItem;


public class BalancedDietDialog extends PaintImmediateDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BalancedDietDialog( final CaloricFoodItem item ) {
        JPanel contentPane = new VerticalLayoutPanel();
//        contentPane.setLayout( new GridBagLayout() );
        JLabel label = new JLabel( item.getLabelText(), new ImageIcon( EatingAndExerciseResources.getImage( item.getImage() ) ), SwingConstants.CENTER ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent( Graphics g ) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
                super.paintComponent( g );
            }
        };
        contentPane.add( label );
        //todo: this slider is limited to 5 because its scaling is done locally (based on previous value), so allowing it to go to zero causes failure
        //correct solution is to update value not based on previous value
        LinearValueControlNode linearValueControlNode = new LinearValueControlNode( EatingAndExerciseResources.getString( "units.calories" ), EatingAndExerciseResources.getString( "units.cal" ), 5, 4000, item.getCalories(), new DefaultDecimalFormat( "0.0" ) );
        linearValueControlNode.setTextFieldColumns( 6 );

        linearValueControlNode.addListener( new LinearValueControlNode.Listener() {
            public void valueChanged( double value ) {
                item.setTotalCalories( value );
//                System.out.println( "set calories: "+value+", item.getCalories() = " + item.getCalories() );
            }
        } );
        contentPane.add( new PNodeComponent( linearValueControlNode ) );

        contentPane.setOpaque( true );
        contentPane.setBackground( Color.white );
        SwingUtils.centerWindowOnScreen( this );
        setContentPane( contentPane );
        pack();
    }

    public static void main( String[] args ) {
        BalancedDietDialog dialog = new BalancedDietDialog( new CaloricFoodItem( "balanced diet", Human.FOOD_PYRAMID, 5, 5, 5 ) );
        dialog.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        dialog.setVisible( true );
    }

    public void resetAll() {
        dispose();
    }
}
