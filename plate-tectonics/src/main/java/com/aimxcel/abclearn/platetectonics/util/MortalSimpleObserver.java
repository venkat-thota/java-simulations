package com.aimxcel.abclearn.platetectonics.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.INotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public abstract class MortalSimpleObserver implements SimpleObserver {
    protected MortalSimpleObserver( final ObservableProperty<?> property, INotifier<?> killNotifier ) {
        killNotifier.addUpdateListener( new UpdateListener() {
            public void update() {
                property.removeObserver( MortalSimpleObserver.this );
            }
        }, false );
    }
}
