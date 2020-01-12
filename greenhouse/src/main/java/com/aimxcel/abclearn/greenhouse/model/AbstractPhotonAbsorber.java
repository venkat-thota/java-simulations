package com.aimxcel.abclearn.greenhouse.model;

import java.util.ArrayList;
import java.util.Observable;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;

public abstract class AbstractPhotonAbsorber extends Observable implements ModelElement, PhotonAbsorber {

    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    public void addListener( PhotonAbsorber.Listener listener ) {
        listeners.add( listener );
    }

    public void removeListener( PhotonAbsorber.Listener listener ) {
        listeners.remove( listener );
    }

    protected void notifyListeners( Photon photon ) {
        for ( int j = 0; j < listeners.size(); j++ ) {
            Listener listener = (Listener) listeners.get( j );
            listener.photonAbsorbed( photon );
        }
    }
}
