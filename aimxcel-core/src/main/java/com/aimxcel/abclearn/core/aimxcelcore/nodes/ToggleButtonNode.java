 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Cursor;
import java.awt.Image;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.event.DynamicCursorHandler;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class ToggleButtonNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PImage imageNode;
    private final DynamicCursorHandler cursorHandler;

    // Button that will always be enabled.
    public ToggleButtonNode( IUserComponent userComponent, Property<Boolean> onProperty, final Image onImage, final Image offImage ) {
        this( userComponent, onProperty, onImage, offImage, new Property<Boolean>( true ), onImage, offImage );
    }

    public ToggleButtonNode( final IUserComponent userComponent,
                             final Property<Boolean> onProperty, final Image onImage, final Image offImage,
                             final Property<Boolean> enabledProperty, final Image onDisabledImage, final Image offDisabledImage ) {

        cursorHandler = new DynamicCursorHandler();

        imageNode = new PImage();
        addChild( imageNode );

        addInputEventListener( new PBasicInputEventHandler() {
            @Override public void mousePressed( PInputEvent event ) {
                if ( enabledProperty.get() ) {
                    SimSharingManager.sendUserMessage( userComponent, UserComponentTypes.button, UserActions.pressed, getParameterSet() );
                    onProperty.set( !onProperty.get() );
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

    // Override this to add parameters
    protected ParameterSet getParameterSet() {
        return new ParameterSet();
    }
}