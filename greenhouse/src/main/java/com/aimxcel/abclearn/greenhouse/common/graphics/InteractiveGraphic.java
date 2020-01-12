package com.aimxcel.abclearn.greenhouse.common.graphics;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public interface InteractiveGraphic extends Graphic {


    boolean canHandleMousePress( MouseEvent event, Point2D.Double modelLoc );

    void mouseDragged( MouseEvent event, Point2D.Double modelLoc );

    void mousePressed( MouseEvent event, Point2D.Double modelLoc );

    void mouseReleased( MouseEvent event, Point2D.Double modelLoc );

    void mouseEntered( MouseEvent event, Point2D.Double modelLoc );

    void mouseExited( MouseEvent event, Point2D.Double modelLoc );
}
