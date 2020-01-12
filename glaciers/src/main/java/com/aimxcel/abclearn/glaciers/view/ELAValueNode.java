package com.aimxcel.abclearn.glaciers.view;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.model.Climate;
import com.aimxcel.abclearn.glaciers.model.Climate.ClimateListener;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class ELAValueNode extends PText {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new AimxcelFont( 14 );
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final NumberFormat NUMBER_FORMAT = new DefaultDecimalFormat( "0" );
    
    private final Climate _climate;
    private final ClimateListener _climateListener;
    
    public ELAValueNode( Climate climate ) {
        super();
        
        setPickable( false );
        setChildrenPickable( false );
        
        setFont( FONT );
        setTextPaint( TEXT_COLOR );
        
        _climate = climate;
        _climateListener = new ClimateListener() {

            public void snowfallChanged() {
                update();
            }

            public void temperatureChanged() {
                update();
            }
        };
        _climate.addClimateListener( _climateListener );
        
        update();
    }
    
    public void cleanup() {
        _climate.removeClimateListener( _climateListener );
    }
    
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( visible ) {
            update();
        }
    }
    
    private void update() {
        if ( getVisible() ) {
            final double ela = _climate.getELA();
            String s = NUMBER_FORMAT.format( ela );
            setText( "ELA = " + s + " " + GlaciersStrings.UNITS_METERS );
        }
    }
}
