package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPText;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


class ImageButtonNodeWithText extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BufferedImage hover;
    private final PImage imageNode;

    public ImageButtonNodeWithText( final IUserComponent component, final BufferedImage up, final BufferedImage hover, final BufferedImage pressed, final String text, final VoidFunction0 effect ) {
        this.hover = hover;
        imageNode = new PImage( up );
        addChild( imageNode );
        final AimxcelPText textNode = new AimxcelPText( text );
        textNode.scale( up.getWidth() / textNode.getFullWidth() * 0.65 );
        addChild( textNode );

        textNode.centerFullBoundsOnPoint( imageNode.getFullBounds().getCenter2D() );

        //account for shadow
        textNode.translate( -4 / textNode.getScale(), -4 / textNode.getScale() );
        addInputEventListener( new CursorHandler() );
        addInputEventListener( new PBasicInputEventHandler() {
            @Override public void mouseEntered( final PInputEvent event ) {
                super.mouseEntered( event );
                hover();
            }

            @Override public void mouseExited( final PInputEvent event ) {
                imageNode.setImage( up );
            }

            @Override public void mousePressed( final PInputEvent event ) {
                super.mousePressed( event );
                imageNode.setImage( pressed );
            }

            @Override public void mouseReleased( final PInputEvent event ) {
                super.mouseReleased( event );

                SimSharingManager.sendButtonPressed( component );
                effect.apply();
                imageNode.setImage( up );
            }
        } );
    }

    //Need to show the highlight any time the mouse is over the object, even if the button was shown when the mouse was already there.
    public void hover() { imageNode.setImage( hover ); }
}