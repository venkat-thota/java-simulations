package com.aimxcel.abclearn.greenhouse.filter;

public class ProbablisticPassFilter extends Filter1D {
    private double probability;

    public ProbablisticPassFilter( double probability ) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability( double probability ) {
        this.probability = probability;
    }

    public boolean passes( double value ) {
        return Math.random() <= probability;
    }
}
