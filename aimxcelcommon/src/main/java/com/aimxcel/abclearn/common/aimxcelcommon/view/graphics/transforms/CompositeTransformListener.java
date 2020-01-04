

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.TransformListener;

/**
 * CompositeTransformListener
 *
 * @author ?
 * @version $Revision$
 */
public class CompositeTransformListener implements TransformListener {
    private ArrayList listeners = new ArrayList();

    public void transformChanged( ModelViewTransform2D mvt ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            TransformListener o = (TransformListener) listeners.get( i );
            o.transformChanged( mvt );
        }
    }

    public TransformListener transformListenerAt( int i ) {
        return (TransformListener) listeners.get( i );
    }

    public void removeTransformListener( TransformListener tl ) {
        listeners.remove( tl );
    }

    public int numTransformListeners() {
        return listeners.size();
    }

    public void addTransformListener( TransformListener tl ) {
        listeners.add( tl );
    }

    public TransformListener[] getTransformListeners() {
        return (TransformListener[]) listeners.toArray( new TransformListener[0] );
    }
}