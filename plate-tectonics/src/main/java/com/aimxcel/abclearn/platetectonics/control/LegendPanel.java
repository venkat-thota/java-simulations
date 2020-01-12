package com.aimxcel.abclearn.platetectonics.control;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.PANEL_TITLE_FONT;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class LegendPanel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LegendPanel( ColorMode colorMode ) {
        final PNode title = new PText( PlateTectonicsResources.Strings.LEGEND ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( PANEL_TITLE_FONT );
        }};
        addChild( title );

        Color minColor = colorMode.getMaterial().getMinColor();
        Color maxColor = colorMode.getMaterial().getMaxColor();

        final AimxcelPPath gradient = new AimxcelPPath( new Rectangle2D.Double( 0, 0, 100, 10 ), new GradientPaint( 0, 0, minColor, 100, 0, maxColor ), new BasicStroke( 1 ), Color.BLACK );
        addChild( gradient );

        final PText minLabel = new PText( colorMode.getMinString() );
        addChild( minLabel );
        final PText maxLabel = new PText( colorMode.getMaxString() );
        addChild( maxLabel );

        double verticalPadding = 2;
        double horizontalPadding = 5;

        double yOffset = title.getFullBounds().getHeight() + verticalPadding;
        double height = Math.max( Math.max( minLabel.getFullBounds().getHeight(), maxLabel.getFullBounds().getHeight() ), gradient.getFullBounds().getHeight() );
        minLabel.setOffset( 0, yOffset + ( height - minLabel.getFullBounds().getHeight() ) / 2 );
        gradient.setOffset( minLabel.getFullBounds().getMaxX() + horizontalPadding, yOffset + ( height - gradient.getFullBounds().getHeight() ) / 2 );
        maxLabel.setOffset( gradient.getFullBounds().getMaxX() + horizontalPadding, yOffset + ( height - maxLabel.getFullBounds().getHeight() ) / 2 );

        title.setOffset( ( maxLabel.getFullBounds().getMaxX() - title.getFullBounds().getWidth() ) / 2, 0 );
    }
}
