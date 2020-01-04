
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.client;

import java.io.IOException;

public interface IActor<T, U> {

    //Send an object and wait for a response object
    U ask( T question ) throws IOException, ClassNotFoundException;

    //Send a one-way message
    void tell( T statement ) throws IOException;
}