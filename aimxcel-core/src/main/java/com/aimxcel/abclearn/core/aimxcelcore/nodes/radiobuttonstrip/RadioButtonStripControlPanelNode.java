 
package com.aimxcel.abclearn.core.aimxcelcore.nodes.radiobuttonstrip;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ControlPanelNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class RadioButtonStripControlPanelNode<T> extends ControlPanelNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Element<T> {
        public final PNode node;
        public final T value;
        public final IUserComponent component;

        public Element( final PNode node, final T value, final IUserComponent component ) {
            this.node = node;
            this.value = value;
            this.component = component;
        }
    }

    public RadioButtonStripControlPanelNode( SettableProperty<T> selected, List<Element<T>> elements, int buttonPadding ) {
        this( selected, elements, buttonPadding, new Color( 230, 230, 230 ), new BasicStroke( 2 ), new Color( 102, 102, 102 ), ControlPanelNode.DEFAULT_INSET );
    }

    public RadioButtonStripControlPanelNode( SettableProperty<T> selected, List<Element<T>> elements, int buttonPadding,
                                             Color backgroundColor, BasicStroke outlineStroke, Color strokeColor, int controlPanelInset ) {
        super( new RadioButtonStripNode<T>( selected, elements, buttonPadding ), backgroundColor, outlineStroke, strokeColor, controlPanelInset );
    }

    public RadioButtonStripControlPanelNode( SettableProperty<T> selected, List<Element<T>> elements, int buttonPadding,
                                             Color backgroundColor, BasicStroke outlineStroke, Color strokeColor, int controlPanelInset,
                                             final double pressAmountX, final double pressAmountY ) {
        super( new RadioButtonStripNode<T>( selected, elements, buttonPadding, pressAmountX, pressAmountY ), backgroundColor, outlineStroke, strokeColor, controlPanelInset );
    }

    //Inner class enables us to wrap the parent in ControlPanelNode.  Public in case clients want to use it without the control panel exterior decoration.
    public static class RadioButtonStripNode<T> extends PNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RadioButtonStripNode( final SettableProperty<T> selected, final List<Element<T>> elements, int padding ) {
            this( selected, elements, padding, ToggleButtonNode.DEFAULT_PRESS_AMOUNT_X, ToggleButtonNode.DEFAULT_PRESS_AMOUNT_Y );
        }

        public RadioButtonStripNode( final SettableProperty<T> selected, final List<Element<T>> elements, int padding, final double pressAmountX, final double pressAmountY ) {

            double maxWidth = 0;
            double maxHeight = 0;

            for ( Element<T> element : elements ) {
                PNode pNode = element.node;
                if ( pNode.getFullBounds().getWidth() > maxWidth ) {
                    maxWidth = pNode.getFullBounds().getWidth();
                }
                if ( pNode.getFullBounds().getHeight() > maxHeight ) {
                    maxHeight = pNode.getFullBounds().getHeight();
                }
            }

            final double finalMaxHeight = Math.max( maxWidth, maxHeight ) + padding;
            final double finalMaxWidth = finalMaxHeight;
            final HBox representationLayer = new HBox( 10 ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                for ( final Element<T> element : elements ) {

                    PNode button = new AimxcelPPath( new RoundRectangle2D.Double( -2, -2, finalMaxWidth + 4, finalMaxHeight + 4, 20, 20 ), null ) {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{

                        final ZeroOffsetNode theNode = new ZeroOffsetNode( element.node ) {/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

						{
                            final Point2D.Double origOffset = new Point2D.Double( finalMaxWidth / 2 - getFullWidth() / 2, finalMaxHeight / 2 - getFullHeight() / 2 );
                            setOffset( origOffset );
                        }};
                        addChild( theNode );
                    }};

                    addChild( new ToggleButtonNode( button, selected.valueEquals( element.value ), new VoidFunction0() {
                        public void apply() {
                            SimSharingManager.sendUserMessage( element.component, UserComponentTypes.button, UserActions.pressed );
                            selected.set( element.value );
                        }
                    }, ToggleButtonNode.DEFAULT_BACKGROUND_COLOR, true, pressAmountX, pressAmountY ) );
                }
            }};
            addChild( representationLayer );
        }
    }
}