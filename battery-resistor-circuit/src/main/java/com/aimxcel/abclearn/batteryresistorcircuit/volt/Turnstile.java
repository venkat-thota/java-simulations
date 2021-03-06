package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;

public class Turnstile implements Painter, Law, CurrentListener {
    BufferedImage image;
    double angle;
    double angularSpeed;
    int width;
    int height;
    AffineTransform centerTrf;
    double angVelScale;

    public Turnstile( Point center, BufferedImage image, double angVelScale ) {
        this.angVelScale = angVelScale;
        width = image.getWidth();
        height = image.getHeight();
        centerTrf = AffineTransform.getTranslateInstance( center.x, center.y );
        this.image = image;
        angularSpeed = .31;
    }

    //Want to model position as well as angular velocity easily.
    /*This class could observe electrons only,
    or electrons and current,
    or current only...*/
    public void paint( Graphics2D g ) {
        //System.err.println("Angle="+angle);
        AffineTransform rotation = AffineTransform.getRotateInstance( angle, width / 2, height / 2 );
        AffineTransform total = new AffineTransform();
        total.concatenate( centerTrf );
        total.concatenate( rotation );
        g.drawRenderedImage( image, total );
    }

    public void iterate( double dt, System2D system2D ) {
        angle = angularSpeed * dt + angle;
    }

    public void currentChanged( double a ) {
        this.angularSpeed = a * angVelScale;
    }
}
