package com.aimxcel.abclearn.greenhouse.model;

import java.awt.geom.Point2D;
import java.util.Observable;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;

public class Thermometer extends Observable implements ModelElement {
    private TemperatureReporter temperatureReporter;
    private Point2D.Double location = new Point2D.Double();
    private double temperature;
    private static final int temperatureHistorySize = 1;
    private double[] temperatureHistory = new double[temperatureHistorySize];


    public Thermometer( TemperatureReporter temperatureReporter ) {
        this.temperatureReporter = temperatureReporter;
    }

    public void stepInTime( double dt ) {

        double sum = 0;
        for ( int i = temperatureHistory.length - 2; i >= 0; i-- ) {
            sum += temperatureHistory[i];
            temperatureHistory[i + 1] = temperatureHistory[i];
        }
        temperatureHistory[0] = temperatureReporter.getTemperature();
        temperature = sum / temperatureHistory.length;

        sum += temperatureHistory[0];
        temperature = sum / temperatureHistorySize;

        setChanged();
        notifyObservers( new Double( temperature ) );
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public void setLocation( Point2D.Double location ) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }
}
