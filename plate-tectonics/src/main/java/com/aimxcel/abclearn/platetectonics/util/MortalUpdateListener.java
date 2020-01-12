package com.aimxcel.abclearn.platetectonics.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.INotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;


public abstract class MortalUpdateListener extends UpdateListener {
    protected MortalUpdateListener( final INotifier<?> sourceNotifier, INotifier<?> killNotifier ) {
        killNotifier.addUpdateListener( new UpdateListener() {
            public void update() {
                sourceNotifier.removeListener( MortalUpdateListener.this );
            }
        }, false );
    }
}
