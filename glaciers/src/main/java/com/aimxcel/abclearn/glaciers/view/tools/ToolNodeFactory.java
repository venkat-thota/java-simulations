
package com.aimxcel.abclearn.glaciers.view.tools;

import com.aimxcel.abclearn.glaciers.model.*;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;


public class ToolNodeFactory {

    private ToolNodeFactory() {}
   
    public static AbstractToolNode createNode( AbstractTool tool, GlaciersModel model, GlaciersModelViewTransform mvt, TrashCanDelegate trashCan, boolean englishUnits ) {
        AbstractToolNode node = null;
        if ( tool instanceof Thermometer ) {
            node = new ThermometerNode( (Thermometer) tool, model.getGlacier(), mvt, trashCan );
        }
        else if ( tool instanceof GlacialBudgetMeter ) {
            node = new GlacialBudgetMeterNode( (GlacialBudgetMeter) tool,  model.getGlacier(), mvt, trashCan, englishUnits );
        }
        else if ( tool instanceof TracerFlag ) {
            node = new TracerFlagNode( (TracerFlag) tool, mvt, trashCan );
        }
        else if ( tool instanceof IceThicknessTool ) {
            node = new IceThicknessToolNode( (IceThicknessTool) tool, mvt, trashCan, englishUnits );
        }
        else if ( tool instanceof BoreholeDrill ) {
            node = new BoreholeDrillNode( (BoreholeDrill) tool, mvt, trashCan );
        }
        else if ( tool instanceof GPSReceiver ) {
            node = new GPSReceiverNode( (GPSReceiver) tool, mvt, trashCan, englishUnits );
        }
        else {
            throw new UnsupportedOperationException( "no node for tool type " + tool.getClass() );
        }
        
        return node;
    }
}
