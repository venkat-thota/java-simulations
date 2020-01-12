package com.aimxcel.abclearn.greenhouse.model;


public interface PhotonEmitter<T> {

    void addListener( Listener<?> listener );

    void removeListener( Listener<?> listener );

    double getProductionRate();

    void setProductionRate( double productionRate );

    T emitPhoton();

    //
    // Inner classes
    //
    public interface Listener<T> {
        void photonEmitted( T photon );
    }

}
