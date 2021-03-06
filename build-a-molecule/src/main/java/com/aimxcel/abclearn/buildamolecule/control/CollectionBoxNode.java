package com.aimxcel.abclearn.buildamolecule.control;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.control.GeneralLayoutNode.HorizontalAlignMethod.Align;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox.Adapter;
import com.aimxcel.abclearn.buildamolecule.view.view3d.JmolDialogProperty;
import com.aimxcel.abclearn.buildamolecule.view.view3d.ShowMolecule3DButtonNode;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

public class CollectionBoxNode extends GeneralLayoutNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CollectionBox box;
    private final PNode boxNode = new PNode();
    private final AimxcelPPath blackBox;
    private final PNode moleculeLayer = new PNode();
    private final List<PNode> moleculeNodes = new LinkedList<PNode>();

    // show 3d elements
    private final JmolDialogProperty dialog = new JmolDialogProperty();

    // stores nodes for each molecule
    private final Map<Molecule, PNode> moleculeNodeMap = new HashMap<Molecule, PNode>();

    private static final double MOLECULE_PADDING = 5;
    private static final double BLACK_BOX_PADDING_FOR_3D = 10;
    private Timer blinkTimer = null;
    private double button3dWidth;
    private int headerCount = 0;

    private final LayoutMethod method = new CompositeLayoutMethod( new VerticalLayoutMethod(), new HorizontalAlignMethod( Align.Centered ) );

    private SimpleObserver locationUpdateObserver;

    public CollectionBoxNode( final CollectionBox box, final Function1<PNode, Rectangle2D> toModelBounds ) {
        this.box = box;

        blackBox = new AimxcelPPath( new Rectangle2D.Double( 0, 0, 160, 50 ), BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_BACKGROUND ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final AimxcelPPath reference = this;
            locationUpdateObserver = new SimpleObserver() {
                public void update() {
                    // we need to pass the collection box model coordinates, but here we have relative piccolo coordinates
                    box.setDropBounds( toModelBounds.apply( reference ) );
                }
            };

            // create our show 3D button, and have it change visibility based on the box quantity
            PNode show3dButton = new ShowMolecule3DButtonNode( dialog, box.getMoleculeType() ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                box.addListener( new Adapter() {
                    {
                        // update initial visibility
                        updateVisibility();
                    }

                    private void updateVisibility() {
                        setVisible( box.quantity.get() > 0 );
                    }

                    @Override public void onAddedMolecule( Molecule molecule ) {
                        updateVisibility();
                    }

                    @Override public void onRemovedMolecule( Molecule molecule ) {
                        updateVisibility();
                    }
                } );
            }};
            addChild( show3dButton );
            show3dButton.centerFullBoundsOnPoint(
                    getFullBounds().getWidth() - BLACK_BOX_PADDING_FOR_3D - show3dButton.getFullBounds().getWidth() / 2,
                    getFullBounds().getHeight() / 2
            );
            button3dWidth = show3dButton.getFullBounds().getWidth();
        }};
        boxNode.addChild( blackBox );
        boxNode.addChild( moleculeLayer );
        updateBoxGraphics();

        box.addListener( new CollectionBox.Listener() {
            public void onAddedMolecule( Molecule molecule ) {
                addMolecule( molecule );
            }

            public void onRemovedMolecule( Molecule molecule ) {
                removeMolecule( molecule );
            }

            public void onAcceptedMoleculeCreation( Molecule molecule ) {
                blink();
            }
        } );

        // update the color if it changes (developer control)
        BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_HIGHLIGHT.addObserver( new SimpleObserver() {
            public void update() {
                updateBoxGraphics();
            }
        } );

        // TODO: this is somewhat of an ugly way of getting the fixed layout (where the molecules don't resize). consider changing
        // kept for now since it is much easier to revert back to the old behavior
        {
            // add invisible molecules to the molecule layer so that its size won't change later (fixes molecule positions)
            List<PNode> nodes = new LinkedList<PNode>();
            for ( int i = 0; i < box.getCapacity(); i++ ) {
                PNode node = box.getMoleculeType().createPseudo3DNode();
                node.setVisible( false );
                nodes.add( node );
                moleculeLayer.addChild( node );
            }

            // position them like we would with the others
            layOutMoleculeList( nodes );
        }

        centerMoleculesInBlackBox();
        addChild( boxNode, method, 3, 0, 0, 0 ); // add this at the specified index
    }

    protected void addHeaderNode( PNode headerNode ) {
        addChild( headerCount, headerNode, method, 0, 0, -3, 0 );
        headerCount++;
    }

    private void addMolecule( Molecule molecule ) {
        cancelBlinksInProgress();
        updateBoxGraphics();

        PNode pseudo3DNode = molecule.getMatchingCompleteMolecule().createPseudo3DNode();
        moleculeLayer.addChild( pseudo3DNode );
        moleculeNodes.add( pseudo3DNode );
        moleculeNodeMap.put( molecule, pseudo3DNode );

        updateMoleculeLayout();
    }

    private void removeMolecule( Molecule molecule ) {
        cancelBlinksInProgress();
        updateBoxGraphics();

        PNode lastMoleculeNode = moleculeNodeMap.get( molecule );
        moleculeLayer.removeChild( lastMoleculeNode );
        moleculeNodes.remove( lastMoleculeNode );
        moleculeNodeMap.remove( molecule );

        updateMoleculeLayout();
    }

    private void updateMoleculeLayout() {
        // position molecule nodes
        layOutMoleculeList( moleculeNodes );

        // center in the black box
        if ( box.quantity.get() > 0 ) {
            centerMoleculesInBlackBox();
        }
    }

    /**
     * Layout of molecules. Spaced horizontally with MOLECULE_PADDING, and vertically centered
     *
     * @param moleculeNodes List of molecules to lay out
     */
    private void layOutMoleculeList( List<PNode> moleculeNodes ) {
        double maxHeight = 0;
        for ( PNode moleculeNode : moleculeNodes ) {
            maxHeight = Math.max( maxHeight, moleculeNode.getFullBounds().getHeight() );
        }
        double x = 0;
        for ( PNode moleculeNode : moleculeNodes ) {
            moleculeNode.setOffset( x, ( maxHeight - moleculeNode.getFullBounds().getHeight() ) / 2 );
            x += moleculeNode.getFullBounds().getWidth() + MOLECULE_PADDING;
        }
    }

    /**
     * @return Molecule area. Excludes the area in the black box where the 3D button needs to go
     */
    private PBounds getMoleculeAreaInBlackBox() {
        PBounds blackBoxFullBounds = blackBox.getFullBounds();
        return new PBounds(
                blackBoxFullBounds.getX(),
                blackBoxFullBounds.getY(),
                blackBoxFullBounds.getWidth() - BLACK_BOX_PADDING_FOR_3D - button3dWidth, // leave room for 3d button on RHS
                blackBoxFullBounds.getHeight()
        );
    }

    private void centerMoleculesInBlackBox() {
        PBounds moleculeArea = getMoleculeAreaInBlackBox();

        // for now, we scale the molecules up and down depending on their size
        moleculeLayer.setScale( 1 );
        double xScale = ( moleculeArea.getWidth() - 25 ) / moleculeLayer.getFullBounds().getWidth();
        double yScale = ( moleculeArea.getHeight() - 25 ) / moleculeLayer.getFullBounds().getHeight();
        moleculeLayer.setScale( Math.min( xScale, yScale ) );

        moleculeLayer.centerFullBoundsOnPoint(
                moleculeArea.getCenterX() - moleculeArea.getX(),
                moleculeArea.getCenterY() - moleculeArea.getY() );
    }

    private void updateBoxGraphics() {
        blackBox.setStroke( new BasicStroke( 4 ) );
        if ( box.isFull() ) {
            blackBox.setStrokePaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_HIGHLIGHT.get() );
        }
        else {
            blackBox.setStrokePaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BACKGROUND );
        }
    }

    /**
     * Allows us to set the model position of the collection boxes according to how they are laid out
     */
    public void updateLocation() {
        locationUpdateObserver.update();
    }

    /**
     * Sets up a blinking box to register that a molecule was created that can go into a box
     */
    private void blink() {
        double blinkLengthInSeconds = 1.3;

        // our delay between states
        int blinkDelayInMs = 100;

        // properties that we will use over time in our blinker
        final Property<Boolean> on = new Property<Boolean>( false ); // on/off state
        final Property<Integer> counts = new Property<Integer>( (int) ( blinkLengthInSeconds * 1000 / blinkDelayInMs ) ); // decrements to zero to stop the blinker

        cancelBlinksInProgress();

        blinkTimer = new Timer();
        blinkTimer.schedule( new TimerTask() {
            @Override
            public void run() {
                // decrement and check
                counts.set( counts.get() - 1 );
                assert ( counts.get() >= 0 );

                if ( counts.get() == 0 ) {
                    // set up our normal graphics (border/background)
                    updateBoxGraphics();

                    // make sure we don't get called again
                    blinkTimer.cancel();
                    blinkTimer = null;
                }
                else {
                    // toggle state
                    on.set( !on.get() );

                    // draw graphics
                    if ( on.get() ) {
                        blackBox.setPaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_BACKGROUND_BLINK );
                        blackBox.setStrokePaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_BORDER_BLINK );
                    }
                    else {
                        blackBox.setPaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_BACKGROUND );
                        blackBox.setStrokePaint( BuildAMoleculeConstants.MOLECULE_COLLECTION_BACKGROUND );
                    }

                    // make sure this paint happens immediately
                    blackBox.repaint();
                }
            }
        }, 0, blinkDelayInMs );
    }

    private void cancelBlinksInProgress() {
        // stop any previous blinking from happening. don't want double-blinking
        if ( blinkTimer != null ) {
            blinkTimer.cancel();
            blinkTimer = null;
        }
    }
}
