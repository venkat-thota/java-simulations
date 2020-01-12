package com.aimxcel.abclearn.greenhouse.model;


public class BasicPhotonAbsorber extends AbstractPhotonAbsorber {

    public void stepInTime( double dt ) {
    }

    // Every photon is removed
    public void absorbPhoton( Photon photon ) {
        super.notifyListeners( photon );
    }
}
