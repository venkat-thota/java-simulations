
package com.aimxcel.abclearn.motion.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.motion.model.TimeData;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PClip;


public class CursorNode extends PClip {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChartCursor cursor;
    private TemporalChart chart;
    private AimxcelPPath path;

    public CursorNode( final ChartCursor cursor, final TemporalChart chart ) {
        this.cursor = cursor;
        this.chart = chart;

        //TODO: add a clipping layer in the chart
        final SimpleObserver updateClip = new SimpleObserver() {
            public void update() {
                Point2D pt = chart.modelToView( new TimeData( chart.getDataModelBounds().getMaxY(), chart.getDataModelBounds().getMinX() ) );
                Rectangle2D.Double clipPath = new Rectangle2D.Double( pt.getX(), pt.getY(), chart.getViewDimension().getWidth(), chart.getViewDimension().getHeight() );
                setPathTo( clipPath );
            }
        };
        //TODO: listen to chart origin, not just size
        chart.getViewDimension().addObserver( updateClip );
        updateClip.update();

        path = new HighQualityAimxcelPPath( new Color( 50, 50, 200, 83 ), new BasicStroke( 1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[] { 5, 2 }, 0 ), new Color( 20, 20, 30, 160 ) );
        addChild( path );

        final ChartCursor.Adapter pathUpdater = new ChartCursor.Adapter() {
            public void positionChanged() {
                double time = cursor.getTime();
                double chartX = chart.modelToView( new TimeData( 0, time ) ).getX();//todo: provide mapValue and mapTime
                double width = 6.0;
                path.setPathTo( new Rectangle2D.Double( chartX - width / 2, 0, width, chart.getViewDimension().getHeight() ) );
            }
        };
        pathUpdater.positionChanged();
        chart.getViewDimension().addObserver( new SimpleObserver() {
            public void update() {
                pathUpdater.positionChanged();
            }
        } );
        cursor.addListener( pathUpdater );

        //Update the in-chart cursor location during zoom in/out
        chart.getDataModelBounds().addObserver( new SimpleObserver() {
            public void update() {
                pathUpdater.positionChanged();
            }
        } );

        final ChartCursor.Adapter visibilityUpdater = new ChartCursor.Adapter() {
            public void visibilityChanged() {
                setVisible( cursor.isVisible() );
            }
        };
        visibilityUpdater.visibilityChanged();
        cursor.addListener( visibilityUpdater );
        addInputEventListener( new CursorHandler() );
        addInputEventListener( new PBasicInputEventHandler() {
            double relativeGrabPoint = Double.NaN;

            public void mousePressed( PInputEvent event ) {
                super.mousePressed( event );
                updateRelativeGrabPoint( event );
            }

            public void mouseDragged( PInputEvent event ) {
                super.mouseDragged( event );
                if ( Double.isNaN( relativeGrabPoint ) ) {
                    updateRelativeGrabPoint( event );
                }
                cursor.setTime( getModelPoint( event ) - relativeGrabPoint );
            }

            public void mouseReleased( PInputEvent event ) {
                super.mouseReleased( event );
                relativeGrabPoint = Double.NaN;
            }

            private void updateRelativeGrabPoint( PInputEvent event ) {
                relativeGrabPoint = getModelPoint( event ) - cursor.getTime();
            }

            private double getModelPoint( PInputEvent event ) {
                return chart.viewToModel( event.getPositionRelativeTo( chart ).getX() );
            }
        } );
    }
}