
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import com.aimxcel.abclearn.aimxceljfreechart.core.JFreeChartCursorNode;


public class TestJFreeChartCursorNode extends TestDynamicJFreeChartNodeTree {

    public TestJFreeChartCursorNode() {
        JFreeChartCursorNode jFreeChartCursorNode = new JFreeChartCursorNode( getDynamicJFreeChartNode() );
        getAimxcelPCanvas().addScreenChild( jFreeChartCursorNode );
    }

    public static void main( String[] args ) {
        new TestJFreeChartCursorNode().start();
    }

}
