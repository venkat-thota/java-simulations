
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseModel;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseModel.Units;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

public class ScaleNode extends PNode {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double FACE_WIDTH = 0.9;
    private static final double FACE_HEIGHT = 0.13;
    private static final double FACE_Y = 0.05;
    private static final double DEPTH_DX = 0.06;
    private static final double DEPTH_DY = 0.1;
    private static final float STROKE_WIDTH = 0.02f;
    private static final Color SCALE_COLOR = Color.LIGHT_GRAY;
    private static final String BMI_LABEL = EatingAndExerciseResources.getString( "bmi" );
    private static final String BMI_UNITS = EatingAndExerciseResources.getString( "units.bmi" );
    private static final double TEXT_SCALE = 1.0 / 175.0;
    
    private final EatingAndExerciseModel model;
    private final Human human;
    private final PText weightBMIReadout;

    public ScaleNode( final EatingAndExerciseModel model, Human human ) {
        
        this.model = model;
        this.human = human;
        
        // top face of the scale
        DoubleGeneralPath topPath = new DoubleGeneralPath();
        topPath.moveTo( -FACE_WIDTH / 2, FACE_Y );
        topPath.lineTo( -FACE_WIDTH / 2 + DEPTH_DX, FACE_Y - DEPTH_DY );
        topPath.lineTo( FACE_WIDTH / 2 - DEPTH_DX, FACE_Y - DEPTH_DY );
        topPath.lineTo( FACE_WIDTH / 2, FACE_Y );
        topPath.lineTo( -FACE_WIDTH / 2, FACE_Y );
        addChild( new AimxcelPPath( topPath.getGeneralPath(), SCALE_COLOR, new BasicStroke( STROKE_WIDTH ), Color.black ) );

        // front face
        DoubleGeneralPath facePath = new DoubleGeneralPath();
        facePath.moveTo( -FACE_WIDTH / 2, FACE_Y );
        facePath.lineTo( -FACE_WIDTH / 2, FACE_Y + FACE_HEIGHT );
        facePath.lineTo( FACE_WIDTH / 2, FACE_Y + FACE_HEIGHT );
        facePath.lineTo( FACE_WIDTH / 2, FACE_Y );
        facePath.lineTo( -FACE_WIDTH / 2, FACE_Y );
        PNode faceNode = new AimxcelPPath( facePath.getGeneralPath(), SCALE_COLOR, new BasicStroke( STROKE_WIDTH ), Color.black );
        addChild( faceNode );
        
        // monitor changes in the human's weight and BMI
        human.addListener( new Human.Adapter() {
            public void weightChanged() {
                updateReadout();
            }

            public void bmiChanged() {
                updateReadout();
            }
        } );
        
        // read-out appears on the front of the scale
        weightBMIReadout = new EatingAndExercisePText( "??" );
        weightBMIReadout.scale( TEXT_SCALE );
        addChild( weightBMIReadout );
        updateReadout();

        // radio buttons for switching between English and Metric units
        JPanel unitsPanel = new VerticalLayoutPanel();
        unitsPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
        ButtonGroup buttonGroup = new ButtonGroup();
        for ( int i = 0; i < EatingAndExerciseModel.availableUnits.length; i++ ) {
            
            final JRadioButton jRadioButton = new JRadioButton( EatingAndExerciseModel.availableUnits[i].getShortName(), EatingAndExerciseModel.availableUnits[i] == model.getUnits() );
            buttonGroup.add( jRadioButton );
            unitsPanel.add( jRadioButton );
            
            // connect button and model
            final Units units = EatingAndExerciseModel.availableUnits[i];
            jRadioButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    model.setUnits( units );
                }
            } );
            model.addListener( new EatingAndExerciseModel.Adapter() {
                public void unitsChanged() {
                    jRadioButton.setSelected( model.getUnits() == units );
                }
            } );
        }

        // PSwing wrapper for the units panel
        PSwing unitsPSwing = new PSwing( unitsPanel );
        unitsPSwing.setOffset( FACE_WIDTH / 2 + STROKE_WIDTH / 2, 0 );
        unitsPSwing.scale( TEXT_SCALE * 0.75 );
        addChild( unitsPSwing );
        
        // update the readout when the units change
        model.addListener( new EatingAndExerciseModel.Adapter() {
            public void unitsChanged() {
                updateReadout();
            }
        } );
    }

    private void updateReadout() {
        weightBMIReadout.setText( "" + EatingAndExerciseStrings.WEIGHT_FORMAT.format( model.getUnits().modelToViewMass( human.getMass() ) ) + " " + model.getUnits().getMassUnit() + ", " + BMI_LABEL + ": " + EatingAndExerciseStrings.BMI_FORMAT.format( human.getBMI() ) + " " + BMI_UNITS );
        updateTextLayout();
    }

    private void updateTextLayout() {
        weightBMIReadout.setOffset( 0 - weightBMIReadout.getFullBounds().getWidth() / 2, FACE_Y + FACE_HEIGHT - weightBMIReadout.getFullBounds().getHeight() - 0.01 );
    }
}
