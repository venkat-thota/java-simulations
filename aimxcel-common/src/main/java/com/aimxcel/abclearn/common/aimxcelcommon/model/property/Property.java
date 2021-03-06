
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ChangeObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;


public class Property<T> extends SettableProperty<T> {
    private T value;
    private final T initialValue;

    /**
     * Create a property with the specified initial value
     *
     * @param value
     */
    public Property( T value ) {
        super( value );
        this.initialValue = value;
        this.value = value;
    }

    public void reset() {
        set( initialValue );
    }

    @Override
    public synchronized T get() {
        // synchronized the get value for visibility and atomicity guarantees
        return value;
    }

    public void set( T value ) {
        Runnable notifier;

        /*
         * Set the value and get the old/new value change notifier while synchronized, so this will be atomic.
         * Otherwise if setting the value and getting the notification values were not atomic, our notifications
         * could present with incorrect values due to race conditions involving other threads.
         */
        synchronized ( this ) {
            this.value = value;
            notifier = getChangeNotifier();
        }

        /*
        * Actually send the notifications AFTER the synchronized block. This prevents the form of deadlock described
        * in getChangeNotifier(), since otherwise observers could run actions that would block permanently.
        */
        notifier.run();
    }

    /**
     * Getter for the initial value, protected to keep the API for Property as simple as possible for typical usage, but
     * possible to override for applications which require knowledge about the initial value such as Gravity and Orbits.
     *
     * @return the initial value
     */
    protected T getInitialValue() {
        return initialValue;
    }

    // test
    public static void main( String[] args ) {
        final Property<Boolean> enabled = new Property<Boolean>( true );
        enabled.addObserver( new SimpleObserver() {
            public void update() {
                System.out.println( "SimpleObserver enabled=" + enabled.get() );
            }
        } );
        enabled.addObserver( new VoidFunction1<Boolean>() {
            public void apply( Boolean newValue ) {
                System.out.println( "VoidFunction1 newValue=" + newValue );
            }
        } );
        enabled.addObserver( new ChangeObserver<Boolean>() {
            public void update( Boolean newValue, Boolean oldValue ) {
                System.out.println( "newValue = " + newValue + ", oldValue = " + oldValue );
            }
        } );
        enabled.set( !enabled.get() );
    }

    /**
     * Convenience factory method to allow writing in the form "value(white)" instead of the more verbose "new Property<Color>(white)", to improve readability at usage sites
     *
     * @param value the value to wrap in a property
     * @param <T>   type of the property
     * @return the Property with the specified value
     */
    public static <T> Property<T> property( T value ) {
        return new Property<T>( value );
    }
}