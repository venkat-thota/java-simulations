package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import java.awt.Color;

public interface CellFactory {

   
    ElementCell createCellForElement( int atomicNumberOfCell, Color backgroundColor );
}