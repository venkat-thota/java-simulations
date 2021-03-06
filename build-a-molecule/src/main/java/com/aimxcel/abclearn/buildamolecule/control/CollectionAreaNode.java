package com.aimxcel.abclearn.buildamolecule.control;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.control.GeneralLayoutNode.HorizontalAlignMethod.Align;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.KitCollection;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class CollectionAreaNode extends GeneralLayoutNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CollectionBoxNode> collectionBoxNodes = new LinkedList<CollectionBoxNode>();

    /**
     * Creates a collection area (with collection boxes)
     *
     * @param collection           Our model
     * @param singleCollectionMode Whether we should use single or multiple molecule collection boxes
     * @param toModelBounds        Function to convert piccolo node bounds to model bounds
     */
    public CollectionAreaNode( final KitCollection collection, final boolean singleCollectionMode, Function1<PNode, Rectangle2D> toModelBounds ) {
        LayoutMethod method = new CompositeLayoutMethod( new VerticalLayoutMethod(), new HorizontalAlignMethod( Align.Centered ) );

        final double maximumBoxWidth = singleCollectionMode ? SingleCollectionBoxNode.getMaxWidth() : MultipleCollectionBoxNode.getMaxWidth();
        final double maximumBoxHeight = singleCollectionMode ? SingleCollectionBoxNode.getMaxHeight() : MultipleCollectionBoxNode.getMaxHeight();

        // add nodes for all of our collection boxes.
        for ( CollectionBox collectionBox : collection.getCollectionBoxes() ) {
            final CollectionBoxNode collectionBoxNode = singleCollectionMode
                                                        ? new SingleCollectionBoxNode( collectionBox, toModelBounds )
                                                        : new MultipleCollectionBoxNode( collectionBox, toModelBounds );
            collectionBoxNodes.add( collectionBoxNode );

            // TODO: can we fix this up somehow to be better? easier way to force height?
            // center box horizontally and put at bottom vertically in our holder
            final VoidFunction0 layoutBoxNode = new VoidFunction0() {
                public void apply() {
                    // compute correct offsets
                    double offsetX = ( maximumBoxWidth - collectionBoxNode.getFullBounds().getWidth() ) / 2;
                    double offsetY = maximumBoxHeight - collectionBoxNode.getFullBounds().getHeight();

                    // only apply these if they are different. otherwise we run into infinite recursion
                    if ( collectionBoxNode.getXOffset() != offsetX || collectionBoxNode.getYOffset() != offsetY ) {
                        collectionBoxNode.setOffset( offsetX, offsetY );
                    }
                }
            };
            layoutBoxNode.apply();

            // also position if its size changes in the future
            collectionBoxNode.addPropertyChangeListener( new PropertyChangeListener() {
                public void propertyChange( PropertyChangeEvent evt ) {
                    layoutBoxNode.apply();
                }
            } );

            // enforce consistent bounds of the maximum size. reason: we don't want switching between collections to alter the positions of the collection boxes
            PNode collectionBoxHolder = new PNode() {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
                    // invisible background. enforces SwingLayoutNode's correct positioning
                    addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, maximumBoxWidth, maximumBoxHeight ) ) {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                        setStroke( null ); // don't add any sort of border to mess up the bounds
                        setVisible( false );
                    }} );

                    addChild( collectionBoxNode );
                }
            };
            addChild( collectionBoxHolder, method, 0, 0, 15, 0 );
        }

        final HTMLImageButtonNode resetCollectionButton = new HTMLImageButtonNode( BuildAMoleculeStrings.RESET_COLLECTION, Color.ORANGE ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
                setUserComponent( UserComponent.resetCollection );

                // when clicked, empty collection boxes
                addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent e ) {
                        for ( CollectionBox box : collection.getCollectionBoxes() ) {
                            box.clear();
                        }
                        for ( Kit kit : collection.getKits() ) {
                            kit.resetKit();
                        }
                    }
                } );

                // when any collection box quantity changes, re-update our visibility
                for ( CollectionBox box : collection.getCollectionBoxes() ) {
                    box.quantity.addObserver( new SimpleObserver() {
                        public void update() {
                            updateEnabled();
                        }
                    } );
                }
            }

            public void updateEnabled() {
                boolean enabled = false;
                for ( CollectionBox box : collection.getCollectionBoxes() ) {
                    if ( box.quantity.get() > 0 ) {
                        enabled = true;
                    }
                }
                setEnabled( enabled );
            }
        };

        // add the reset collection button, but with an invisible bottom layer so that when clicked its size won't change
        addChild( new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
                      addChild( new AimxcelPPath( resetCollectionButton.getFullBounds() ) {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                          setStroke( new BasicStroke( 2 ) ); // a stroke, so that the stroke around the button won't cause a size change
                          setVisible( false );
                      }} );
                      addChild( resetCollectionButton );
                  }}, method );
    }

    public void updateCollectionBoxLocations() {
        for ( CollectionBoxNode collectionBoxNode : collectionBoxNodes ) {
            collectionBoxNode.updateLocation();
        }
    }
}
