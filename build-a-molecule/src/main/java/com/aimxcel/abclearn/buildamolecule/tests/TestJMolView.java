//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.tests;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;

import com.aimxcel.abclearn.aimxceljmol.JmolPanel;

/**
 * @author Sam Reid
 */
public class TestJMolView {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Hello" ) {{
            setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            setSize( 410, 410 );
        }};
        Container contentPane = frame.getContentPane();
        JmolPanel jmolPanel = new JmolPanel( MoleculeList.H2O, BuildAMoleculeStrings.JMOL_3D_LOADING );

        contentPane.add( jmolPanel );

        frame.setVisible( true );
    }
}