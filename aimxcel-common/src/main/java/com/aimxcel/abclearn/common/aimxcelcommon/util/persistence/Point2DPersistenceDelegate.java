
 package com.aimxcel.abclearn.common.aimxcelcommon.util.persistence;

import java.awt.geom.Point2D;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Statement;


public class Point2DPersistenceDelegate extends DefaultPersistenceDelegate {

    protected void initialize( Class type, Object oldInstance, Object newInstance, Encoder out ) {
        Point2D point = (Point2D) oldInstance;
        out.writeStatement( new Statement( oldInstance,
                                           "setLocation",
                                           new Object[] { new Double( point.getX() ), new Double( point.getY() ) } ) );
    }
}
