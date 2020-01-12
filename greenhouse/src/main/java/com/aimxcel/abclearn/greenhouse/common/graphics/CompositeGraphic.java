package com.aimxcel.abclearn.greenhouse.common.graphics;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;


public class CompositeGraphic implements InteractiveGraphic {

    // The map of graphic objects to be drawn in the panel
    private TreeMap graphicLayers = new TreeMap();
    private InteractiveGraphic selectedGraphic;
    private InteractiveGraphic mouseEnteredGraphic;
    private List inorder = new ArrayList();

    /**
     *
     */
    public CompositeGraphic() {
    }

    /**
     * Clears objects in the graphical context that are experiment-specific
     */
    public void removeAllGraphics() {
        graphicLayers.clear();
        recreateList();
    }

    /**
     * Draws all the Graphic objects in the ApparatusPanel
     */
    public void paint( Graphics2D g2 ) {
        AffineTransform orgTx = g2.getTransform();
        for ( int i = 0; i < inorder.size(); i++ ) {
            Graphic graphic = (Graphic) inorder.get( i );
            graphic.paint( g2 );
        }
        g2.setTransform( orgTx );
    }

    /**
     * @param graphic
     * @param level
     */
    public void addGraphic( Graphic graphic, double level ) {

        Double levelKey = new Double( level );
        Collection layer = (Collection) graphicLayers.get( levelKey );
        if ( layer == null ) {
            layer = new ArrayList( 10 );
            graphicLayers.put( levelKey, layer );
        }

        synchronized( graphicLayers ) {
            layer.add( graphic );
        }
        recreateList();
    }

    private void recreateList() {
        this.inorder = this.getElementsInOrder();
    }

    /**
     * Removes the specified paintable from the specified level.
     */
    public void removeGraphic( Graphic graphic ) {
        synchronized( graphicLayers ) {
            for ( Iterator layerIt = graphicLayers.values().iterator(); layerIt.hasNext(); ) {
                Collection layer = (Collection) layerIt.next();
                ArrayList removeList = new ArrayList();
                for ( Iterator graphicIt = layer.iterator();
                      graphicIt.hasNext(); ) {
                    Graphic testGraphic = (Graphic) graphicIt.next();
                    if ( testGraphic == graphic ) {
                        removeList.add( testGraphic );
                    }
                }
                layer.removeAll( removeList );
            }
        }
        recreateList();
    }

    /**
     * Returns a list of the elements in all the layers, in order of their
     * painting order
     *
     * @return
     */
    private List getElementsInOrder() {
        ArrayList elements = new ArrayList();
        Collection layers = graphicLayers.values();
        for ( Iterator layerIt = layers.iterator(); layerIt.hasNext(); ) {
            elements.addAll( (ArrayList) layerIt.next() );
        }
        return elements;
    }

    //
    // New versions of mouse handlers
    public void mousePressed( MouseEvent event, Point2D.Double modelLoc ) {
        if ( selectedGraphic == null ) {
            selectedGraphic = determineMouseHandler( event, modelLoc );
            if ( selectedGraphic != null ) {
                selectedGraphic.mousePressed( event, modelLoc );
            }
        }
    }

    private InteractiveGraphic determineMouseHandler( MouseEvent event, Point2D.Double modelLoc ) {
        InteractiveGraphic ig = null;
        synchronized( graphicLayers ) {
            List elements = this.getElementsInOrder();
            for ( int i = elements.size() - 1; i >= 0; i-- ) {

                Graphic graphic = (Graphic) elements.get( i );

                if ( graphic instanceof InteractiveGraphic
                     && ( (InteractiveGraphic) graphic ).canHandleMousePress( event, modelLoc ) ) {
                    ig = (InteractiveGraphic) graphic;
                    if ( ig instanceof CompositeGraphic ) {
                        ig = ( (CompositeGraphic) ig ).determineMouseHandler( event, modelLoc );
                    }
                    break;
                }
            }
        }
        return ig;
    }

    public void mouseDragged( MouseEvent event, Point2D.Double modelLoc ) {
        if ( selectedGraphic != null ) {
            selectedGraphic.mouseDragged( event, modelLoc );
        }
    }

    public void mouseReleased( MouseEvent event, Point2D.Double modelLoc ) {
        if ( selectedGraphic != null ) {
            selectedGraphic.mouseReleased( event, modelLoc );
        }
        selectedGraphic = null;
    }

    public void mouseEntered( MouseEvent event, Point2D.Double modelLoc ) {
        InteractiveGraphic newIG = determineMouseHandler( event, modelLoc );
        // If the mouse is still over an interactive graphic, tell him
        if ( newIG != null && newIG != mouseEnteredGraphic ) {
            mouseEnteredGraphic = newIG;
            mouseEnteredGraphic.mouseEntered( event, modelLoc );
        }
    }

    public void mouseExited( MouseEvent event, Point2D.Double modelLoc ) {
        if ( mouseEnteredGraphic != null ) {
            mouseEnteredGraphic.mouseExited( event, modelLoc );
            mouseEnteredGraphic = null;
        }
    }

    public boolean canHandleMousePress( MouseEvent event, Point2D.Double modelLoc ) {
        return determineMouseHandler( event, modelLoc ) != null;
    }

    public void mouseMoved( MouseEvent e, Point2D.Double modelLoc ) {
        // If the interactive graphic that the mouse is over is
        // has changed, then note it.
        InteractiveGraphic newIG = determineMouseHandler( e, modelLoc );

        //Moving off of a graphic to no graphic.
        if ( mouseEnteredGraphic != null && newIG == null ) {
            mouseEnteredGraphic.mouseExited( e, modelLoc );
            mouseEnteredGraphic = null;
        }
        //Moving from one graphic to another.
        else if ( mouseEnteredGraphic != null && newIG != null && mouseEnteredGraphic != newIG ) {
            mouseEnteredGraphic.mouseExited( e, modelLoc );
            mouseEnteredGraphic = newIG;
            mouseEnteredGraphic.mouseEntered( e, modelLoc );
        }
        //Moved to something from nothing.
        else if ( newIG != null && mouseEnteredGraphic == null ) {
            mouseEnteredGraphic = newIG;
            mouseEnteredGraphic.mouseEntered( e, modelLoc );
        }
    }

    public void moveToTop( Graphic target ) {
        removeGraphic( target );
        addGraphic( target, Double.POSITIVE_INFINITY );
    }
}
