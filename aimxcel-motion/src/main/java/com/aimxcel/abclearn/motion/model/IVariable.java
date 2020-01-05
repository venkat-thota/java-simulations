
package com.aimxcel.abclearn.motion.model;

/**
 * User: Sam Reid
 * Date: Dec 29, 2006
 * Time: 9:15:36 AM
 */

public interface IVariable {
    void setValue( double value );

    double getValue();

    public void addListener( Listener listener );

    public void removeListener( Listener listener );

    public static interface Listener {
        void valueChanged();
    }
}
