
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;


public abstract class RichSimpleObserver implements SimpleObserver {
    public abstract void update();

    
    public void observe( ObservableProperty... properties ) {
        for ( ObservableProperty property : properties ) {
            property.addObserver( this );
        }
    }

    
    public void unobserve( ObservableProperty... properties ) {
        for ( ObservableProperty property : properties ) {
            property.removeObserver( this );
        }
    }
}
