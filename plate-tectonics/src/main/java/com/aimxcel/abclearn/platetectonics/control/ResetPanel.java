package com.aimxcel.abclearn.platetectonics.control;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.tabs.PlateMotionTab;
import com.aimxcel.abclearn.platetectonics.tabs.PlateTectonicsTab;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Spacer;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.TextButtonNode;
import com.aimxcel.abclearn.lwjgl.utils.GLActionListener;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class ResetPanel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResetPanel( final PlateTectonicsTab tab, final Runnable resetAll ) {

        // maxWidth updated every time something is added, so we can position things nicely at the end
        final Property<Double> maxWidth = new Property<Double>( 0.0 );

        // current y to add things at
        final Property<Double> y = new Property<Double>( 0.0 );

        final boolean isMotionTab = tab instanceof PlateMotionTab;

        PNode rewindNode = null;
        PNode newCrustNode = null;
        if ( isMotionTab ) {
            final PlateMotionTab motionTab = (PlateMotionTab) tab;
            rewindNode = new TextButtonNode( Strings.REWIND, PlateTectonicsConstants.BUTTON_FONT, PlateTectonicsConstants.BUTTON_COLOR ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setUserComponent( UserComponents.rewindButton );
                setOffset( 0, y.get() + 15 );
                y.set( getFullBounds().getMaxY() );
                maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
                motionTab.getPlateMotionModel().animationStarted.addObserver( new SimpleObserver() {
                    public void update() {
                        setEnabled( motionTab.getPlateMotionModel().animationStarted.get() );
                        repaint();
                    }
                } );

                // run the rewind in the LWJGL thread
                addActionListener( new GLActionListener( new Runnable() {
                    public void run() {
                        motionTab.getPlateMotionModel().rewind();
                        motionTab.getClock().pause();
                        motionTab.getClock().resetSimulationTime();
                    }
                } ) );
            }};
            addChild( rewindNode );

            newCrustNode = new TextButtonNode( Strings.NEW_CRUST, PlateTectonicsConstants.BUTTON_FONT, PlateTectonicsConstants.BUTTON_COLOR ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setUserComponent( PlateTectonicsSimSharing.UserComponents.newCrustButton );
                setOffset( 0, y.get() + 15 );
                y.set( getFullBounds().getMaxY() );
                maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
                motionTab.getPlateMotionModel().hasAnyPlates.addObserver( new SimpleObserver() {
                    public void update() {
                        setEnabled( motionTab.getPlateMotionModel().hasAnyPlates.get() );
                        repaint();
                    }
                } );

                // run the rewind in the LWJGL thread
                addActionListener( new GLActionListener( new Runnable() {
                    public void run() {
                        motionTab.newCrust();
                    }
                } ) );
            }};
            addChild( newCrustNode );
        }

        PNode resetAllNode = new TextButtonNode( Strings.RESET_ALL, PlateTectonicsConstants.BUTTON_FONT, PlateTectonicsConstants.BUTTON_COLOR ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setUserComponent( UserComponents.resetAllButton );
            setOffset( 0, y.get() + 15 );
            y.set( getFullBounds().getMaxY() );
            maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );

            // run the reset in the LWJGL thread
            addActionListener( new GLActionListener( resetAll ) );
        }};
        addChild( resetAllNode );

        // horizontally center the buttons, if applicable
        resetAllNode.setOffset( ( maxWidth.get() - resetAllNode.getFullBounds().getWidth() ) / 2, resetAllNode.getYOffset() );
        if ( isMotionTab ) {
            rewindNode.setOffset( ( maxWidth.get() - rewindNode.getFullBounds().getWidth() ) / 2, rewindNode.getYOffset() );
            newCrustNode.setOffset( ( maxWidth.get() - newCrustNode.getFullBounds().getWidth() ) / 2, newCrustNode.getYOffset() );
        }

        // this prevents panel resizing when the button bounds change (like when they are pressed)
        addChild( new Spacer( 0, y.get(), 1, 1 ) );
    }
}
