
 package com.aimxcel.abclearn.aimxcelgraphics.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetcomponents.AimxcelButton;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;


public class EmbeddedControlTest {
    JFrame frame = new JFrame();
    ApparatusPanel apparatusPanel = new ApparatusPanel();

    public EmbeddedControlTest() {
        final GraphicLayerSet root = new GraphicLayerSet( apparatusPanel );
        TranslationListener translationListener = new TranslationListener() {
            public void translationOccurred( TranslationEvent translationEvent ) {
                root.translate( translationEvent.getDx(), translationEvent.getDy() );
            }
        };
        BattGraphic battGraphic = new BattGraphic( apparatusPanel, translationListener );
        AimxcelShapeGraphic background = new AimxcelShapeGraphic( apparatusPanel,
                                                            RectangleUtils.expand( new Rectangle( battGraphic.getBounds() ), 15, 15 ), Color.green, new BasicStroke( 1 ), Color.black );
        background.addTranslationListener( translationListener );
        root.addGraphic( background );
        root.addGraphic( battGraphic );

        frame.setContentPane( apparatusPanel );
        frame.setSize( 800, 800 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        apparatusPanel.addGraphic( root );
    }

    static class BattGraphic extends GraphicLayerSet {
        public BattGraphic( Component component, TranslationListener dragHandler ) {
            super( component );
            AimxcelShapeGraphic phetShapeGraphic = new AimxcelShapeGraphic( component,
                                                                      new Rectangle( 100, 100 ), Color.blue, new BasicStroke( 1 ), Color.black );
            addGraphic( phetShapeGraphic );
            AimxcelButton phetButton = new AimxcelButton( component, "Hello" );
            addGraphic( phetButton );
            phetButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    System.out.println( "e = " + e );
                }
            } );
            phetShapeGraphic.addTranslationListener( dragHandler );
        }
    }

    public static void main( String[] args ) {
        new EmbeddedControlTest().start();
    }

    private void start() {
        frame.setVisible( true );
    }
}