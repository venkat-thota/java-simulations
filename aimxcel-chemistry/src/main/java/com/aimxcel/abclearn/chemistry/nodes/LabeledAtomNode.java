package com.aimxcel.abclearn.chemistry.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.chemistry.model.Element;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadedSphereNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class LabeledAtomNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ShadedSphereNode sphericalNode;
    private final PNode labelContainer = new PNode();
    private final PText labelNode;

    public LabeledAtomNode( final Element element ) {

        double transformedRadius = element.getRadius();
        sphericalNode = new ShadedSphereNode( 2 * transformedRadius, element.getColor() );
        addChild( sphericalNode );

        // Create, scale, and add the label
        labelNode = new PText() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setText( element.getSymbol() );
            setFont( new AimxcelFont( 10, true ) );
            setScale( sphericalNode.getFullBoundsReference().width * 0.65 / getFullBoundsReference().width );
            if ( 0.30 * element.getColor().getRed() + 0.59 * element.getColor().getGreen() + 0.11 * element.getColor().getBlue() < 125 ) {
                setTextPaint( Color.WHITE );
            }
            setOffset( -getFullBoundsReference().width / 2, -getFullBoundsReference().height / 2 );
        }};
        labelContainer.addChild( labelNode );
        sphericalNode.addChild( labelContainer );

        // Add a cursor handler to signal to the user that this is movable.
//        addInputEventListener( new CursorHandler() );
    }

    // added this in, because rotating the ShadedSphereNode actually causes painting errors
    public void rotateTo( double theta ) {
        labelContainer.setRotation( -theta );
    }

    public PText getLabelNode() {
        return labelNode;
    }
}
