package com.aimxcel.abclearn.platetectonics.control;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.*;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;
import com.aimxcel.abclearn.platetectonics.model.PlateMotionModel;
import com.aimxcel.abclearn.platetectonics.model.PlateMotionModel.MotionType;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ChangeObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Spacer;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;
public class MotionTypeChooserPanel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Property<MotionType> motionType;
    private PlateMotionModel plateModel;

    private static final double SPACING = 10;
    private static final double ICON_WIDTH = 100;
    private static final double ICON_HEIGHT = 50;

    public MotionTypeChooserPanel( final PlateMotionModel plateModel ) {
        this.plateModel = plateModel;
        this.motionType = plateModel.motionType;

        boolean showDivergent = plateModel.allowsDivergentMotion();
        boolean showConvergent = plateModel.allowsConvergentMotion();
        boolean showTransform = plateModel.allowsTransformMotion();

        PSwing convergentButton = new PSwing( new MotionTypeChooserRadioButton( UserComponents.convergentMotion, Strings.CONVERGENT, MotionType.CONVERGENT ) );
        PSwing divergentButton = new PSwing( new MotionTypeChooserRadioButton( UserComponents.divergentMotion, Strings.DIVERGENT, MotionType.DIVERGENT ) );
        PSwing transformButton = new PSwing( new MotionTypeChooserRadioButton( UserComponents.transformMotion, Strings.TRANSFORM, MotionType.TRANSFORM ) );

        final Property<Double> x = new Property<Double>( 0.0 );

        // TODO: refactor for simplicity
        /*---------------------------------------------------------------------------*
        * convergent
        *----------------------------------------------------------------------------*/
        if ( showConvergent ) {
            convergentButton.setOffset( x.get() + ( ICON_WIDTH - convergentButton.getFullBounds().getWidth() ) / 2, ICON_HEIGHT + 10 );
            addChild( convergentButton );
            addChild( new Spacer( x.get(), 0, ICON_WIDTH, ICON_HEIGHT ) );
            addChild( new IconArrow( new Point2D.Double( 2, 0 ), new Point2D.Double( ICON_WIDTH / 2 - 5, 0 ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( x.get(), ICON_HEIGHT / 2 );
                setPaint( PlateTectonicsConstants.ARROW_CONVERGENT_FILL );
            }} );
            addChild( new IconArrow( new Point2D.Double( ICON_WIDTH - 2, 0 ), new Point2D.Double( ICON_WIDTH / 2 + 5, 0 ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( x.get(), ICON_HEIGHT / 2 );
                setPaint( PlateTectonicsConstants.ARROW_CONVERGENT_FILL );
            }} );
            x.set( x.get() + ICON_WIDTH + SPACING );
        }

        /*---------------------------------------------------------------------------*
        * divergent
        *----------------------------------------------------------------------------*/
        if ( showDivergent ) {
            divergentButton.setOffset( x.get() + ( ICON_WIDTH - divergentButton.getFullBounds().getWidth() ) / 2, ICON_HEIGHT + 10 );
            addChild( divergentButton );
            addChild( new Spacer( x.get(), 0, ICON_WIDTH, ICON_HEIGHT ) );
            addChild( new IconArrow( new Point2D.Double( ICON_WIDTH / 2 - 5, 0 ), new Point2D.Double( 2, 0 ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( x.get(), ICON_HEIGHT / 2 );
                setPaint( PlateTectonicsConstants.ARROW_DIVERGENT_FILL );
            }} );
            addChild( new IconArrow( new Point2D.Double( ICON_WIDTH / 2 + 5, 0 ), new Point2D.Double( ICON_WIDTH - 2, 0 ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( x.get(), ICON_HEIGHT / 2 );
                setPaint( PlateTectonicsConstants.ARROW_DIVERGENT_FILL );
            }} );
            x.set( x.get() + ICON_WIDTH + SPACING );
        }

        /*---------------------------------------------------------------------------*
        * transform
        *----------------------------------------------------------------------------*/
        if ( showTransform ) {
            transformButton.setOffset( x.get() + ( ICON_WIDTH - transformButton.getFullBounds().getWidth() ) / 2, ICON_HEIGHT + 10 );
            addChild( transformButton );
            addChild( new Spacer( x.get(), 0, ICON_WIDTH, ICON_HEIGHT ) );
            double transformX = x.get() + ICON_WIDTH / 2;
            final float shearFactor = -0.6f;
            final AffineTransform shearMatrix = new AffineTransform( 1, 0, shearFactor, 1, -shearFactor * ( ICON_HEIGHT / 2 ), 0 );
            addChild( new IconArrow( new Point2D.Double( transformX, ICON_HEIGHT / 2 - 5 ), new Point2D.Double( transformX, 2 ), 8 ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setPaint( PlateTectonicsConstants.ARROW_TRANSFORM_FILL );
                setTransform( shearMatrix );
            }} );
            addChild( new IconArrow( new Point2D.Double( transformX, ICON_HEIGHT / 2 + 5 ), new Point2D.Double( transformX, ICON_HEIGHT - 2 ), 8 ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setPaint( PlateTectonicsConstants.ARROW_TRANSFORM_FILL );
                setTransform( shearMatrix );
            }} );
            x.set( x.get() + ICON_WIDTH + SPACING );
        }
    }

    private static class IconArrow extends ArrowNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IconArrow( Point2D tailLocation, Point2D tipLocation ) {
            this( tailLocation, tipLocation, 14 );
        }

        public IconArrow( Point2D tailLocation, Point2D tipLocation, double headHeight ) {
            super( tailLocation, tipLocation, headHeight, 14, 8 );
            setStrokePaint( Color.BLACK );
            setStroke( new BasicStroke( 1 ) );
        }
    }

    private boolean typeEquals( MotionType myType, MotionType current ) {
        if ( current == null ) {
            // if there is no currently-chosen motion type, pick defaults like this
            if ( plateModel.allowsConvergentMotion() ) {
                return myType == MotionType.CONVERGENT;
            }
            if ( plateModel.allowsDivergentMotion() ) {
                return myType == MotionType.DIVERGENT;
            }
            return myType == MotionType.TRANSFORM;
        }
        return myType == current;
    }

    private class MotionTypeChooserRadioButton extends SimSharingJRadioButton {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private MotionTypeChooserRadioButton( IUserComponent userComponent, String title, final MotionType type ) {
            // TODO: clean up handling with this and the other mode. very complicated interactions
            super( userComponent, title );
            if ( plateModel.animationStarted.get() ) {
                setSelected( typeEquals( type, motionType.get() ) );
            }
            else {
                setSelected( typeEquals( type, plateModel.motionTypeIfStarted.get() ) );
            }

            // update the model change in the LWJGL thread
            // not using GLActionListener here because we also need to make a call in the Swing EDT
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent actionEvent ) {
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            plateModel.motionTypeIfStarted.set( type );
                        }
                    } );
                    setSelected( true );
                }
            } );

            // update this control in the Swing thread
            plateModel.motionTypeIfStarted.addObserver( new ChangeObserver<MotionType>() {
                public void update( final MotionType newValue, MotionType oldValue ) {
                    // compute the value in the LWJGL thread
                    final boolean set = typeEquals( type, newValue );

                    // and change the component view in the Swing EDT
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setSelected( set );
                        }
                    } );
                }
            } );
            plateModel.animationStarted.addObserver( new SimpleObserver() {
                public void update() {
                    final boolean enabled = !plateModel.animationStarted.get();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            setEnabled( enabled );
                        }
                    } );
                }
            } );
        }
    }
}
