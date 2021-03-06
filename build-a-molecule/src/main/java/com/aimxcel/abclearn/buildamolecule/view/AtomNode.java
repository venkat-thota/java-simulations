package com.aimxcel.abclearn.buildamolecule.view;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants.MODEL_VIEW_TRANSFORM;

import java.awt.*;

import com.aimxcel.abclearn.buildamolecule.model.Atom2D;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadedSphereNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class AtomNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ShadedSphereNode sphericalNode;

    public AtomNode( final Atom2D atom ) {

        double transformedRadius = MODEL_VIEW_TRANSFORM.modelToViewDeltaX( atom.getRadius() );
        sphericalNode = new ShadedSphereNode( 2 * transformedRadius, atom.getColor() );
        addChild( sphericalNode );

        // Create, scale, and add the label
        PText labelNode = new PText() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setText( atom.getSymbol() );
            setFont( new AimxcelFont( 10, true ) );
            setScale( sphericalNode.getFullBoundsReference().width * 0.65 / getFullBoundsReference().width );
            if ( 0.30 * atom.getColor().getRed() + 0.59 * atom.getColor().getGreen() + 0.11 * atom.getColor().getBlue() < 125 ) {
                setTextPaint( Color.WHITE );
            }
            setOffset( -getFullBoundsReference().width / 2, -getFullBoundsReference().height / 2 );
        }};
        sphericalNode.addChild( labelNode );

        // Add the code for moving this node when the model element's position
        // changes.
        atom.addPositionListener( new SimpleObserver() {
            public void update() {
                sphericalNode.setOffset( MODEL_VIEW_TRANSFORM.modelToView( atom.getPosition() ).toPoint2D() );
            }
        } );

        // Add a cursor handler to signal to the user that this is movable.
        addInputEventListener( new CursorHandler() );

        // respond to the visibility of the atom
        atom.visible.addObserver( new SimpleObserver() {
            public void update() {
                setVisible( atom.visible.get() );
            }
        } );

        // and if we remove the atom from play, we will get rid of this node
        atom.addListener( new Atom2D.Adapter() {
            @Override
            public void removedFromModel( Atom2D atom ) {
                getParent().removeChild( AtomNode.this );
            }
        } );
    }
}
