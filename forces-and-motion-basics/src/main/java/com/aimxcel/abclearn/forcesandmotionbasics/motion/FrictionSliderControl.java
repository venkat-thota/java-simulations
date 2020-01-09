package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPText;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.HSliderNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

import static com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas.DEFAULT_FONT;
import static com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.VSliderNode.DEFAULT_TRACK_THICKNESS;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;


class FrictionSliderControl extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final double MAX = 0.5;

    public FrictionSliderControl( final SettableProperty<Double> friction ) {
        addChild( new VBox( 0, new AimxcelPText( Strings.FRICTION_TITLE, DEFAULT_FONT ),
                            new HSliderNode( UserComponents.frictionSliderKnob, 0, MAX, DEFAULT_TRACK_THICKNESS, 150, friction, new BooleanProperty( true ) ) {/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

							{
                                addLabel( 0, new AimxcelPText( Strings.NONE ) );
                                addLabel( MAX, new AimxcelPText( Strings.LOTS ) );
                            }} ) );
    }
}