package com.aimxcel.abclearn.forcesandmotionbasics.common;

import java.awt.Font;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;


public class AbstractForcesAndMotionBasicsCanvas extends AimxcelPCanvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Font used for most of the labels and controls
    public static final Font DEFAULT_FONT = new AimxcelFont( 17, true );

    //Stage where nodes are added and scaled up and down
    protected final PNode rootNode;

    //Size for the stage, should have the right aspect ratio since it will always be visible
    //The dimension was determined by running on Windows and inspecting the dimension of the canvas after menubar and tabs are added
    public static final PDimension STAGE_SIZE = new PDimension( 1008, 680 );

    //Default inset between edges, etc.
    public static final double INSET = 10;

    protected AbstractForcesAndMotionBasicsCanvas() {

        setWorldTransformStrategy( new CenteredStage( this, STAGE_SIZE ) );

        // Root of our scene graph
        rootNode = new PNode();
        addWorldChild( rootNode );

        setBorder( null );
    }

    protected void addChild( PNode node ) { rootNode.addChild( node ); }
}