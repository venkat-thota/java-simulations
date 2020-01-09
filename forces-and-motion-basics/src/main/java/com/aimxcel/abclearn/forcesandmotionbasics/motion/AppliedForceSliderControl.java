package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import fj.data.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JTextField;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.ParameterKeys;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Not;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty.DoubleProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option.Some;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPText;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.HSliderNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

import static com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas.DEFAULT_FONT;
import static com.aimxcel.abclearn.forcesandmotionbasics.motion.SpeedValue.*;
import static com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.VSliderNode.DEFAULT_TRACK_THICKNESS;


class AppliedForceSliderControl extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MAX_APPLIED_FORCE = 500;//Newtons.  Int because it is used in string values for the slider

    public AppliedForceSliderControl( final ObservableProperty<SpeedValue> speedValue, final DoubleProperty appliedForce,
                                      final Property<List<StackableNode>> stack, final boolean friction, final BooleanProperty playing, final MotionModel model ) {

        final Not enabled = Not.not( stack.valueEquals( List.<StackableNode>nil() ) );
        final String unitsString = friction ? Strings.NEWTONS__N : Strings.NEWTONS;

        final SettableProperty<Double> appliedForceSliderModel = new SettableProperty<Double>( appliedForce.get() ) {
            @Override public void set( final Double value ) {
                if ( speedValue.get() == WITHIN_ALLOWED_RANGE ) {
                    appliedForce.set( value );
                }
                else if ( speedValue.get() == RIGHT_SPEED_EXCEEDED ) {
                    appliedForce.set( MathUtil.clamp( -MAX_APPLIED_FORCE, value, 0 ) );
                }
                else { appliedForce.set( MathUtil.clamp( 0, value, MAX_APPLIED_FORCE ) ); }
                notifyIfChanged();
            }

            @Override public Double get() { return appliedForce.get(); }

            {
                speedValue.addObserver( new VoidFunction1<SpeedValue>() {
                    public void apply( final SpeedValue speedValue ) {

                        //Pass the current value through the filter in case it now needs to be clamped.
                        set( get() );
                    }
                } );
            }
        };

        final HSliderNode sliderNode = new HSliderNode( UserComponents.appliedForceSliderKnob, -MAX_APPLIED_FORCE, MAX_APPLIED_FORCE, DEFAULT_TRACK_THICKNESS, 200 * 1.75, appliedForceSliderModel, enabled ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final int longTick = 15;
            final int shortTick = 10;
            final int longTickOffset = 8;
            final int shortTickOffset = 15;
            AimxcelPPath tick1 = addLabel( -MAX_APPLIED_FORCE, new EnableAimxcelPText( "-" + MAX_APPLIED_FORCE, DEFAULT_FONT, enabled ), longTick, longTickOffset );
            AimxcelPPath tick1_5 = addLabel( -MAX_APPLIED_FORCE * 0.75, dummyLabel(), shortTick, shortTickOffset );
            AimxcelPPath tick2 = addLabel( -MAX_APPLIED_FORCE * 0.5, dummyLabel(), longTick, longTickOffset );
            AimxcelPPath tick2_5 = addLabel( -MAX_APPLIED_FORCE * 0.25, dummyLabel(), shortTick, shortTickOffset );
            addLabel( 0, new EnableAimxcelPText( "0", DEFAULT_FONT, enabled ), longTick, longTickOffset );
            AimxcelPPath tick3_5 = addLabel( MAX_APPLIED_FORCE * 0.25, dummyLabel(), shortTick, shortTickOffset );
            AimxcelPPath tick4 = addLabel( MAX_APPLIED_FORCE * 0.5, dummyLabel(), longTick, longTickOffset );
            AimxcelPPath tick4_5 = addLabel( MAX_APPLIED_FORCE * 0.75, dummyLabel(), shortTick, shortTickOffset );
            AimxcelPPath tick5 = addLabel( MAX_APPLIED_FORCE, new EnableAimxcelPText( "" + MAX_APPLIED_FORCE, DEFAULT_FONT, enabled ), longTick, longTickOffset );

            //Gray out the ticks if the speed is exceeded.
            speedValue.addObserver( grayIf( tick1, LEFT_SPEED_EXCEEDED ) );
            speedValue.addObserver( grayIf( tick2, LEFT_SPEED_EXCEEDED ) );
            speedValue.addObserver( grayIf( tick4, RIGHT_SPEED_EXCEEDED ) );
            speedValue.addObserver( grayIf( tick5, RIGHT_SPEED_EXCEEDED ) );

            setTrackFillPaint( Color.white );

            //Gray out the half of the track that is unavailable
            //Note the metrics are rotated since it is an HSliderNode wrapping a VSliderNode.
            getTrackNode().addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, getTrackNode().getFullBounds().getWidth(), getTrackNode().getFullBounds().getHeight() / 2 ), Color.lightGray ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                speedValue.addObserver( new VoidFunction1<SpeedValue>() {
                    public void apply( final SpeedValue speedValue ) {
                        setVisible( speedValue == RIGHT_SPEED_EXCEEDED );
                    }
                } );
            }} );
            getTrackNode().addChild( new AimxcelPPath( new Rectangle2D.Double( 0, getTrackNode().getFullBounds().getHeight() / 2, getTrackNode().getFullBounds().getWidth(), getTrackNode().getFullBounds().getHeight() / 2 ), Color.lightGray ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                speedValue.addObserver( new VoidFunction1<SpeedValue>() {
                    public void apply( final SpeedValue speedValue ) {
                        setVisible( speedValue == LEFT_SPEED_EXCEEDED );
                    }
                } );
            }} );

            //When playing: When dropping the slider thumb, the value should go back to 0.  The user has to hold the thumb to keep applying the force
            //When paused: The user can set a value and it will stay (just as they can always set a value with the text box)
            addInputEventListener( new PBasicInputEventHandler() {
                @Override public void mouseReleased( final PInputEvent event ) {
                    if ( playing.get() ) {
                        appliedForce.set( 0.0 );
                        appliedForceSliderModel.set( 0.0 );
                    }
                }
            } );

            //Force slider should go to zero if the stack is emptied
            stack.addObserver( new VoidFunction1<List<StackableNode>>() {
                public void apply( final List<StackableNode> stackableNodes ) {
                    if ( stackableNodes.isEmpty() ) {
                        appliedForceSliderModel.set( 0.0 );
                        model.speed.set( new Some<Double>( 0.0 ) );
                        model.velocity.set( 0.0 );
                    }
                }
            } );
        }};
        VBox box = new VBox( 5, new EnableAimxcelPText( Strings.APPLIED_FORCE, DEFAULT_FONT, enabled ),
                             sliderNode,
                             new HBox( new AimxcelPText( unitsString, DEFAULT_FONT ) {/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

							{
                                 setTransparency( 0.0f );
                             }},
                                       new PSwing( new JTextField( 3 ) {
                                           /**
										 * 
										 */
										private static final long serialVersionUID = 1L;

										{
                                               setFont( DEFAULT_FONT );
                                               setText( "0" );
                                               final DecimalFormat format = new DecimalFormat( "0" );
                                               setHorizontalAlignment( JTextField.RIGHT );
                                               appliedForce.addObserver( new VoidFunction1<Double>() {
                                                   public void apply( final Double value ) {
                                                       setText( format.format( value ) );
                                                   }
                                               } );
                                               enabled.addObserver( new VoidFunction1<Boolean>() {
                                                   public void apply( final Boolean enabled ) {
                                                       setEnabled( enabled );
                                                   }
                                               } );
                                               addActionListener( new ActionListener() {
                                                   public void actionPerformed( final ActionEvent e ) {
                                                       updateValueFromText( format );
                                                   }
                                               } );
                                               addFocusListener( new FocusListener() {
                                                   public void focusGained( final FocusEvent e ) {
                                                   }

                                                   public void focusLost( final FocusEvent e ) {
                                                       updateValueFromText( format );
                                                   }
                                               } );
                                           }

                                           private void updateValueFromText( final DecimalFormat format ) {
                                               try {
                                                   final double value = MathUtil.clamp( -MAX_APPLIED_FORCE, format.parse( getText() ).doubleValue(), MAX_APPLIED_FORCE );
                                                   SimSharingManager.sendUserMessage( UserComponents.appliedForceTextField, UserComponentTypes.textField, UserActions.textFieldCommitted, ParameterSet.parameterSet( ParameterKeys.appliedForce, value ) );
                                                   appliedForce.set( value );
                                                   appliedForceSliderModel.set( appliedForce.get() );
                                               }
                                               catch ( ParseException e1 ) {
                                                   setText( format.format( appliedForce.get() ) );
                                               }
                                           }
                                       } ),
                                       new EnableAimxcelPText( unitsString, DEFAULT_FONT, enabled )
                             )
        );

        addChild( box );
    }

    private AimxcelPText dummyLabel() {
        return new AimxcelPText( "a", DEFAULT_FONT ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setTransparency( 0.0f );
        }};
    }

    private VoidFunction1<SpeedValue> grayIf( final AimxcelPPath path, final SpeedValue value ) {
        return new VoidFunction1<SpeedValue>() {
            public void apply( final SpeedValue speedValue ) {
                path.setStrokePaint( speedValue == value ? Color.gray : Color.black );
            }
        };
    }

    private class EnableAimxcelPText extends AimxcelPText {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EnableAimxcelPText( final String text, final Font font, ObservableProperty<Boolean> enabled ) {
            super( text, font );

            enabled.addObserver( new VoidFunction1<Boolean>() {
                public void apply( final Boolean enabled ) {
                    setTextPaint( enabled ? Color.black : Color.gray );
                }
            } );
        }
    }
}