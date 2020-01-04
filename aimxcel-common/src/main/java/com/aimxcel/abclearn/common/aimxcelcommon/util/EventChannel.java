
 package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class EventChannel implements InvocationHandler {

    private List targets = new ArrayList();
    private Class targetInterface;
    private Object proxy;
    private List listenersToAdd = new ArrayList();
    private List listenersToRemove = new ArrayList();
    private boolean invokingTargets;
    public EventChannel( Class theClass ) {
        if ( !EventListener.class.isAssignableFrom( theClass ) ) {
            throw new InvalidParameterException( "Attempt to create proxy for a class that is not an EventListener" );
        }
        targetInterface = theClass;
        proxy = Proxy.newProxyInstance( theClass.getClassLoader(),
                                        new Class[] { theClass }, this );
    }

     public synchronized void addListener( EventListener listener ) {
        if ( targetInterface.isInstance( listener ) ) {
            if ( invokingTargets ) {
                listenersToAdd.add( listener );
            }
            else {
                targets.add( listener );
            }
        }
        else {
            throw new InvalidParameterException( "Parameter is not EventListener" );
        }
    }

    /**
     * Removes a listener from the registry
     *
     * @param listener
     */
    public synchronized void removeListener( EventListener listener ) {
        if ( targetInterface.isInstance( listener ) ) {
            if ( invokingTargets ) {
                listenersToRemove.add( listener );
            }
            else {
                targets.remove( listener );
            }
        }
        else {
            throw new InvalidParameterException( "Parameter is not EventListener" );
        }
    }

    /**
     * Removes all listeners from the registry
     */
    public void removeAllListeners() {
        targets.clear();
    }

    /**
     * Returns the number of registered listeners.
     *
     * @return the number of registered listeners.
     */
    public int getNumListeners() {
        return targets.size();
    }

    /**
     * Determines whether this EventChannel contains the specified listener.
     *
     * @param eventListener
     * @return true if this EventChannel contains the specified listener.
     */
    public boolean containsListener( EventListener eventListener ) {
        return targets.contains( eventListener );
    }

    /**
     * Returns the interface for which this object acts as a proxy.
     *
     * @return the interface Class
     */
    public Class getInterface() {
        return targetInterface;
    }

    /**
     * Returns a reference to the proxy
     *
     * @return the proxy
     */
    public Object getListenerProxy() {
        return proxy;
    }

    //----------------------------------------------------------------------------
    // Invocation Handler implementation
    //----------------------------------------------------------------------------

    /**
     * Invokes a specified method on all the instances in the channel that implememt it
     *
     * @param proxy
     * @param method
     * @param args
     * @return the result of the invocation.
     * @throws Throwable
     */
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
        Object target = null;
        try {
            invokingTargets = true;
            for ( int i = 0; i < targets.size(); i++ ) {
                target = targets.get( i );
                invokeMethod( method, target, args );
            }
            invokingTargets = false;

            // If anyone tried to add or remove a listener while we were invoking
            // targets, add/remove them now
            if ( !listenersToAdd.isEmpty() ) {
                targets.addAll( listenersToAdd );
                listenersToAdd.clear();
            }
            if ( !listenersToRemove.isEmpty() ) {
                targets.removeAll( listenersToRemove );
                listenersToRemove.clear();
            }
        }
        catch ( InvocationTargetException ite ) {
            throw new InvocationTargetException( ite, "target = " + target );
        }
        catch ( Throwable t ) {
            System.out.println( "t = " + t );
            throw new Throwable( t );
        }
        return null;
    }

    protected void invokeMethod( Method method, Object target, Object[] args ) throws InvocationTargetException,
                                                                                      IllegalAccessException {
        method.invoke( target, args );
    }
}
