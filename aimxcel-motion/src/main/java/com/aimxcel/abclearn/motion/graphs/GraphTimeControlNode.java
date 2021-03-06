
package com.aimxcel.abclearn.motion.graphs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;
import com.aimxcel.abclearn.timeseries.ui.TimeseriesResources;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;



public class GraphTimeControlNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PSwing goStopButton;
    private PSwing clearButton;
    private PNode seriesLayer = new PNode();
    private boolean editable = true;
    private boolean constructed = false;

    public GraphTimeControlNode( TimeSeriesModel timeSeriesModel ) {
        addChild( seriesLayer );

        goStopButton = new PSwing( new GoStopButton( timeSeriesModel ) );
        addChild( goStopButton );

        clearButton = new PSwing( new ClearButton( timeSeriesModel ) );
        addChild( clearButton );

        constructed = true;
        relayout();
    }

    public GraphControlSeriesNode addVariable( ControlGraphSeries series ) {
        GraphControlSeriesNode seriesNode = createGraphControlSeriesNode( series );
        seriesNode.setEditable( editable );
        seriesNode.setOffset( 0, seriesLayer.getFullBounds().getHeight() + 5 );
        seriesLayer.addChild( seriesNode );
        relayout();
        return seriesNode;
    }

    protected GraphControlSeriesNode createGraphControlSeriesNode( ControlGraphSeries series ) {
        return new GraphControlSeriesNode( series );
    }

    private void relayout() {
        if ( constructed ) {
            double dy = 5;
            seriesLayer.setOffset( 0, 0 );
            for ( int i = 0; i < seriesLayer.getChildrenCount(); i++ ) {
                GraphControlSeriesNode child = (GraphControlSeriesNode) seriesLayer.getChild( i );
                child.relayout( dy );
            }
            goStopButton.setOffset( 0, seriesLayer.getFullBounds().getMaxY() + dy );
            clearButton.setOffset( 0, goStopButton.getFullBounds().getMaxY() + dy );
        }
    }

    public void setEditable( boolean editable ) {
        this.editable = editable;
        for ( int i = 0; i < seriesLayer.getChildrenCount(); i++ ) {
            GraphControlSeriesNode child = (GraphControlSeriesNode) seriesLayer.getChild( i );
            child.setEditable( editable );
        }
        setHasChild( goStopButton, editable );
        setHasChild( clearButton, editable );
    }

    private void setHasChild( PNode child, boolean addChild ) {
        if ( addChild && !getChildrenReference().contains( child ) ) {
            addChild( child );
        }
        else if ( !addChild && getChildrenReference().contains( child ) ) {
            removeChild( child );
        }
    }

    public static class ClearButton extends JButton {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private TimeSeriesModel graphTimeSeries;

        public ClearButton( final TimeSeriesModel graphTimeSeries ) {
            super( AimxcelCommonResources.getString( "Common.clear" ) );
            this.graphTimeSeries = graphTimeSeries;
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    graphTimeSeries.clear();
                }
            } );
            graphTimeSeries.addListener( new TimeSeriesModel.Adapter() {
                public void dataSeriesChanged() {
                    updateEnabledState();
                }
            } );

            updateEnabledState();
        }

        private void updateEnabledState() {
            setEnabled( graphTimeSeries.isThereRecordedData() );
        }
    }

    public static class GoStopButton extends JButton {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean goButton = true;
        private TimeSeriesModel timeSeriesModel;

        public GoStopButton( final TimeSeriesModel timeSeriesModel ) {
            super( AimxcelCommonResources.getString( "chart-time-control.go" ) );
            this.timeSeriesModel = timeSeriesModel;
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    if ( isGoButton() ) {
                        //if the charts are filled up, then we must go to live mode, since switching to record mode entails restoring the last recorded state first
                        //see #2088
                        if ( timeSeriesModel.getRecordTime() >= timeSeriesModel.getMaxRecordTime() ) {
                            timeSeriesModel.startLiveMode();
                        }
                        else {
                            timeSeriesModel.startRecording();
                        }
                    }
                    else {
                        timeSeriesModel.setPaused( true );
                    }
                }
            } );
            timeSeriesModel.addListener( new TimeSeriesModel.Adapter() {

                public void modeChanged() {
                    updateGoState();
                }

                public void pauseChanged() {
                    updateGoState();
                }
            } );
            updateGoState();
        }

        private void updateGoState() {
            setGoButton( !( timeSeriesModel.isRecording() || timeSeriesModel.isLiveAndNotPaused() ) );
        }

        private void setGoButton( boolean go ) {
            this.goButton = go;
            setText( goButton ? AimxcelCommonResources.getString( "chart-time-control.go" ) : AimxcelCommonResources.getString( "Common.StopwatchPanel.stop" ) );
            setIcon( new ImageIcon( TimeseriesResources.loadBufferedImage( goButton ? "icons/go.png" : "icons/stop.png" ) ) );
        }

        private boolean isGoButton() {
            return goButton;
        }
    }

}
