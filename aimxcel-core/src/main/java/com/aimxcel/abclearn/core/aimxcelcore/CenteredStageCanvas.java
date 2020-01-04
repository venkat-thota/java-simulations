 
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PDimension;


public class CenteredStageCanvas extends AimxcelPCanvas implements Resettable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final java.util.logging.Logger LOGGER = LoggingUtils.getLogger( CenteredStageCanvas.class.getCanonicalName() );

    private final PNode rootNode;
    private final Dimension2D stageSize;

    // Constructs a canvas with a default stage size that seems to be good for most sims.
    public CenteredStageCanvas() {
        this( CenteredStage.DEFAULT_STAGE_SIZE );
    }

    // Constructs a canvas with a specified stage size.
    public CenteredStageCanvas( Dimension2D stageSize ) {

        this.stageSize = stageSize;

        setWorldTransformStrategy( new CenteredStage( this, stageSize ) );

        // Show the stage bounds if this program arg is specified.
        if ( AimxcelApplication.getInstance().getSimInfo().hasCommandLineArg( "-showStageBounds" ) ) {
            addBoundsNode( stageSize );
        }

        rootNode = new PNode();
        addWorldChild( rootNode );
    }

    // Adds a child to the root node.
    public void addChild( PNode node ) {
        rootNode.addChild( node );
    }

    // Removes a child from the root node.
    public void removeChild( PNode node ) {
        rootNode.removeChild( node );
    }

    // Gets the stage size. Dimension2D is mutable, so this returns a copy.
    public Dimension2D getStageSize() {
        return new PDimension( stageSize );
    }

    public double getStageWidth() {
        return stageSize.getWidth();
    }

    public double getStageHeight() {
        return stageSize.getHeight();
    }

    public Rectangle2D getStageBounds() {
        return new Rectangle2D.Double( -rootNode.getXOffset(), -rootNode.getYOffset(), getStageWidth(), getStageHeight() );
    }

    // Centers the root node on the stage.
    public void centerRootNodeOnStage() {
        rootNode.setOffset( ( ( getStageWidth() - rootNode.getFullBoundsReference().getWidth() ) / 2 ) - PNodeLayoutUtils.getOriginXOffset( rootNode ),
                            ( ( getStageHeight() - rootNode.getFullBoundsReference().getHeight() ) / 2 ) - PNodeLayoutUtils.getOriginYOffset( rootNode ) );
    }

    // Scales the root node to fit in the bounds of the stage.
    public void scaleRootNodeToFitStage() {
        double xScale = getStageWidth() / rootNode.getFullBoundsReference().getWidth();
        double yScale = getStageHeight() / rootNode.getFullBoundsReference().getHeight();
        if ( xScale < 1 || yScale < 1 ) {
            final double scale = Math.min( xScale, yScale );
            LOGGER.info( "rootNode won't fit in the play area, scaling rootNode by " + scale + " for " + getClass().getName() );
            rootNode.scale( scale );
        }
    }

    public void reset() {}
}