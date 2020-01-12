package com.aimxcel.abclearn.buildamolecule.view;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeResources;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class ScissorsNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PImage scissorsOpenImage;
    private PImage scissorsClosedImage;

    public ScissorsNode() {
        scissorsOpenImage = new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_SCISSORS_ICON ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            centerFullBoundsOnPoint( 0, 0 );
        }};
        addChild( scissorsOpenImage );
        scissorsClosedImage = new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_SCISSORS_CLOSED_ICON ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            centerFullBoundsOnPoint( 0, 0 );
        }};
        addChild( scissorsClosedImage );

        // default to "open"
        scissorsClosedImage.setVisible( false );
    }

    public void setClosed( boolean closed ) {
        scissorsOpenImage.setVisible( !closed );
        scissorsClosedImage.setVisible( closed );
    }

    @Override public void setPickable( boolean isPickable ) {
        super.setPickable( isPickable );
        scissorsOpenImage.setPickable( isPickable );
        scissorsClosedImage.setPickable( isPickable );
    }
}
