package com.aimxcel.abclearn.greenhouse.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class PhotonEarthCollisionModel {

    private static MutableVector2D loa = new MutableVector2D();

    public static void handle( Photon photon, Earth earth ) {

        double separation = Math.abs( photon.getLocation().distance( earth.getLocation() ) );
        if ( separation <= Earth.radius ) {
            loa.setComponents( (float) ( photon.getLocation().getX() - earth.getLocation().getX() ),
                               (float) ( photon.getLocation().getY() - earth.getLocation().getY() ) );
            earth.absorbPhoton( photon );
        }

        if ( earth.getReflectivity( photon ) >= Math.random() ) {
            photon.setVelocity( photon.getVelocity().getX(), -photon.getVelocity().getY() );
        }
    }
}
