package com.aimxcel.abclearn.greenhouse.util;

public class ModelViewTx1D {
    private double modelMin;
    private int viewMin;
    private double m;

    public ModelViewTx1D( double modelValue1, double modelValue2, int viewValue1, int viewValue2 ) {

        this.modelMin = modelValue1;
        this.viewMin = viewValue1;
        m = ( (double) ( viewValue2 - viewValue1 ) ) / ( modelValue2 - modelValue1 );

    }

    public double viewToModel( int view ) {
        double model = modelMin + ( (double) ( view - viewMin ) ) / m;
        return model;
    }

    public double modelToView( double model ) {
        int view = viewMin + (int) ( m * ( model - modelMin ) );
        return view;
    }
}
