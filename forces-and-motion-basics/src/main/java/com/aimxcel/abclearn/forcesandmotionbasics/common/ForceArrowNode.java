package com.aimxcel.abclearn.forcesandmotionbasics.common;

import fj.data.Option;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPText;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

import static com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas.DEFAULT_FONT;
import static com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas.INSET;
import static com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil.getSign;

public class ForceArrowNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double forceInNewtons;
    private AimxcelPText nameNode;

    public ForceArrowNode( final boolean transparent, final Vector2D tail, final double forceInNewtons, final String name, Color color, final TextLocation textLocation, final boolean showValues ) {
        this( transparent, tail, forceInNewtons, name, color, textLocation, showValues, Option.<ForceArrowNode>none() );
    }

    public ForceArrowNode( final boolean transparent, final Vector2D tail, final double forceInNewtons, final String name, Color color, final TextLocation textLocation, final boolean showValues, final Option<ForceArrowNode> other ) {
        this.forceInNewtons = forceInNewtons;

        //Choose a single scale factor that works in all of the tabs.
        final double value = forceInNewtons * 3.5 / 5;
        if ( value == 0 && textLocation == TextLocation.SIDE ) { return; }
        else if ( value == 0 && textLocation == TextLocation.TOP ) {
            showTextOnly( tail );
            return;
        }
        final double arrowScale = 1.2;
        final Point2D.Double tipLocation = tail.plus( value, 0 ).toPoint2D();
        final double headWidth = 40 * arrowScale;
        final ArrowNode arrowNode = new ArrowNode( tail.toPoint2D(), tipLocation, 30 * arrowScale, headWidth, 20 * arrowScale, 0.5, false );
        arrowNode.setPaint( transparent ? new Color( color.getRed(), color.getGreen(), color.getBlue(), 175 ) : color );
        arrowNode.setStroke( transparent ? new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 6, 4 }, 0 ) : new BasicStroke( 1 ) );
        addChild( arrowNode );
        nameNode = new AimxcelPText( name, DEFAULT_FONT ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            if ( textLocation == TextLocation.SIDE ) {
                if ( value > 0 ) {
                    setOffset( arrowNode.getFullBounds().getMaxX() + INSET, arrowNode.getFullBounds().getCenterY() - getFullBounds().getHeight() / 2 );
                }
                else {
                    setOffset( arrowNode.getFullBounds().getMinX() - getFullBounds().getWidth() - INSET, arrowNode.getFullBounds().getCenterY() - getFullBounds().getHeight() / 2 );
                }
            }
            else {
                setOffset( arrowNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, arrowNode.getFullBounds().getCenterY() - headWidth / 2 - getFullBounds().getHeight() - 4 );
            }
        }};
        addChild( nameNode );

        //If the text intersects, move the name down to avoid intersection.
        if ( intersectsAny( other ) ) {
            nameNode.setOffset( arrowNode.getFullBounds().getCenterX() - nameNode.getFullBounds().getWidth() / 2, arrowNode.getFullBounds().getMaxY() + INSET - 2 );
        }

        if ( showValues ) {
            final String text = new DecimalFormat( "0" ).format( Math.abs( forceInNewtons ) );
            final AimxcelPText valueNode = new AimxcelPText( new MessageFormat( Strings.FORCE_READOUT__PATTERN ).format( new Object[] { text } ), new AimxcelFont( 16, true ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                centerFullBoundsOnPoint( arrowNode.getFullBounds().getCenter2D() );
                double dx = 2;
                translate( forceInNewtons < 0 ? dx :
                           forceInNewtons > 0 ? -dx :
                           0, 0 );

                //For single character text (9N and lower), show below the name
                if ( text.length() <= 1 ) {
                    setOffset( nameNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, nameNode.getFullBounds().getMaxY() - 3 );
                }
            }};
            addChild( valueNode );
        }
    }

    private boolean intersectsAny( final Option<ForceArrowNode> other ) {
        return other.isSome() && getSign( forceInNewtons ) == getSign( other.some().forceInNewtons );
    }

    private void showTextOnly( final Vector2D tail ) {
        addChild( new AimxcelPText( Strings.SUM_OF_FORCES_EQUALS_ZERO, DEFAULT_FONT ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            centerBoundsOnPoint( tail.x, tail.y - 38 );//Fine tune location so that it shows at the same y location as the text would if there were an arrow
        }} );
    }
}
