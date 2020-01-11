
package com.aimxcel.abclearn.motion.model;



public interface IVariable {
    void setValue( double value );

    double getValue();

    public void addListener( Listener listener );

    public void removeListener( Listener listener );

    public static interface Listener {
        void valueChanged();
    }
}
