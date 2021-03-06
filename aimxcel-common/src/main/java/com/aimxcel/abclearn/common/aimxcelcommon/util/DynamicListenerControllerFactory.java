
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DynamicListenerController;


public class DynamicListenerControllerFactory {
    public static DynamicListenerController newController( Class theInterface ) {
        if ( !theInterface.isInterface() ) {
            throw new IllegalStateException( "The specified class must be an interface." );
        }

        DynamicListenerControllerImpl controller = new DynamicListenerControllerImpl( theInterface );

        return (DynamicListenerController) Proxy.newProxyInstance(
                theInterface.getClassLoader(),
                new Class[] { theInterface, DynamicListenerController.class },
                controller
        );
    }

    private static class DynamicListenerControllerImpl implements DynamicListenerController, InvocationHandler {
        private Collection listeners = new HashSet();

        private final Class listenerInterface;

        DynamicListenerControllerImpl( Class listenerInterface ) {
            this.listenerInterface = listenerInterface;
        }

        public void addListener( Object listener ) throws IllegalStateException {
            if ( !listenerInterface.isAssignableFrom( listener.getClass() ) ) {
                throw new IllegalStateException( "The object " + listener + " fails to implement " + listenerInterface + "." );
            }

            listeners.add( listener );
        }

        public void removeListener( Object listener ) {
            listeners.remove( listener );
        }

        public Collection getAllListeners() {
            return Collections.unmodifiableCollection( listeners );
        }

        public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
            if ( method.getDeclaringClass() == Object.class ) {
                return method.invoke( this, args );
            }
            else if ( method.getDeclaringClass() == DynamicListenerController.class ) {
                try {
                    return method.invoke( this, args );
                }
                catch ( InvocationTargetException e ) {
                    throw e.getTargetException();
                }
            }
            else if ( method.getReturnType() == void.class ) {
                Iterator iterator = getAllListeners().iterator();

                while ( iterator.hasNext() ) {
                    Object listener = iterator.next();

                    method.invoke( listener, args );
                }

                return null;
            }
            else {
                throw new IllegalStateException( "Cannot implement method " + method );
            }
        }
    }
}
