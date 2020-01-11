// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.platetectonics.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.INotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

/**
 * Listener workaround so that we can stop listening to a property when a notifier fires. This is useful to prevent memory leaks.
 */
public abstract class MortalSimpleObserver implements SimpleObserver {
    protected MortalSimpleObserver( final ObservableProperty<?> property, INotifier<?> killNotifier ) {
        killNotifier.addUpdateListener( new UpdateListener() {
            public void update() {
                property.removeObserver( MortalSimpleObserver.this );
            }
        }, false );
    }
}
