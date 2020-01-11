
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;


public class SeriesData {
    private String title;
    private Color color;
    private Stroke stroke;
    private XYSeries series;
    private ArrayList listeners = new ArrayList();
    private static int index = 0;
    private boolean visible = true;

    public SeriesData( String title, Color color, Stroke stroke ) {
        this( title, color, new XYSeries( title + " " + ( index++ ), false, true ), stroke );
    }

    public SeriesData( String title, Color color, XYSeries series, Stroke stroke ) {
        this.title = title;
        this.color = color;
        this.series = series;
        this.stroke = stroke;
    }

    public String getTitle() {
        return title;
    }

    public Color getColor() {
        return color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public XYSeries getSeries() {
        return series;
    }

    public void addValue( double time, double value ) {
        series.add( time, value );
        notifyDataAdded();
    }

    public void removeListener( Listener listener ) {
        listeners.remove( listener );
    }

    public void clear() {
        if ( series.getItemCount() > 0 ) {
            series.clear();
            notifyDataCleared();
        }
    }

    private void notifyDataCleared() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).dataCleared();
        }
    }

    public void setVisible( boolean visible ) {
        if ( this.visible != visible ) {
            this.visible = visible;
            notifyVisibilityChanged();
        }
    }

    private void notifyVisibilityChanged() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).visibilityChanged();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setStroke( Stroke stroke ) {
        this.stroke = stroke;
    }

    public static interface Listener {
        void dataAdded();

        void dataCleared();

        void visibilityChanged();
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void notifyDataAdded() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).dataAdded();
        }
    }
}
