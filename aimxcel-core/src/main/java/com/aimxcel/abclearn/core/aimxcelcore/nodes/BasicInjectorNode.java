
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;


public class BasicInjectorNode extends InjectorNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
     * Constructs a particle injection node.
     *
     * @param mvt           - Model-view transform for relating view space to model space.
     * @param rotationAngle - Angle of rotation for the injection bulb.
     */
    public BasicInjectorNode( double rotationAngle, final SimpleObserver inject ) {
        super( rotationAngle, inject );
        buttonImageNode.addInputEventListener( new CursorHandler() );
        buttonImageNode.addInputEventListener( new PBasicInputEventHandler() {
            @Override
            public void mousePressed( PInputEvent event ) {
                buttonImageNode.setImage( pressedButtonImage );
                inject.update();
            }

            @Override
            public void mouseReleased( PInputEvent event ) {
                buttonImageNode.setImage( unpressedButtonImage );
            }
        } );
    }
}