

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package edu.colorado.phet.common.phetgraphics.view.phetcomponents;

import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JComponent;
import javax.swing.SortingFocusTraversalPolicy;

/**
 * Uses setNextFocusableComponent to handle focus.  Doesn't support removal yet.  A later version should use FocusTraversalPolicy.
 */

public class PJCFocusManager implements AbcLearnJComponentManager.Listener {
    private ArrayList list = new ArrayList();
    private FocusTraversalPolicy policy = new PJCFocusManager.MyPolicy();

    public void phetJComponentCreated( AbcLearnJComponent phetJComponent ) {
        if ( list.size() >= 1 ) {
            AbcLearnJComponent prev = (AbcLearnJComponent) list.get( list.size() - 1 );
            prev.getSourceComponent().setNextFocusableComponent( phetJComponent.getSourceComponent() );

            AbcLearnJComponent first = (AbcLearnJComponent) list.get( 0 );
            phetJComponent.getSourceComponent().setNextFocusableComponent( first.getSourceComponent() );
        }
        list.add( phetJComponent );
    }

    public class MyPolicy extends SortingFocusTraversalPolicy {
        public MyPolicy() {
            super( new PJCFocusManager.MyComparator() );
        }
    }

    public class MyComparator implements Comparator {
        public int compare( Object o1, Object o2 ) {
            if ( o1 instanceof JComponent && o2 instanceof JComponent ) {
                int d1 = indexOf( (JComponent) o1 );
                int d2 = indexOf( (JComponent) o2 );
                return Double.compare( d1, d2 );
            }
            return 0;
        }
    }

    private int indexOf( JComponent jComponent ) {
        for ( int i = 0; i < list.size(); i++ ) {
            AbcLearnJComponent phetJComponent = (AbcLearnJComponent) list.get( i );
            if ( phetJComponent.getSourceComponent() == jComponent ) {
                return i;
            }
        }
        return -1;
    }
}