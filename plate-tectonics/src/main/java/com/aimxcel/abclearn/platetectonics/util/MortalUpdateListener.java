// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.platetectonics.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.INotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;

/**
 * Listener workaround so that we can stop listening to a notifier when another notifier fires. This is useful to prevent memory leaks.
 */
public abstract class MortalUpdateListener extends UpdateListener {
    protected MortalUpdateListener( final INotifier<?> sourceNotifier, INotifier<?> killNotifier ) {
        killNotifier.addUpdateListener( new UpdateListener() {
            public void update() {
                sourceNotifier.removeListener( MortalUpdateListener.this );
            }
        }, false );
    }
}
