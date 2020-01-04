
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.periodictable;

import java.awt.Color;

/**
 * Default cell factory just shows an empty square with the specified background color and the element symbol
 *
 * @author Sam Reid
 */
public class DefaultCellFactory implements CellFactory {
    public ElementCell createCellForElement( int atomicNumberOfCell, Color backgroundColor ) {
        return new BasicElementCell( atomicNumberOfCell, backgroundColor );
    }
}