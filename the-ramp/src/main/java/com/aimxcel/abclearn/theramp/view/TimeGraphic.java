package com.aimxcel.abclearn.theramp.view;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.timeseries.TimeSeriesModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;



public class TimeGraphic extends PNode implements ModelElement {
    private TimeSeriesModel timeModel;
    private DecimalFormat format = new DecimalFormat( "0.00" );
    public PText aimxcelTextGraphic;

    public TimeGraphic( TimeSeriesModel clock ) {
        this.timeModel = clock;
        aimxcelTextGraphic = new PText( "" );
        aimxcelTextGraphic.setFont( RampFontSet.getFontSet().getTimeReadoutFont() );
        addChild( aimxcelTextGraphic );
        stepInTime( 0.0 );
            }

    public void stepInTime( double dt ) {
        double time = timeModel.getTime();
        String text = MessageFormat.format( TheRampStrings.getString( "readout.seconds" ), new Object[] { format.format( time ) } );
        aimxcelTextGraphic.setText( text );
    }
}
