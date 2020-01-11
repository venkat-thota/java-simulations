package edu.colorado.phet.theramp.view;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import edu.colorado.phet.theramp.TheRampStrings;
import edu.colorado.phet.theramp.timeseries.TimeSeriesModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;



public class TimeGraphic extends PNode implements ModelElement {
    private TimeSeriesModel timeModel;
    private DecimalFormat format = new DecimalFormat( "0.00" );
    public PText phetTextGraphic;

    public TimeGraphic( TimeSeriesModel clock ) {
        this.timeModel = clock;
        phetTextGraphic = new PText( "" );
        phetTextGraphic.setFont( RampFontSet.getFontSet().getTimeReadoutFont() );
        addChild( phetTextGraphic );
        stepInTime( 0.0 );
            }

    public void stepInTime( double dt ) {
        double time = timeModel.getTime();
        String text = MessageFormat.format( TheRampStrings.getString( "readout.seconds" ), new Object[] { format.format( time ) } );
        phetTextGraphic.setText( text );
    }
}
