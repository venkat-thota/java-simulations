// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.control;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.PANEL_TITLE_FONT;

import javax.swing.*;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.Spacer;
import com.aimxcel.abclearn.lwjgl.utils.GLSimSharingPropertyCheckBox;
import com.aimxcel.abclearn.lwjgl.utils.GLSimSharingPropertyRadioButton;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

/**
 * Gives the user a list of "View" options
 */
public class ViewOptionsPanel extends PNode {
    public ViewOptionsPanel( final Property<Boolean> showLabels, Property<ColorMode> colorMode ) {
        this( showLabels, false, new Property<Boolean>( false ), new Property<Boolean>( false ), colorMode );
    }

    public ViewOptionsPanel( final Property<Boolean> showLabels,
                             final boolean containsWaterOption,
                             final Property<Boolean> showWater,
                             final Property<Boolean> showWaterEnabled,
                             final Property<ColorMode> colorMode ) {
        final PNode title = new PText( PlateTectonicsResources.Strings.OPTIONS ) {{
            setFont( PANEL_TITLE_FONT );
        }};
        addChild( title );

        final Property<Double> maxWidth = new Property<Double>( title.getFullBounds().getWidth() );
        final Property<Double> y = new Property<Double>( title.getFullBounds().getMaxY() );

        // "Density" radio button
        final PSwing densityMode = new PSwing( new GLSimSharingPropertyRadioButton<ColorMode>( UserComponents.densityView, Strings.DENSITY_VIEW, colorMode, ColorMode.DENSITY ) ) {{
            setOffset( 0, y.get() + 5 );
            y.set( getFullBounds().getMaxY() );
            maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
        }};
        addChild( densityMode );

        // "Temperature" radio button
        final PSwing temperatureMode = new PSwing( new GLSimSharingPropertyRadioButton<ColorMode>( UserComponents.temperatureView, Strings.TEMPERATURE_VIEW, colorMode, ColorMode.TEMPERATURE ) ) {{
            setOffset( 0, y.get() );
            y.set( getFullBounds().getMaxY() );
            maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
        }};
        addChild( temperatureMode );

        // "Both" radio button
        final PSwing combinedMode = new PSwing( new GLSimSharingPropertyRadioButton<ColorMode>( UserComponents.bothView, Strings.BOTH_VIEW, colorMode, ColorMode.COMBINED ) ) {{
            setOffset( 0, y.get() );
            y.set( getFullBounds().getMaxY() );
            maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
        }};
        addChild( combinedMode );

        y.set( y.get() + 5 );

        // "Show Labels" check box
        final PSwing showLabelCheckBox = new PSwing( new GLSimSharingPropertyCheckBox( UserComponents.showLabels, Strings.SHOW_LABELS, showLabels ) ) {{
            setOffset( 0, y.get() );
            y.set( getFullBounds().getMaxY() );
            maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
        }};
        addChild( showLabelCheckBox );

        // "Show Seawater" check box
        if ( containsWaterOption ) {
            addChild( new PSwing( new GLSimSharingPropertyCheckBox( UserComponents.showWater, Strings.SHOW_SEAWATER, showWater ) {{
                showWaterEnabled.addObserver( new SimpleObserver() {
                    public void update() {
                        // access property in the LWJGL thread
                        final Boolean enabled = showWaterEnabled.get();

                        // and set the swing property in the Swing EDT
                        SwingUtilities.invokeLater( new Runnable() {
                            public void run() {
                                setEnabled( enabled );
                            }
                        } );
                    }
                } );
            }} ) {{
                setOffset( 0, y.get() );
                y.set( getFullBounds().getMaxY() );
                maxWidth.set( Math.max( maxWidth.get(), getFullBounds().getWidth() ) );
            }} );
        }

        // this prevents panel resizing when the button bounds change (like when they are pressed)
        addChild( new Spacer( 0, y.get(), 1, 1 ) );

        // horizontally center title
        title.setOffset( ( maxWidth.get() - title.getFullBounds().getWidth() ) / 2, title.getYOffset() );
    }

}
