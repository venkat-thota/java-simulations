package com.aimxcel.abclearn.greenhouse.model;


public interface PhotonAbsorber {

    void addListener( PhotonAbsorber.Listener listener );

    void removeListener( PhotonAbsorber.Listener listener );

    void absorbPhoton( Photon photon );

    //
    // Inner classes
    //
    public interface Listener {
        void photonAbsorbed( Photon photon );
    }
}
