package com.aimxcel.abclearn.buildamolecule.tests;

import java.io.File;
import java.io.IOException;

import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;

import com.aimxcel.abclearn.common.aimxcelcommon.util.FileUtils;
public class FullTranslationGenerator {
    public static void main( String[] args ) {
        File outFile = new File( args[0] );

        StringBuilder builder = new StringBuilder();
        for ( CompleteMolecule molecule : MoleculeList.getMasterInstance().getAllCompleteMolecules() ) {
            // performance not an issue
            builder.append( molecule.getStringKey() + "=" + molecule.getCommonName() + "\n" );
        }

        try {
            FileUtils.writeString( outFile, builder.toString() );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
