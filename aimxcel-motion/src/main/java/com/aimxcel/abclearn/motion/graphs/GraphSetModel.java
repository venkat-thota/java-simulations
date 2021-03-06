
package com.aimxcel.abclearn.motion.graphs;

import java.util.ArrayList;



public class GraphSetModel {
    private GraphSuite graphSuite;
    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    public GraphSetModel( GraphSuite graphSuite ) {
        this.graphSuite = graphSuite;
    }

    public void setGraphSuite( GraphSuite graphSuite ) {
        //todo can't check for same state because of radio button listeners.
        this.graphSuite = graphSuite;
        notifyListeners();
    }

    public GraphSuite getGraphSuite() {
        return graphSuite;
    }

    public static interface Listener {
        void graphSuiteChanged();
    }

    public void addListener( GraphSetModel.Listener listener ) {
        listeners.add( listener );
    }

    public void notifyListeners() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            GraphSetModel.Listener listener = (GraphSetModel.Listener) listeners.get( i );
            listener.graphSuiteChanged();
        }
    }
}
