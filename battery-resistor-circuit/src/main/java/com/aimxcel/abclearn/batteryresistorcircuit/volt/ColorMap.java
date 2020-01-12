package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import java.awt.*;

public interface ColorMap {
    public Color toColor( double ratio );

    public boolean isChanging();
}
