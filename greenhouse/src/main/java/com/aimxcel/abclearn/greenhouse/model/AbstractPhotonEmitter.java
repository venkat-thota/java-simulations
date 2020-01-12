package com.aimxcel.abclearn.greenhouse.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.greenhouse.model.PhotonEmitter.Listener;

public abstract class AbstractPhotonEmitter extends Observable implements ModelElement, PhotonEmitter<Photon> {

    private double productionRate;
    private double timeSincePhotonsProduced;
    private HashSet<Listener<?>> listeners = new HashSet<Listener<?>>();

    public void addListener( PhotonEmitter.Listener listener ) {
        listeners.add( listener );
    }

    public void removeListener( PhotonEmitter.Listener listener ) {
        listeners.remove( listener );
    }

    public double getProductionRate() {
        return productionRate;
    }

    public void setProductionRate( double productionRate ) {
        this.timeSincePhotonsProduced = 0;
        this.productionRate = productionRate;
    }

    public void stepInTime( double dt ) {
        timeSincePhotonsProduced += dt;
        int numPhotons = (int) ( productionRate * timeSincePhotonsProduced );
        for ( int i = 0; i < numPhotons; i++ ) {
            Photon photon = emitPhoton();
            notifyListeners( photon );
            timeSincePhotonsProduced = 0;
        }
    }

    // Notify all listeners
    protected void notifyListeners( Photon photon ) {
        for ( Iterator<Listener<?>> iterator = listeners.iterator(); iterator.hasNext(); ) {
            Listener<Photon> listener = (Listener<Photon>) iterator.next();
            listener.photonEmitted( photon );
        }
    }
}
