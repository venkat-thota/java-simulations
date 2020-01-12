package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import java.awt.*;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;

public class BatteryPainter implements Painter {
    Painter current;
    Painter left;
    Painter right;
    Painter leftTransparent;
    Painter rightTransparent;
    boolean isLeft;
    boolean transp;

    public BatteryPainter( Painter left, Painter right, Painter leftTransparent, Painter rightTransparent ) {
        this.left = left;
        this.right = right;
        this.leftTransparent = leftTransparent;
        this.rightTransparent = rightTransparent;
        chooseImage();
    }

    public void setTransparent( boolean transp ) {
        this.transp = transp;
        chooseImage();
    }

    public void setLeft( boolean left ) {
        this.isLeft = left;
        chooseImage();
    }

    private void chooseImage() {
        if ( !transp && isLeft ) {
            current = left;
        }
        else if ( !transp && !isLeft ) {
            current = right;
        }
        else if ( transp && isLeft ) {
            current = leftTransparent;
        }
        else {
            current = rightTransparent;
        }
    }

    public void paint( Graphics2D g ) {
        current.paint( g );
    }
}
