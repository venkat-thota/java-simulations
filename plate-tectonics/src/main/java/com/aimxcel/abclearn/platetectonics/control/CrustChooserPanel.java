package com.aimxcel.abclearn.platetectonics.control;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Spacer;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class CrustChooserPanel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// size and padding for the icons
    public static final int CRUST_AREA_MAX_WIDTH = 140;
    public static final int CRUST_AREA_MAX_HEIGHT = 100;
    public static final int CRUST_AREA_PADDING = 20;

    // how much horizontal room we need for labeling
    private final int verticalOffset;

    // spacers to enforce the layout
    private final Spacer continentalSpacer;
    private final Spacer youngOceanicSpacer;
    private final Spacer oldOceanicSpacer;

    public CrustChooserPanel() {
        PNode continentalLabel = new PText( Strings.CONTINENTAL_CRUST );
        PNode youngOceanicLabel = new PText( Strings.YOUNG_OCEANIC_CRUST );
        PNode oldOceanicLabel = new PText( Strings.OLD_OCEANIC_CRUST );

        addChild( continentalLabel );
        addChild( youngOceanicLabel );
        addChild( oldOceanicLabel );

        // center the labels above the draggable crust pieces
        continentalLabel.setOffset( ( CRUST_AREA_MAX_WIDTH - continentalLabel.getFullBounds().getWidth() ) / 2, 0 );
        youngOceanicLabel.setOffset( ( CRUST_AREA_MAX_WIDTH - youngOceanicLabel.getFullBounds().getWidth() ) / 2
                                     + CRUST_AREA_PADDING + CRUST_AREA_MAX_WIDTH, 0 );
        oldOceanicLabel.setOffset( ( CRUST_AREA_MAX_WIDTH - oldOceanicLabel.getFullBounds().getWidth() ) / 2
                                   + ( CRUST_AREA_PADDING + CRUST_AREA_MAX_WIDTH ) * 2, 0 );

        // padding between labels and crust pieces
        verticalOffset = (int) ( continentalLabel.getFullBounds().getHeight() + 10 );

        // add spacers so that the ControlPanelNode border accounts for the room
        // the crust pieces are added as separate draggable nodes, and thus are not contained within this panel
        continentalSpacer = new Spacer( 0, verticalOffset, CRUST_AREA_MAX_WIDTH, CRUST_AREA_MAX_HEIGHT );
        addChild( continentalSpacer );

        youngOceanicSpacer = new Spacer( ( CRUST_AREA_PADDING + CRUST_AREA_MAX_WIDTH ), verticalOffset, CRUST_AREA_MAX_WIDTH, CRUST_AREA_MAX_HEIGHT );
        addChild( youngOceanicSpacer );

        oldOceanicSpacer = new Spacer( ( CRUST_AREA_PADDING + CRUST_AREA_MAX_WIDTH ) * 2, verticalOffset, CRUST_AREA_MAX_WIDTH, CRUST_AREA_MAX_HEIGHT );
        addChild( oldOceanicSpacer );
    }

    private Vector2F getPieceCenter( Spacer node ) {
        Point2D globalPoint = node.localToGlobal( new Point2D.Double(
                node.getRectangle().getX() + CRUST_AREA_MAX_WIDTH / 2,
                node.getRectangle().getY() + CRUST_AREA_MAX_HEIGHT / 2 ) );
        return new Vector2F( globalPoint.getX(), globalPoint.getY() );
    }

    /*---------------------------------------------------------------------------*
    * coordinates for where the crust pieces should be placed
    *----------------------------------------------------------------------------*/
    public Vector2F getContinentalCenter() {
        return getPieceCenter( continentalSpacer );
    }

    public Vector2F getYoungOceanicCenter() {
        return getPieceCenter( youngOceanicSpacer );
    }

    public Vector2F getOldOceanicCenter() {
        return getPieceCenter( oldOceanicSpacer );
    }
}
