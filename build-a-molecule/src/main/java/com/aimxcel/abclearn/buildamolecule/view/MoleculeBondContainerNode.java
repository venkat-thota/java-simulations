//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.view;

import java.util.LinkedList;
import java.util.List;

import com.aimxcel.abclearn.buildamolecule.model.Atom2D;
import com.aimxcel.abclearn.buildamolecule.model.Bond;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;

/**
 * Contains "bond breaking" nodes for a single molecule, so they can be cut apart with scissors
 */
public class MoleculeBondContainerNode extends PNode {
    private List<MoleculeBondNode> bondNodes = new LinkedList<MoleculeBondNode>();

    public MoleculeBondContainerNode( final Kit kit, Molecule molecule, final BuildAMoleculeCanvas canvas ) {
        for ( Bond<Atom2D> bond : molecule.getBonds() ) {
            addChild( new MoleculeBondNode( bond, kit, canvas ) {{
                bondNodes.add( this );
            }} );
        }
    }

    public void destruct() {
        for ( MoleculeBondNode bondNode : bondNodes ) {
            bondNode.destruct();
        }
    }
}
