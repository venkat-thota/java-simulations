package com.aimxcel.abclearn.aimxceljmol;

import org.jmol.api.JmolViewer;

public interface Molecule {

    
    String getDisplayName();

    
    String getData();

    
    void fixJmolColors( JmolViewer viewer );
}
