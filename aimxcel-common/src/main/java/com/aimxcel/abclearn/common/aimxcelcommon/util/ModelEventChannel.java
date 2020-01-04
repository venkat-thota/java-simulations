
 package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.util.EventChannel;
import com.aimxcel.abclearn.common.aimxcelcommon.util.ModelEventChannel;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SwingThreadModelListener;


public class ModelEventChannel extends EventChannel {

    public ModelEventChannel( Class interf ) {
        super( interf );
    }

    protected void invokeMethod( final Method method,
                                 final Object target,
                                 final Object[] args ) throws InvocationTargetException, IllegalAccessException {
    	if ( target instanceof SwingThreadModelListener ) {
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    try {
                        ModelEventChannel.super.invokeMethod( method, target, args );
                    }
                    catch ( InvocationTargetException e ) {
                        e.printStackTrace();
                    }
                    catch ( IllegalAccessException e ) {
                        e.printStackTrace();
                    }
                }
            } );
        }
        else {
            super.invokeMethod( method, target, args );
        }
    }
}
