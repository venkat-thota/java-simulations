package com.aimxcel.abclearn.buildamolecule.tests;

import java.util.List;

import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;

public class DuplicateMoleculeNameCheck {
    public static void main( String[] args ) {
        List<CompleteMolecule> molecules = MoleculeList.getMasterInstance().getAllCompleteMolecules();

        for ( CompleteMolecule a : molecules ) {
            for ( CompleteMolecule b : molecules ) {
                if ( a.cid < b.cid && a.getCommonName().equals( b.getCommonName() ) ) {
                    System.out.println( "duplicate name: " + a.getCommonName() + " -- " + a.cid + "," + b.cid );
                    System.out.println( a.cid + ": " + a.getGeneralFormula() );
                    System.out.println( b.cid + ": " + b.getGeneralFormula() );
                }
            }
        }
    }
}
