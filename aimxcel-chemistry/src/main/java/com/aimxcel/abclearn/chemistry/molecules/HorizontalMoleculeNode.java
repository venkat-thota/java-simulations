package com.aimxcel.abclearn.chemistry.molecules;


import static com.aimxcel.abclearn.chemistry.model.Element.*;

import com.aimxcel.abclearn.chemistry.model.Element;

import com.aimxcel.abclearn.core.aimxcelcore.util.PNodeLayoutUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
public abstract class HorizontalMoleculeNode extends PComposite {

    public HorizontalMoleculeNode( Element... atoms ) {

        PComposite parentNode = new PComposite();
        addChild( parentNode );

        // add each node from left to right, overlapping consistently
        double x = 0;
        PNode previousNode = null;
        for ( Element atom : atoms ) {
            AtomNode currentNode = new AtomNode( atom );
            parentNode.addChild( currentNode );
            if ( previousNode != null ) {
                x = previousNode.getFullBoundsReference().getMaxX() + ( 0.25 * currentNode.getFullBoundsReference().getWidth() );
            }
            currentNode.setOffset( x, 0 );
            previousNode = currentNode;
        }

        // move origin to geometric center
        parentNode.setOffset( -PNodeLayoutUtils.getOriginXOffset( parentNode ), -PNodeLayoutUtils.getOriginYOffset( parentNode ) );
    }

    public static class CNode extends HorizontalMoleculeNode {
        public CNode() {
            super( C );
        }
    }

    public static class Cl2Node extends HorizontalMoleculeNode {
        public Cl2Node() {
            super( Cl, Cl );
        }
    }

    public static class CONode extends HorizontalMoleculeNode {
        public CONode() {
            super( C, O );
        }
    }

    public static class CO2Node extends HorizontalMoleculeNode {
        public CO2Node() {
            super( O, C, O );
        }
    }

    public static class CS2Node extends HorizontalMoleculeNode {
        public CS2Node() {
            super( S, C, S );
        }
    }

    public static class F2Node extends HorizontalMoleculeNode {
        public F2Node() {
            super( F, F );
        }
    }

    public static class H2Node extends HorizontalMoleculeNode {
        public H2Node() {
            super( H, H );
        }
    }

    public static class N2Node extends HorizontalMoleculeNode {
        public N2Node() {
            super( N, N );
        }
    }

    public static class NONode extends HorizontalMoleculeNode {
        public NONode() {
            super( N, O );
        }
    }

    public static class N2ONode extends HorizontalMoleculeNode {
        public N2ONode() {
            super( N, N, O );
        }
    }

    public static class O2Node extends HorizontalMoleculeNode {
        public O2Node() {
            super( O, O );
        }
    }

    public static class SNode extends HorizontalMoleculeNode {
        public SNode() {
            super( S );
        }
    }
}
