package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.model.BoreholeDrill;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.ImageButtonNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class BoreholeDrillNode extends AbstractToolNode {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// can the drill be dragged while its button is pressed?
    private static final boolean ALLOW_DRAGGING_WHILE_BUTTON_IS_PRESSED = true;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public BoreholeDrillNode( final BoreholeDrill boreholeDrill, GlaciersModelViewTransform mvt, TrashCanDelegate trashCan ) {
        super( boreholeDrill, mvt, trashCan );
        
        PNode drillNode = new DrillNode();
        addChild( drillNode );
        
        ButtonNode buttonNode = new ButtonNode();
        addChild( buttonNode );
        
        /*
         * NOTE! These offsets were tweaked to line up with specific images.
         * If you change the images, you will need to re-tweak these values.
         */
        drillNode.setOffset( -8, -drillNode.getFullBoundsReference().getHeight() ); // tip of drill bit
        buttonNode.setOffset( -11, -drillNode.getFullBoundsReference().getHeight() + 8 );
        
        buttonNode.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                boreholeDrill.drill();
            }
        });
        
        if ( !ALLOW_DRAGGING_WHILE_BUTTON_IS_PRESSED ) {
            // disable dragging while the button is pressed
            buttonNode.addInputEventListener( new PBasicInputEventHandler() {

                public void mousePressed( PInputEvent event ) {
                    setDraggingEnabled( false );
                }

                public void mouseReleased( PInputEvent event ) {
                    setDraggingEnabled( true );
                }
            } );
        }
    }
    
    //----------------------------------------------------------------------------
    // Inner classes
    //----------------------------------------------------------------------------
    
    private static class DrillNode extends PComposite {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrillNode() {
            super();
            PImage imageNode = new PImage( GlaciersImages.BOREHOLE_DRILL );
            addChild( imageNode );
        }
    }
    
    private static class ButtonNode extends ImageButtonNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ButtonNode() {
            super( GlaciersImages.BOREHOLE_DRILL_OFF_BUTTON, GlaciersImages.BOREHOLE_DRILL_ON_BUTTON );
        }
    }
    
    //----------------------------------------------------------------------------
    // Image icon
    //----------------------------------------------------------------------------
    
    public static Image createImage() {
        // NOTE: icon does not show button on drill
        PImage imageNode = new PImage( GlaciersImages.BOREHOLE_DRILL );
        imageNode.scale( 0.65 );
        return imageNode.toImage();
    }
}
