

package edu.colorado.phet.faraday.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;

import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnImageGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;
import edu.colorado.phet.faraday.FaradayConstants;
import edu.colorado.phet.faraday.FaradayResources;


/**
 * FaradaySlider is the graphic slider used throughout Faraday.
 * It has a knob, a knob hightlight, and a track, with no background.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class FaradaySlider extends GraphicSlider {

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
    public FaradaySlider( IUserComponent userComponent, Component component, int trackLength ) {
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
    public FaradaySlider( IUserComponent userComponent, Component component, int trackLength, int trackWidth, Color trackColor ) {
        super( userComponent, component );

        assert ( trackLength > 0 );

        // Background - none

        // Track
        Shape shape = new Rectangle( 0, 0, trackLength, trackWidth );
        AbcLearnGraphic track = new AbcLearnShapeGraphic( component, shape, trackColor );
        setTrack( track );

        // Knob
        BufferedImage knobImage = FaradayResources.getImage( FaradayConstants.SLIDER_KNOB_IMAGE );
        AbcLearnGraphic knob = new AbcLearnImageGraphic( component, knobImage );
        knob.centerRegistrationPoint();
        setKnob( knob );

        // Knob Highlight
        BufferedImage knobHighlightImage = FaradayResources.getImage( FaradayConstants.SLIDER_KNOB_HIGHLIGHT_IMAGE );
        AbcLearnGraphic knobHighlight = new AbcLearnImageGraphic( component, knobHighlightImage );
        knobHighlight.centerRegistrationPoint();
        setKnobHighlight( knobHighlight );
    }
}
