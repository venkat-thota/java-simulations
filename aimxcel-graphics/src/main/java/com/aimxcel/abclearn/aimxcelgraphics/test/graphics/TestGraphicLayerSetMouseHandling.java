
package com.aimxcel.abclearn.aimxcelgraphics.test.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelMultiLineTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
public class TestGraphicLayerSetMouseHandling extends JFrame {

    /**
     * main
     *
     * @param args command line arguments
     */
    public static void main( String[] args ) {
        TestGraphicLayerSetMouseHandling frame = new TestGraphicLayerSetMouseHandling();
        frame.setVisible( true );
    }

    /**
     * Constructor. Builds the JFrame and ApparatusPanel.
     */
    public TestGraphicLayerSetMouseHandling() {

        // Set up the frame.
        ApparatusPanel apparatusPanel = new ApparatusPanel();
        setContentPane( apparatusPanel );
        setSize( 500, 500 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Instructions
        String[] lines = {
                "The RED circle can be dragged independently of everything.",
                "Dragging the GREEN circle also drags the red circle.",
                "Dragging the YELLOW square moves all 4 shapes.",
                "The BLUE square ignores mouse events."
        };
        Font font = new Font( null, Font.PLAIN, 14 );
        AimxcelMultiLineTextGraphic help = new AimxcelMultiLineTextGraphic( apparatusPanel, font, lines, Color.BLACK );
        help.setLocation( 10, 10 );
        help.setRenderingHints( new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON ) );
        apparatusPanel.addGraphic( help );

        // Draggable graphic with parts that are independently draggable.
        ParentGraphic parentGraphic = new ParentGraphic( apparatusPanel );
        parentGraphic.setLocation( 200, 250 );
        apparatusPanel.addGraphic( parentGraphic );
    }

    /**
     * ParentGraphic is the toplevel graphic that is placed on the apparatus panel.
     * It is draggable, and has some parts that are independently draggable
     * (namely, the green & red circles).
     *
     * @author Chris Malley (cmalley@pixelzoom.com)
     * @version $Revision$
     */
    private class ParentGraphic extends GraphicLayerSet {

        public ParentGraphic( Component component ) {

            // Draggable graphic with a child that is independently draggable.
            ChildGraphic child = new ChildGraphic( component );
            setLocation( 0, 0 );
            addGraphic( child );

            // Blue rectangle, not draggable, allows picking through it.
            AimxcelShapeGraphic rectangle1 = new AimxcelShapeGraphic( component,
                                                                new Rectangle( 0, 0, 50, 50 ), Color.BLUE, new BasicStroke( 1 ), Color.BLACK );
            rectangle1.setRegistrationPoint( 25, 25 ); // center
            rectangle1.setIgnoreMouse( true );
            rectangle1.setLocation( -30, 0 );
            addGraphic( rectangle1 );

            // Blue rectangle, not draggable, does NOT allow picking through it.
            AimxcelShapeGraphic rectangle2 = new AimxcelShapeGraphic( component,
                                                                new Rectangle( 0, 0, 50, 50 ), Color.YELLOW, new BasicStroke( 1 ), Color.BLACK );
            rectangle2.setRegistrationPoint( 25, 25 ); // center
            rectangle2.setIgnoreMouse( false );
            rectangle2.setLocation( 30, 0 );
            addGraphic( rectangle2 );

            // The entire graphic can be dragged.
            setCursorHand();
            addTranslationListener( new TranslationListener() {
                public void translationOccurred( TranslationEvent translationEvent ) {
                    translate( translationEvent.getDx(), translationEvent.getDy() );
                }
            } );
        }
    }

    /**
     * ChildGraphic is a child of ParentGraphic.
     * It contains the red & green circles.
     * The red circle can be dragged independently.
     *
     * @author Chris Malley (cmalley@pixelzoom.com)
     * @version $Revision$
     */
    private class ChildGraphic extends GraphicLayerSet {

        public ChildGraphic( Component component ) {
            super( component );

            // Green circle, not draggable.
            AimxcelShapeGraphic background = new AimxcelShapeGraphic( component,
                                                                new Ellipse2D.Double( 0, 0, 200, 200 ), Color.GREEN, new BasicStroke( 1 ), Color.BLACK );
            background.setRegistrationPoint( 100, 100 ); // center
            background.setLocation( 0, 0 );
            addGraphic( background );

            // Red circle, draggable.
            final AimxcelShapeGraphic control = new AimxcelShapeGraphic( component,
                                                                   new Ellipse2D.Double( 0, 0, 50, 50 ), Color.RED, new BasicStroke( 1 ), Color.BLACK );
            control.setRegistrationPoint( 25, 25 ); // center
            control.setLocation( 0, -25 );
            addGraphic( control );

            // The entire graphic can be dragged.
            setCursorHand();
            addTranslationListener( new TranslationListener() {
                public void translationOccurred( TranslationEvent translationEvent ) {
                    translate( translationEvent.getDx(), translationEvent.getDy() );
                }
            } );

            // ...or just the control can be dragged.
            control.setCursorHand();
            control.addTranslationListener( new TranslationListener() {
                public void translationOccurred( TranslationEvent translationEvent ) {
                    control.translate( translationEvent.getDx(), translationEvent.getDy() );
                }
            } );
        }
    }
}