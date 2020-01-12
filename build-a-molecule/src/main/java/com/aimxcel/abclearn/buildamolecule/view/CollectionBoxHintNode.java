package com.aimxcel.abclearn.buildamolecule.view;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants.MODEL_VIEW_TRANSFORM;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.Atom2D;
import com.aimxcel.abclearn.buildamolecule.model.CollectionBox;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;
import com.aimxcel.abclearn.buildamolecule.model.Atom2D.Adapter;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;


public class CollectionBoxHintNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollectionBoxHintNode( final Molecule molecule, final CollectionBox box ) {

        PBounds moleculeDestinationBounds = molecule.getDestinationBounds();

        // at the end, where our tip and tail should be
        final Vector2D tipTarget = MODEL_VIEW_TRANSFORM.modelToView( new Vector2D( box.getDropBounds().getMinX() - 20, box.getDropBounds().getCenterY() ) );

        final Rectangle2D moleculeViewBounds = MODEL_VIEW_TRANSFORM.modelToViewRectangle( moleculeDestinationBounds );

        PNode labelNode = new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            PText textNode = new PText( BuildAMoleculeStrings.COLLECTION_HINT ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setFont( new AimxcelFont( 16, true ) );
                setTextPaint( Color.BLACK );
            }};
            double verticalPad = 5;
            double horizontalPad = 5;
            PBounds textBounds = textNode.getFullBounds();
            addChild( new AimxcelPPath( new RoundRectangle2D.Double( textBounds.x - horizontalPad, textBounds.y - verticalPad, textBounds.getWidth() + horizontalPad * 2, textBounds.getHeight() + verticalPad * 2, 15, 15 ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setPaint( Color.WHITE );
                setStrokePaint( Color.BLACK );
            }} );
            addChild( textNode );
            centerFullBoundsOnPoint( moleculeViewBounds.getCenterX(), moleculeViewBounds.getMaxY() + 5 + getFullBounds().getHeight() / 2 );
        }};
        addChild( labelNode );

        Vector2D textEnd = new Vector2D( labelNode.getFullBounds().getMaxX() - 5, labelNode.getFullBounds().getCenterY() );
        Vector2D direction = tipTarget.minus( textEnd ).normalized();

        final ArrowNode blueArrow = new ArrowNode( textEnd.plus( direction.times( 15 ) ).toPoint2D(), tipTarget.toPoint2D(), 30, 40, 20 ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setPaint( Color.BLUE );
        }};
        addChild( blueArrow );

        // hide this when we move the molecule
        Adapter grabListener = new Adapter() {
            @Override public void grabbedByUser( Atom2D particle ) {
                disperse();
                for ( Atom2D atom : molecule.getAtoms() ) {
                    atom.removeListener( this );
                }
            }
        };
        for ( Atom2D atom : molecule.getAtoms() ) {
            atom.addListener( grabListener );
        }
    }

    /**
     * Figures out a nice point around the rectangle (padding-distance from it) that the tip of an arrow should end at to
     * point from outsidePoint
     *
     * @param bounds  Rectangle bounds
     * @param padding Padding
     * @param tail    Arrow tail
     * @return Arrow tip
     */
    private Vector2D getInterceptPoint( Rectangle2D bounds, double padding, Vector2D tail ) {
        Vector2D center = new Vector2D( bounds.getCenterX(), bounds.getCenterY() );
        Vector2D unitDirection = center.minus( tail ).normalized();

        double minX = bounds.getMinX() - padding;
        double maxX = bounds.getMaxX() + padding;

        double minY = bounds.getMinY() - padding;
        double maxY = bounds.getMaxY() + padding;

        Vector2D hitLocation = null;

        // check for left/right hits
        if ( tail.getX() < minX || tail.getX() > maxX ) {
            double borderX = tail.getX() > bounds.getCenterX() ? maxX : minX;
            double t = ( borderX - tail.getX() ) / unitDirection.getX();
            hitLocation = tail.plus( unitDirection.times( t ) );
        }
        if ( tail.getY() < minY || tail.getY() > maxY ) {
            double borderY = tail.getY() > bounds.getCenterY() ? maxY : minY;
            double t = ( borderY - tail.getY() ) / unitDirection.getY();

            Vector2D otherHitLocation = tail.plus( unitDirection.times( t ) );
            if ( hitLocation == null || hitLocation.distance( center ) > otherHitLocation.distance( center ) ) {
                hitLocation = otherHitLocation;
            }
        }
        return hitLocation;
    }

    public void disperse() {
        setVisible( false );
    }
}
