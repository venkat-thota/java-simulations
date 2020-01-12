package com.aimxcel.abclearn.platetectonics.control;

import javax.swing.*;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.VSliderNode;
import com.aimxcel.abclearn.lwjgl.utils.SwingForwardingProperty;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class ZoomPanel extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SwingForwardingProperty<Double> swingZoomRatio;
    private final Property<Double> zoomRatio;

    public ZoomPanel( final Property<Double> zoomRatio ) {
        this.zoomRatio = zoomRatio;
        final PText zoomText = new PText( Strings.ZOOM );

        swingZoomRatio = new SwingForwardingProperty<Double>( zoomRatio );
        VSliderNode slider = new VSliderNode( UserComponents.zoomSlider, 0, 1, 6, 100, swingZoomRatio, new Property<Boolean>( true ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( ( zoomText.getFullBounds().getWidth() - getFullBounds().getWidth() ) / 2,
                       zoomText.getFullBounds().getMaxY() + 10
            );
        }};
        addChild( zoomText );
        addChild( slider );
    }

    public void resetAll() {
        // reset the actual zoom ratio
        zoomRatio.reset();
        final double ratio = zoomRatio.get();

        // and update the slider with the new (reset) value in the Swing EDT
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                swingZoomRatio.set( ratio );
                repaint();
            }
        } );
    }
}
