package com.aimxcel.abclearn.common.aimxcelcommon.math;

import com.aimxcel.abclearn.common.aimxcelcommon.math.PlaneD;
import com.aimxcel.abclearn.common.aimxcelcommon.math.Ray3D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3D;

public class PlaneD {
    public final Vector3D normal;
    public final double distance;

    public static final PlaneD XY = new PlaneD( Vector3D.Z_UNIT, 0 );
    public static final PlaneD XZ = new PlaneD( Vector3D.Y_UNIT, 0 );
    public static final PlaneD YZ = new PlaneD( Vector3D.X_UNIT, 0 );

    public PlaneD( Vector3D normal, double distance ) {
        this.normal = normal;
        this.distance = distance;
    }

    public Vector3D intersectWithRay( Ray3D ray ) {
        return ray.pointAtDistance( ray.distanceToPlane( this ) );
    }

    // NOTE: will return null if points are collinear
    public static PlaneD fromTriangle( Vector3D a, Vector3D b, Vector3D c ) {
        Vector3D normal = ( c.minus( a ) ).cross( b.minus( a ) );
        if ( normal.magnitude() == 0 ) {
            return null;
        }
        normal = normal.normalized();

        return new PlaneD( normal, normal.dot( a ) );
    }
}
