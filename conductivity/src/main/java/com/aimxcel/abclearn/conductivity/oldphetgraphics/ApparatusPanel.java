package com.aimxcel.abclearn.conductivity.oldphetgraphics;

import javax.swing.*;

import com.aimxcel.abclearn.conductivity.oldphetgraphics.CompositeInteractiveGraphic;
import com.aimxcel.abclearn.conductivity.oldphetgraphics.Graphic;
import com.aimxcel.abclearn.conductivity.oldphetgraphics.GraphicsSetup;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ApparatusPanel extends JPanel implements Observer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicStroke borderStroke = new BasicStroke( 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
    CompositeInteractiveGraphic graphic = new CompositeInteractiveGraphic();
    ArrayList graphicsSetups = new ArrayList();

    public ApparatusPanel() {
        // Call superclass constructor with null so that we
        // don't get the default layout manager. This allows us
        // to lay out components with absolute coordinates
        super( null );
        this.addMouseListener( graphic );
        this.addMouseMotionListener( graphic );

        Graphic borderGraphic = new Graphic() {
            public void paint( Graphics2D g ) {
                Stroke origStroke = g.getStroke();
                Rectangle boundingRect = getBounds();
                g.setStroke( borderStroke );
                g.setColor( Color.black );
                g.drawRect( 2, 2,
                            (int)boundingRect.getWidth() - 4,
                            (int)boundingRect.getHeight() - 4 );
                g.setStroke( origStroke );
            }
        };
        addGraphic( borderGraphic, Double.POSITIVE_INFINITY );
    }

    public void addGraphicsSetup( GraphicsSetup setup ) {
        graphicsSetups.add( setup );
    }

    /**
     * Draws all the Graphic objects in the ApparatusPanel
     *
     * @param graphics
     */
    protected void paintComponent( Graphics graphics ) {
        Graphics2D g2 = (Graphics2D)graphics;
        super.paintComponent( g2 );
        for( int i = 0; i < graphicsSetups.size(); i++ ) {
            GraphicsSetup graphicsSetup = (GraphicsSetup)graphicsSetups.get( i );
            graphicsSetup.setup( g2 );
        }
        graphic.paint( g2 );
    }

    public void addGraphic( Graphic graphic, double level ) {
        this.graphic.addGraphic( graphic, level );
    }

    /**
     * Adds a graphic to the default layer 0.
     */
    public void addGraphic( Graphic graphic ) {
        this.graphic.addGraphic( graphic, 0 );
    }

    public void removeGraphic( Graphic graphic ) {
        this.graphic.remove( graphic );
    }

    public void update( Observable o, Object arg ) {
        repaint();
    }


}
