
 package com.aimxcel.abclearn.aimxcelgraphics.view.help;

import java.awt.Component;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;


public class HelpManager extends GraphicLayerSet {
    private static double HELP_LAYER = Double.POSITIVE_INFINITY;

    public HelpManager() {
        super( null );
    }

    public HelpManager( Component component ) {
        super( component );
    }

    public void setComponent( Component component ) {
        super.setComponent( component );
    }

    /**
     * @param helpItem
     * @deprecated use removeGraphic
     */
    public void removeHelpItem( HelpItem helpItem ) {
        super.removeGraphic( helpItem );
    }

    /**
     * @param item
     * @deprecated use addGraphic
     */
    public void addHelpItem( HelpItem item ) {
        super.addGraphic( item, HELP_LAYER );
    }

    public void setHelpEnabled( ApparatusPanel apparatusPanel, boolean h ) {
        if ( h ) {
            apparatusPanel.addGraphic( this, HELP_LAYER );
        }
        else {
            apparatusPanel.removeGraphic( this );
        }
        apparatusPanel.repaint();
    }

    public int getNumHelpItems() {
        return super.getNumGraphics();
    }

}
