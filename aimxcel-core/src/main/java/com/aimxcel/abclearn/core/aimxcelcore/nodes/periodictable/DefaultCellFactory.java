
package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import java.awt.Color;


public class DefaultCellFactory implements CellFactory {
    public ElementCell createCellForElement( int atomicNumberOfCell, Color backgroundColor ) {
        return new BasicElementCell( atomicNumberOfCell, backgroundColor );
    }
}