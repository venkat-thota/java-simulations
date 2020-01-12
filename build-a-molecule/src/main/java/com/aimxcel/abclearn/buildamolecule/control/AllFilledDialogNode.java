package com.aimxcel.abclearn.buildamolecule.control;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants.MODEL_VIEW_TRANSFORM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.FaceNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.TextButtonNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dextra.swing.SwingLayoutNode;


public class AllFilledDialogNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AllFilledDialogNode( PBounds availablePlayAreaBounds, final VoidFunction0 regenerateCallback ) {
        PNode background = new PNode();
        addChild( background );
        addChild( new SwingLayoutNode( new GridBagLayout() ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            // smiley face
            addChild( new FaceNode( 120 ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                          smile();
                      }},
                      new GridBagConstraints() {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                          gridx = 0;
                          gridy = 0;
                      }}
            );

            // text explaining all collection boxes are filled
            addChild( new PText( BuildAMoleculeStrings.COLLECTION_ALL_FILLED ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                          setFont( new AimxcelFont( 20, true ) );
                      }},
                      new GridBagConstraints() {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                          gridx = 0;
                          gridy = 1;
                          insets = new Insets( 10, 0, 0, 0 );
                      }}
            );

            // button to generate a new kit/collection
            addChild( new TextButtonNode( BuildAMoleculeStrings.COLLECTION_TRY_WITH_DIFFERENT_MOLECULES, new AimxcelFont( 18, true ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                          setBackground( Color.ORANGE );
                          addActionListener( new ActionListener() {
                              public void actionPerformed( ActionEvent e ) {
                                  regenerateCallback.apply();
                                  AllFilledDialogNode.this.setVisible( false );
                              }
                          } );
                      }},
                      new GridBagConstraints() {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                          gridx = 0;
                          gridy = 2;
                          insets = new Insets( 10, 0, 0, 0 );
                      }}
            );
        }} );

        float padding = 10;

        PPath backgroundNode = AimxcelPPath.createRectangle( (float) getFullBounds().getX() - padding, (float) getFullBounds().getY() - padding, (float) getFullBounds().getWidth() + 2 * padding, (float) getFullBounds().getHeight() + 2 * padding );
        backgroundNode.setPaint( BuildAMoleculeConstants.COMPLETE_BACKGROUND_COLOR );
        background.addChild( backgroundNode );

        Rectangle2D playAreaViewBounds = MODEL_VIEW_TRANSFORM.modelToView( availablePlayAreaBounds ).getBounds2D();
        centerFullBoundsOnPoint( playAreaViewBounds.getCenterX(), playAreaViewBounds.getCenterY() );
    }
}
