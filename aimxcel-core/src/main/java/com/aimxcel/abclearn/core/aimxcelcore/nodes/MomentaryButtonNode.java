
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelResources;
import com.aimxcel.abclearn.core.aimxcelcore.event.DynamicCursorHandler;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class MomentaryButtonNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PImage imageNode;
    private final DynamicCursorHandler cursorHandler;

    // Button that will always be enabled.
    public MomentaryButtonNode( IUserComponent userComponent, Property<Boolean> onProperty, final Image onImage, final Image offImage ) {
        this( userComponent, onProperty, onImage, offImage, new Property<Boolean>( true ), onImage, offImage );
    }

    public MomentaryButtonNode( final IUserComponent userComponent,
                                final Property<Boolean> onProperty, final Image onImage, final Image offImage,
                                final Property<Boolean> enabledProperty, final Image onDisabledImage, final Image offDisabledImage ) {

        cursorHandler = new DynamicCursorHandler();

        imageNode = new PImage();
        addChild( imageNode );

        addInputEventListener( new PBasicInputEventHandler() {

            private final IUserComponentType userComponentType = UserComponentTypes.button;

            @Override public void mousePressed( PInputEvent event ) {
                if ( enabledProperty.get() ) {
                    SimSharingManager.sendUserMessage( userComponent, userComponentType, UserActions.pressed );
                    onProperty.set( true );
                }
            }

            @Override public void mouseReleased( PInputEvent event ) {
                if ( enabledProperty.get() ) {
                    SimSharingManager.sendUserMessage( userComponent, userComponentType, UserActions.released );
                    onProperty.set( false );
                }
            }
        } );

        onProperty.addObserver( new SimpleObserver() {
            public void update() {
                if ( enabledProperty.get() ) {
                    imageNode.setImage( onProperty.get() ? onImage : offImage );
                }
                else {
                    imageNode.setImage( onProperty.get() ? onDisabledImage : offDisabledImage );
                }
            }
        } );

        enabledProperty.addObserver( new SimpleObserver() {
            public void update() {
                if ( enabledProperty.get() ) {
                    imageNode.setImage( onProperty.get() ? onImage : offImage );
                    cursorHandler.setCursor( Cursor.HAND_CURSOR );
                }
                else {
                    imageNode.setImage( onProperty.get() ? onDisabledImage : offDisabledImage );
                    cursorHandler.setCursor( Cursor.DEFAULT_CURSOR );
                }
            }
        } );

        addInputEventListener( cursorHandler );
    }

    // test
    public static void main( String[] args ) {

        Property<Boolean> buttonOn = new Property<Boolean>( false ) {{
            addObserver( new VoidFunction1<Boolean>() {
                public void apply( Boolean buttonOn ) {
                    System.out.println( "buttonOn = " + buttonOn );
                }
            } );
        }};

        Property<Boolean> buttonEnabled = new Property<Boolean>( true ){{
            addObserver( new VoidFunction1<Boolean>() {
                public void apply( Boolean buttonEnabled ) {
                    System.out.println( "buttonEnabled = " + buttonEnabled );
                }
            } );
        }};

        PNode button = new MomentaryButtonNode( new UserComponent( "testButton"),
                                                buttonOn, CoreAimxcelResources.getImage( "button_pressed.png" ), CoreAimxcelResources.getImage( "button_unpressed.png" ),
                                                buttonEnabled, CoreAimxcelResources.getImage( "button_pressed_disabled.png" ), CoreAimxcelResources.getImage( "button_unpressed_disabled.png" ));
        PCanvas canvas = new PCanvas();
        canvas.setPreferredSize( new Dimension( 500, 500 ) );
        canvas.getLayer().addChild( button );

        JFrame frame = new JFrame();
        frame.setContentPane( canvas );
        frame.pack();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}