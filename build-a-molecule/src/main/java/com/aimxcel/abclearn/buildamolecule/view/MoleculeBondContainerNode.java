package com.aimxcel.abclearn.buildamolecule.view;

import java.util.LinkedList;
import java.util.List;

import com.aimxcel.abclearn.buildamolecule.model.Atom2D;
import com.aimxcel.abclearn.buildamolecule.model.Bond;
import com.aimxcel.abclearn.buildamolecule.model.Kit;
import com.aimxcel.abclearn.buildamolecule.model.Molecule;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
public class MoleculeBondContainerNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MoleculeBondNode> bondNodes = new LinkedList<MoleculeBondNode>();

    public MoleculeBondContainerNode( final Kit kit, Molecule molecule, final BuildAMoleculeCanvas canvas ) {
        for ( Bond<Atom2D> bond : molecule.getBonds() ) {
            addChild( new MoleculeBondNode( bond, kit, canvas ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
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
