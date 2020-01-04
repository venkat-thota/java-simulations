
package com.aimxcel.abclearn.core.aimxcelcore.nodes.kit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.Parameter;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import edu.umd.cs.piccolo.PNode;


class KitControlNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Convenience constructor that uses orange for the buttons
    public KitControlNode( IUserComponent parentKitUserComponent, final int numKits, final Property<Integer> selectedKit, Option<PNode> titleNode, double inset ) {
        this( parentKitUserComponent, numKits, selectedKit, titleNode, inset, Color.orange );
    }

    /**
     * Creates a node that contains back and forward buttons for use in a KitSelectionNode.
     *
     * @param parentKitUserComponent
     * @param numKits                the number of kits that are available, for purposes of hiding/showing the "next" button
     * @param selectedKit            model for which kit is selected, this control sets and observes the value
     * @param titleNode              optional title to be displayed between the buttons
     * @param inset                  space between the arrows and the title (or 2x inset = distance between arrows if no title)
     * @param buttonColor            main color to use for creating button color scheme
     */
    public KitControlNode( final IUserComponent parentKitUserComponent, final int numKits, final Property<Integer> selectedKit, Option<PNode> titleNode, double inset, Color buttonColor ) {
        //Buttons for scrolling previous/next
        //Place the kit "previous" and "next" buttons above the kit to save horizontal space.  In Sugar and Salt Solution, they are moved up next to the title
        final PNode nextButton = new NextKitButton( buttonColor ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    final int nextKit = selectedKit.get() + 1;
                    SimSharingManager.sendUserMessage( UserComponentChain.chain( parentKitUserComponent, UserComponents.nextButton ),
                                                       UserComponentTypes.button,
                                                       UserActions.pressed,
                                                       new ParameterSet( new Parameter( ParameterKeys.selectedKit, nextKit ) ) );
                    selectedKit.set( nextKit );
                }
            } );
            selectedKit.addObserver( new VoidFunction1<Integer>() {
                public void apply( Integer integer ) {
                    setEnabled( selectedKit.get() < numKits - 1 );
                }
            } );
        }};
        addChild( nextButton );

        PreviousKitButton previousButton = new PreviousKitButton( buttonColor ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{

            //Make sure the previous and next buttons don't overlap, useful to handle long i18n strings
            if ( getFullBounds().getMaxX() > nextButton.getFullBounds().getMinX() ) {
                setOffset( nextButton.getFullBounds().getMinX() - 2, getOffset().getY() );
            }
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    final int previousKit = selectedKit.get() - 1;
                    SimSharingManager.sendUserMessage( UserComponentChain.chain( parentKitUserComponent, UserComponents.previousButton ),
                                                       UserComponentTypes.button,
                                                       UserActions.pressed,
                                                       new ParameterSet( new Parameter( ParameterKeys.selectedKit, previousKit ) ) );
                    selectedKit.set( previousKit );
                }
            } );
            selectedKit.addObserver( new VoidFunction1<Integer>() {
                public void apply( Integer integer ) {
                    setEnabled( selectedKit.get() > 0 );
                }
            } );
        }};
        addChild( previousButton );
        if ( titleNode.isSome() ) {
            PNode title = titleNode.get();
            addChild( title );
            title.setOffset( previousButton.getFullBounds().getMaxX() + inset, previousButton.getFullBounds().getCenterY() - title.getFullBounds().getHeight() / 2 );
            nextButton.setOffset( title.getFullBounds().getMaxX() + inset, 0 );
        }
        else {
            nextButton.setOffset( previousButton.getFullBounds().getMaxX() + inset * 2, 0 );
        }

        //If there is only one kit, show the title but not the radio buttons.  Leave them in the scene graph for keeping layout consistent.
        if ( numKits == 1 ) {
            previousButton.setVisible( false );
            previousButton.setPickable( false );
            previousButton.setChildrenPickable( false );

            nextButton.setVisible( false );
            nextButton.setPickable( false );
            nextButton.setChildrenPickable( false );
        }
    }
}