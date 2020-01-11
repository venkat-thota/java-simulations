// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.control;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;
import com.aimxcel.abclearn.platetectonics.model.CrustModel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

/**
 * Displays three sliders (temperature, composition and thickness) to control a piece of crust
 */
public class MyCrustPanel extends PNode {
    private static final AimxcelFont sliderTitleFont = new AimxcelFont( 14 );
    private static final AimxcelFont limitFont = new AimxcelFont( 10 );
    private final SliderNode temperatureSlider;
    private final SliderNode compositionSlider;
    private final SliderNode thicknessSlider;

    public MyCrustPanel( CrustModel model ) {
        PText titleNode = new PText( MY_CRUST ) {{
            setFont( new AimxcelFont( 16, true ) );
        }};

        temperatureSlider = new SliderNode( UserComponents.temperatureSlider, TEMPERATURE, COOL, WARM, model.temperatureRatio, 0, 1 );
        compositionSlider = new SliderNode( UserComponents.compositionSlider, COMPOSITION, MORE_IRON, MORE_SILICA, model.compositionRatio, 0, 1 );
        thicknessSlider = new SliderNode( UserComponents.thicknessSlider, THICKNESS, THIN, THICK, model.thickness, 4000, 70000 );

        // center the title node (based on the slider itself, not the other labels!)
        titleNode.setOffset( ( temperatureSlider.getSlider().getWidth() - titleNode.getFullBounds().getWidth() ) / 2, 0 );

        // position sliders below
        temperatureSlider.setOffset( 0, titleNode.getFullBounds().getMaxY() + 10 );
        compositionSlider.setOffset( 0, temperatureSlider.getFullBounds().getMaxY() + 10 );
        thicknessSlider.setOffset( 0, compositionSlider.getFullBounds().getMaxY() + 10 );

        addChild( titleNode );
        addChild( temperatureSlider );
        addChild( compositionSlider );
        addChild( thicknessSlider );
    }

    public void resetAll() {
        // properties handled in the LWJGL thread
        temperatureSlider.getProperty().reset();
        compositionSlider.getProperty().reset();
        thicknessSlider.getProperty().reset();

        final int temp = temperatureSlider.getInitialValue();
        final int composition = compositionSlider.getInitialValue();
        final int thickness = thicknessSlider.getInitialValue();

        // Swing changes in the Swing EDT
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                temperatureSlider.setValue( temp );
                compositionSlider.setValue( composition );
                thicknessSlider.setValue( thickness );
            }
        } );
    }

    private static class SliderNode extends PNode {
        private JSlider slider;
        private final Property<Double> property;
        private final double min;
        private final double max;

        private static final int sliderMax = 4096;

        private SliderNode( IUserComponent userComponent, String title, String lowText, String highText, final Property<Double> property, double min, double max ) {
            this.property = property;
            this.min = min;
            this.max = max;
            PText titleNode = new PText( title ) {{ setFont( sliderTitleFont ); }};
            PText lowNode = new PText( lowText ) {{ setFont( limitFont ); }};
            PText highNode = new PText( highText ) {{ setFont( limitFont ); }};
            slider = new SimSharingJSlider( userComponent, 0, sliderMax, getInitialValue() ) {{
                setPaintTicks( true );
            }};
            PSwing sliderNode = new PSwing( slider );

            // center the title node
            titleNode.setOffset( ( sliderNode.getFullBounds().getWidth() - titleNode.getFullBounds().getWidth() ) / 2,
                                 0 );

            // put the slider below the title
            sliderNode.setOffset( 0,
                                  titleNode.getFullBounds().getMaxY() );

            // put the low text centered under the left boundary
            lowNode.setOffset( -lowNode.getFullBounds().getWidth() / 2,
                               sliderNode.getFullBounds().getMaxY() - 5 );

            // put the high text centered under the right boundary
            highNode.setOffset( sliderNode.getFullBounds().getWidth() - highNode.getFullBounds().getWidth() / 2,
                                sliderNode.getFullBounds().getMaxY() - 5 );

            addChild( titleNode );
            addChild( lowNode );
            addChild( highNode );
            addChild( sliderNode );

            // listen to model changes
            slider.addChangeListener( new ChangeListener() {
                public void stateChanged( ChangeEvent e ) {
                    // access the value in the Swing EDT
                    final double value = getValue();

                    // invoke the model change in the LWJGL thread
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            property.set( value );
                        }
                    } );
                }
            } );
        }

        public int getInitialValue() {
            double ratio = ( property.get() - min ) / ( max - min );
            return (int) ( ratio * sliderMax );
        }

        private double getValue() {
            double ratio = ( (double) slider.getValue() ) / ( (double) sliderMax );
            return min + ratio * ( max - min );
        }

        public void setValue( int value ) {
            slider.setValue( value );
        }

        public JSlider getSlider() {
            return slider;
        }

        public Property<Double> getProperty() {
            return property;
        }
    }
}
