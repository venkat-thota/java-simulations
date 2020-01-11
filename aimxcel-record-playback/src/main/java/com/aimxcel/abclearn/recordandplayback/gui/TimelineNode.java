
package com.aimxcel.abclearn.recordandplayback.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.recordandplayback.model.RecordAndPlaybackModel;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class TimelineNode<T> extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RecordAndPlaybackModel<T> model;
    private AimxcelPCanvas canvas;
    private double maxTime;

    private int pathOffsetY = 4;
    private int pathHeight = 6;
    private int insetX = 10;
    private AimxcelPPath shaded;
    private AimxcelPPath background;
    private PImage handle;

    public TimelineNode( final RecordAndPlaybackModel<T> model, AimxcelPCanvas canvas, Color timelineColor, double maxTime ) {
        this.model = model;
        this.canvas = canvas;
        this.maxTime = maxTime;

        shaded = new AimxcelPPath( timelineColor );
        Color backgroundColor = new Color( 190, 195, 195 );

        background = new Track( backgroundColor );

        BufferedImage img = null;
        try {
            img = ImageLoader.loadBufferedImage( "piccolo-phet/images/button-template.png" );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        BufferedImage scaledImage = BufferedImageUtils.getScaledInstance( img, 20, 10, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true );
        handle = new PImage( scaledImage );

        addChild( background );
        addChild( shaded );
        addChild( handle );

        canvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                updateSelf();
            }
        } );

        handle.addInputEventListener( new CursorHandler() );
        handle.addInputEventListener( new PBasicInputEventHandler() {
            double relativeGrabPoint = Double.NaN;

            public void mouseDragged( PInputEvent event ) {
                super.mouseDragged( event );
                if ( Double.isNaN( relativeGrabPoint ) ) { updateRelativeGrabLocation( event ); }
                model.setTime( MathUtil.clamp( model.getMinRecordedTime(), getModelPoint( event ) - relativeGrabPoint, model.getMaxRecordedTime() ) );
            }

            private void updateRelativeGrabLocation( PInputEvent event ) {
                relativeGrabPoint = getModelPoint( event ) - model.getTime();
            }

            private double getModelPoint( PInputEvent event ) {
                return xToTime( event.getCanvasPosition().getX() );
            }

            public void mousePressed( PInputEvent event ) {
                super.mousePressed( event );
                updateRelativeGrabLocation( event );
            }

            public void mouseReleased( PInputEvent event ) {
                super.mouseReleased( event );
                relativeGrabPoint = Double.NaN;
            }
        } );

        shaded.addInputEventListener( new PBasicInputEventHandler() {
            public void mousePressed( PInputEvent event ) {
                //todo: should pressing the timeline put the model in playback mode?
                double t = xToTime( event.getCanvasPosition().getX() );
                model.setTime( MathUtil.clamp( model.getMinRecordedTime(), t, model.getMaxRecordedTime() ) );
            }

            /**Relative drag when just clicking in the timeline track*/
            public void mouseDragged( PInputEvent event ) {
                model.setPaused( true );
                double dx = event.getCanvasDelta().width;
                model.setTime( MathUtil.clamp( model.getMinRecordedTime(), model.getTime() + deltaXtoDeltaTime( dx ), model.getMaxRecordedTime() ) );
            }
        } );

        model.addObserver( new SimpleObserver() {
            public void update() {
                updateSelf();
            }
        } );
        updateSelf();
    }

    private static Color darker( Color c, int delta ) {
        return new Color( c.getRed() - delta, c.getGreen() - delta, c.getBlue() - delta );
    }

    private double deltaXtoDeltaTime( double dx ) {
        return xToTime( dx ) - xToTime( 0 );
    }

    private double timeToX( double time ) {
        double scale = ( canvas.getWidth() - insetX * 2 ) / maxTime;
        return time * scale;
    }

    private double xToTime( double x ) {
        double scale = ( canvas.getWidth() - insetX * 2 ) / maxTime;
        return x / scale;
    }

    private void updateSelf() {
        shaded.setPathTo( new Rectangle2D.Double( insetX, pathOffsetY + 1, timeToX( model.getRecordedTimeRange() ), pathHeight - 1 ) );
        background.setPathTo( new Rectangle2D.Double( insetX, pathOffsetY, timeToX( maxTime ), pathHeight ) );
        handle.setVisible( model.isPlayback() );
        double elapsed = model.getTime() - model.getMinRecordedTime();
        handle.setOffset( timeToX( elapsed ) - handle.getFullBounds().getWidth() / 2 + insetX, pathOffsetY - 2 );
    }

    public static class Track extends AimxcelPPath {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private AimxcelPPath topShade;
        private AimxcelPPath bottomShade;
        private AimxcelPPath leftShade;
        private AimxcelPPath rightShade;

        public Track( Color backgroundColor ) {
            super( backgroundColor );
            topShade = new AimxcelPPath( new BasicStroke( 2 ), darker( backgroundColor, 55 ) );
            addChild( topShade );
            bottomShade = new AimxcelPPath( new BasicStroke( 1 ), darker( backgroundColor, 20 ) );
            addChild( bottomShade );
            leftShade = new AimxcelPPath( new BasicStroke( 2 ), darker( backgroundColor, 50 ) );
            addChild( leftShade );
            rightShade = new AimxcelPPath( new BasicStroke( 1 ), darker( backgroundColor, 20 ) );
            addChild( rightShade );
        }

        @Override
        public void setPathTo( Shape aShape ) {
            super.setPathTo( aShape );
            Rectangle2D b = aShape.getBounds2D();
            topShade.setPathTo( new Line2D.Double( b.getX(), b.getY(), b.getMaxX(), b.getY() ) );
            bottomShade.setPathTo( new Line2D.Double( b.getX(), b.getMaxY(), b.getMaxX(), b.getMaxY() ) );
            leftShade.setPathTo( new Line2D.Double( b.getX(), b.getY(), b.getX(), b.getMaxY() ) );
            rightShade.setPathTo( new Line2D.Double( b.getMaxX(), b.getY(), b.getMaxX(), b.getMaxY() ) );
        }
    }
}