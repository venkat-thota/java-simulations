
package com.aimxcel.abclearn.greenhouse.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

import com.aimxcel.abclearn.greenhouse.GreenhouseDefaults;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.photonabsorption.model.Photon;
import com.aimxcel.abclearn.photonabsorption.model.PhotonAbsorptionModel;
import com.aimxcel.abclearn.photonabsorption.view.MoleculeNode;
import com.aimxcel.abclearn.photonabsorption.view.PhotonEmitterNode;
import com.aimxcel.abclearn.photonabsorption.view.PAPhotonNode;
import com.aimxcel.abclearn.photonabsorption.view.VerticalRodNode;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class PhotonAbsorptionCanvas extends AimxcelPCanvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------------------
    // Class Data
    //----------------------------------------------------------------------------

    private static final double PHOTON_EMITTER_WIDTH = 300;

    //----------------------------------------------------------------------------
    // Instance Data
    //----------------------------------------------------------------------------

    // Model-view transform.
    private final ModelViewTransform2D mvt;

    // Local root node for world children.
    PNode myWorldNode;

    // Layers for the canvas.
    private final PNode moleculeLayer;
    private final PNode photonLayer;
    private final PNode photonEmitterLayer;

    // Data structures that match model objects to their representations in
    // the view.
    private final HashMap<Photon, PAPhotonNode> photonMap = new HashMap<Photon, PAPhotonNode>();
    private final HashMap<Molecule, MoleculeNode> moleculeMap = new HashMap<Molecule, MoleculeNode>();

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructor.
     *
     * @param photonAbsorptionModel - Model that is being portrayed on this canvas.
     * @param onTabbedSim - boolean that indicates whether the canvas should
     * be sized assuming that it is in a sim with tabs, which reduces the
     * initial size of the play area.
     */
    public PhotonAbsorptionCanvas( PhotonAbsorptionModel photonAbsorptionModel, boolean onTabbedSim ) {

        // Set up the canvas-screen transform.
        setWorldTransformStrategy( new CenteringBoxStrategy( this, GreenhouseDefaults.INTERMEDIATE_RENDERING_SIZE ) );

        // Use a different zoom factor if this is on a non-tabbed sim, since
        // otherwise things will probably go off the left and right edges of
        // the play area.
        double mvtScaleFactor = onTabbedSim ? 0.23 : 0.18;
        // Set up the model-canvas transform.  The multiplier values below can
        // be used to shift the center of the play area right or left, and the
        // scale factor can be used to essentially zoom in or out.
        mvt = new ModelViewTransform2D(
                new Point2D.Double( 0, 0 ),
                new Point( (int) Math.round( GreenhouseDefaults.INTERMEDIATE_RENDERING_SIZE.width * 0.65 ),
                (int) Math.round( GreenhouseDefaults.INTERMEDIATE_RENDERING_SIZE.height * 0.5 ) ),
                mvtScaleFactor, // Scale factor - smaller numbers "zoom out", bigger ones "zoom in".
        true );

        setBackground( Color.BLACK );

        // Listen to the model for notifications that the canvas cares about,
        // which are primarily the coming and going of model objects.
        photonAbsorptionModel.addListener( new PhotonAbsorptionModel.Adapter() {

            @Override
            public void photonRemoved( Photon photon ) {
                if ( photonLayer.removeChild( photonMap.get( photon ) ) == null ) {
                    System.out.println( getClass().getName() + " - Error: PhotonNode not found for photon." );
                }
                photonMap.remove( photon );
            }

            @Override
            public void photonAdded( Photon photon ) {
                PAPhotonNode photonNode = new PAPhotonNode( photon, mvt );
                photonLayer.addChild( photonNode );
                photonMap.put( photon, photonNode );
            }

            @Override
            public void moleculeRemoved( Molecule molecule ) {
                if ( moleculeLayer.removeChild( moleculeMap.get( molecule ) ) == null ) {
                    System.out.println( getClass().getName() + " - Error: MoleculeNode not found for molecule." );
                }
                moleculeMap.remove( molecule );
            }

            @Override
            public void moleculeAdded( Molecule molecule ) {
                addMolecule( molecule );
            }
        } );

        // Create the node that will be the root for all the world children on
        // this canvas.  This is done to make it easier to zoom in and out on
        // the world without affecting screen children.
        myWorldNode = new PNode();
        addWorldChild( myWorldNode );

        // Add the layers.
        moleculeLayer = new PNode();
        myWorldNode.addChild( moleculeLayer );
        photonLayer = new PNode();
        myWorldNode.addChild( photonLayer );
        photonEmitterLayer = new PNode();
        myWorldNode.addChild( photonEmitterLayer );

        // Add the chamber.
        AimxcelPPath chamberNode = new AimxcelPPath(
                mvt.createTransformedShape( photonAbsorptionModel.getContainmentAreaRect() ),
                new BasicStroke( 6 ),
                Color.LIGHT_GRAY );
        moleculeLayer.addChild( chamberNode );

        // Create the photon emitter.
        PNode photonEmitterNode = new PhotonEmitterNode( PHOTON_EMITTER_WIDTH, mvt, photonAbsorptionModel );
        photonEmitterNode.setOffset( mvt.modelToViewDouble( photonAbsorptionModel.getPhotonEmissionLocation() ) );

        // Create the control panel for photon emission frequency.
        PNode photonEmissionControlPanel = new DualEmissionFrequencyControlPanel( photonAbsorptionModel );
        photonEmissionControlPanel.setOffset(
                photonEmitterNode.getFullBoundsReference().getCenterX() - ( photonEmissionControlPanel.getFullBoundsReference().width / 2 ),
                photonEmitterNode.getFullBoundsReference().getMaxY() + 50 );

        // Create the rod that connects the emitter to the control panel.
        PNode connectingRod = new VerticalRodNode( 30,
                Math.abs( photonEmitterNode.getFullBoundsReference().getCenterY() - photonEmissionControlPanel.getFullBoundsReference().getCenterY() ),
                Color.LIGHT_GRAY );
        connectingRod.setOffset(
                photonEmitterNode.getFullBoundsReference().getCenterX() - ( connectingRod.getFullBoundsReference().width / 2 ),
                photonEmitterNode.getFullBoundsReference().getCenterY() );

        // Add the nodes in the order necessary for correct layering.
        photonEmitterLayer.addChild( connectingRod );
        photonEmitterLayer.addChild( photonEmitterNode );
        photonEmitterLayer.addChild( photonEmissionControlPanel );

        // Add in the initial molecule(s).
        for ( Molecule molecule : photonAbsorptionModel.getMolecules() ) {
            addMolecule( molecule );
        }

        // Update the layout.
        updateLayout();
    }

    //----------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------

    private void addMolecule( Molecule molecule ) {
        MoleculeNode moleculeNode = new MoleculeNode( molecule, mvt );
        moleculeLayer.addChild( moleculeNode );
        moleculeMap.put( molecule, moleculeNode );
    }
}
