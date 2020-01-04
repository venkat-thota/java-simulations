package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.DoubleGeneralPath;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;


public abstract class NextPreviousNavigationNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// what directions are currently able to be followed
    public final Property<Boolean> hasNext = new Property<Boolean>( false );
    public final Property<Boolean> hasPrevious = new Property<Boolean>( false );

    private static final double ARROW_PADDING = 8;

    public NextPreviousNavigationNode( PNode centerNode,
                                       final Color arrowColor,
                                       final Color arrowStrokeColor,
                                       final double arrowWidth,
                                       final double arrowHeight ) {

        /*---------------------------------------------------------------------------*
        * previous
        *----------------------------------------------------------------------------*/

        final AimxcelPPath previousKitNode = new AimxcelPPath( new DoubleGeneralPath() {{
            // triangle pointing to the left
            moveTo( 0, arrowHeight / 2 );
            lineTo( arrowWidth, 0 );
            lineTo( arrowWidth, arrowHeight );
            closePath();
        }}.getGeneralPath() ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( arrowColor );
            setStrokePaint( arrowStrokeColor );
            addInputEventListener( new CursorHandler() {
                @Override
                public void mouseClicked( PInputEvent event ) {
                    if ( hasPrevious.get() ) {
                        previous();
                    }
                }
            } );
            hasPrevious.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( hasPrevious.get() );
                }
            } );
        }};
        addChild( previousKitNode );

        /*---------------------------------------------------------------------------*
        * center
        *----------------------------------------------------------------------------*/

        addChild( centerNode );

        /*---------------------------------------------------------------------------*
        * next
        *----------------------------------------------------------------------------*/

        final AimxcelPPath nextKitNode = new AimxcelPPath( new DoubleGeneralPath() {{
            // triangle pointing to the right
            moveTo( arrowWidth, arrowHeight / 2 );
            lineTo( 0, 0 );
            lineTo( 0, arrowHeight );
            closePath();
        }}.getGeneralPath() ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( arrowColor );
            setStrokePaint( arrowStrokeColor );
            addInputEventListener( new CursorHandler() {
                @Override
                public void mouseClicked( PInputEvent event ) {
                    if ( hasNext.get() ) {
                        next();
                    }
                }
            } );
            hasNext.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( hasNext.get() );
                }
            } );
        }};
        addChild( nextKitNode );

        /*---------------------------------------------------------------------------*
        * positioning
        *----------------------------------------------------------------------------*/

        double maxHeight = Math.max( arrowHeight, centerNode.getFullBounds().getHeight() );

        previousKitNode.setOffset( 0, ( maxHeight - arrowHeight ) / 2 );
        centerNode.setOffset( arrowWidth + ARROW_PADDING, ( maxHeight - centerNode.getFullBounds().getHeight() ) / 2 );
        nextKitNode.setOffset( centerNode.getFullBounds().getMaxX() + ARROW_PADDING, ( maxHeight - arrowHeight ) / 2 );
    }

    protected abstract void next();

    protected abstract void previous();
}
