

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package edu.colorado.phet.common.phetgraphics.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.abclearncommon.view.util.RectangleUtils;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import edu.colorado.phet.common.phetgraphics.view.phetcomponents.AbcLearnButton;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.GraphicLayerSet;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;

/**
 * User: Sam Reid
 * Date: Mar 2, 2005
 * Time: 2:26:36 PM
 */

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
        AbcLearnShapeGraphic background = new AbcLearnShapeGraphic( apparatusPanel,
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
            AbcLearnShapeGraphic phetShapeGraphic = new AbcLearnShapeGraphic( component,
                                                                      new Rectangle( 100, 100 ), Color.blue, new BasicStroke( 1 ), Color.black );
            addGraphic( phetShapeGraphic );
            AbcLearnButton phetButton = new AbcLearnButton( component, "Hello" );
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