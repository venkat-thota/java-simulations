 
package com.aimxcel.abclearn.core.aimxcelcore.nodes.radiobuttonstrip;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.event.DynamicCursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;


public class ToggleButtonNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final DynamicCursorHandler cursorHandler = new DynamicCursorHandler();

    //Nice shade of green to show for selected (pressed in) toggle button nodes.
    public static final Color FAINT_GREEN = new Color( 215, 237, 218 );
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color( 242, 242, 242 );

    public static final double DEFAULT_PRESS_AMOUNT_X = 4;
    public static final double DEFAULT_PRESS_AMOUNT_Y = 6;

    public ToggleButtonNode( final PNode node, final ObservableProperty<Boolean> selected, final VoidFunction0 pressed ) {
        this( node, selected, pressed, DEFAULT_BACKGROUND_COLOR, true );
    }

    public ToggleButtonNode( final PNode node, final ObservableProperty<Boolean> selected, final VoidFunction0 pressed, final Color pressedInColor, final boolean disableCursorWhenPressedIn ) {
        this( node, selected, pressed, pressedInColor, disableCursorWhenPressedIn, DEFAULT_PRESS_AMOUNT_X, DEFAULT_PRESS_AMOUNT_Y );
    }

    public ToggleButtonNode( final PNode node, final ObservableProperty<Boolean> selected, final VoidFunction0 pressed, final Color pressedInColor, final boolean disableCursorWhenPressedIn,
                             final double pressAmountX, final double pressAmountY ) {

        //We have to handle the pickability since the cursor changes, so disable on the target node
        node.setPickable( false );
        node.setChildrenPickable( false );

        final AimxcelPPath hiddenBorder = new AimxcelPPath( RectangleUtils.expand( node.getFullBounds(), pressAmountX, pressAmountY ), null );
        final RoundRectangle2D.Double shape = new RoundRectangle2D.Double( node.getFullBounds().getMinX(), node.getFullBounds().getMinY(), node.getFullBounds().getWidth(), node.getFullBounds().getHeight(), 20, 20 );
        final AimxcelPPath buttonBackground = new AimxcelPPath( shape, DEFAULT_BACKGROUND_COLOR, new BasicStroke( 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER ), null );
        final AimxcelPPath shadow = new AimxcelPPath( shape, Color.darkGray );

        selected.addObserver( new VoidFunction1<Boolean>() {
            public void apply( Boolean selected ) {
                buttonBackground.setStrokePaint( new Color( 120, 120, 120 ) );
                buttonBackground.setPaint( selected ? pressedInColor : DEFAULT_BACKGROUND_COLOR );
                node.setOffset( selected ? new Point( 0, 0 ) : new Point2D.Double( -pressAmountX, -pressAmountY ) );
                buttonBackground.setOffset( selected ? new Point( 0, 0 ) : new Point2D.Double( -pressAmountX, -pressAmountY ) );

                //If the button got pressed in, change from being a hand to an arrow so it doesn't like you can still press the button and vice versa
                if ( disableCursorWhenPressedIn ) {
                    cursorHandler.setCursor( selected ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR );
                }
            }
        } );

        addInputEventListener( cursorHandler );
        addInputEventListener( new PBasicInputEventHandler() {
            @Override public void mousePressed( PInputEvent event ) {
                pressed.apply();
            }
        } );

        addChild( hiddenBorder );
        addChild( shadow );
        addChild( buttonBackground );
        addChild( node );
        node.centerFullBoundsOnPoint( buttonBackground.getFullBounds().getCenterX(), buttonBackground.getFullBounds().getCenterY() );
    }
}