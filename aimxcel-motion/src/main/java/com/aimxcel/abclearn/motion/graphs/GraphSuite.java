
package com.aimxcel.abclearn.motion.graphs;


public class GraphSuite {
    private MinimizableControlGraph[] minimizableControlGraphs;

    public GraphSuite( MinimizableControlGraph[] minimizableControlGraphs ) {
        this.minimizableControlGraphs = minimizableControlGraphs;
    }

    public MinimizableControlGraph getGraphComponent( int i ) {
        return minimizableControlGraphs[i];
    }

    public int getGraphComponentCount() {
        return minimizableControlGraphs.length;
    }

    public String getLabel() {
//        String str = "<html>";
        String str = "";
        for ( int i = 0; i < minimizableControlGraphs.length; i++ ) {
            MinimizableControlGraph minimizableControl = minimizableControlGraphs[i];
            str += minimizableControl.getLabel();
            if ( i < minimizableControlGraphs.length - 1 ) {
                str += ",";
            }
        }
        return str;
//        return str + "</html>";
    }
}
