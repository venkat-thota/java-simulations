// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IParameterKey;

/**
 * Class for creating custom IParameterKey without using enum.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class ParameterKey implements IParameterKey {

    private final String id;

    public ParameterKey( String id ) {
        this.id = id;
    }

    @Override public String toString() {
        return id;
    }
}