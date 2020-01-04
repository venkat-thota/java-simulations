 
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IParameterKey;


public class ParameterKey implements IParameterKey {

    private final String id;

    public ParameterKey( String id ) {
        this.id = id;
    }

    @Override public String toString() {
        return id;
    }
}