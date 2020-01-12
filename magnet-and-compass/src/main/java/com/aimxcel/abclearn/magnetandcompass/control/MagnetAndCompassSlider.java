

package com.aimxcel.abclearn.magnetandcompass.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;



public class MagnetAndCompassSlider extends GraphicSlider {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    private static final int DEFAULT_TRACK_WIDTH = 2;
    private static final Color DEFAULT_TRACK_COLOR = Color.BLACK;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Creates a slider with a specified track length.
     * Defaults are used for the track width and color.
     *
     * @param userComponent sim-sharing user component
     * @param component     the parent component
     * @param trackLength   the track length, in pixels
     */
    public MagnetAndCompassSlider( IUserComponent userComponent, Component component, int trackLength ) {
        this( userComponent, component, trackLength, DEFAULT_TRACK_WIDTH, DEFAULT_TRACK_COLOR );
    }

    /**
     * Creates a slider with a specified track length, width and color.
     *
     * @param userComponent sim-sharing user component
     * @param component     the parent component
     * @param trackLength   the track length, in pixels
     * @param trackWidth    the track width, in pixels
     * @param trackColor    the track color
     */
    public MagnetAndCompassSlider( IUserComponent userComponent, Component component, int trackLength, int trackWidth, Color trackColor ) {
        super( userComponent, component );

        assert ( trackLength > 0 );

        // Background - none

        // Track
        Shape shape = new Rectangle( 0, 0, trackLength, trackWidth );
        AimxcelGraphic track = new AimxcelShapeGraphic( component, shape, trackColor );
        setTrack( track );

        // Knob
        BufferedImage knobImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.SLIDER_KNOB_IMAGE );
        AimxcelGraphic knob = new AimxcelImageGraphic( component, knobImage );
        knob.centerRegistrationPoint();
        setKnob( knob );

        // Knob Highlight
        BufferedImage knobHighlightImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.SLIDER_KNOB_HIGHLIGHT_IMAGE );
        AimxcelGraphic knobHighlight = new AimxcelImageGraphic( component, knobHighlightImage );
        knobHighlight.centerRegistrationPoint();
        setKnobHighlight( knobHighlight );
    }
}
