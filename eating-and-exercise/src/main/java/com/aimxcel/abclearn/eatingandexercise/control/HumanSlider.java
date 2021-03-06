
package com.aimxcel.abclearn.eatingandexercise.control;

import java.text.NumberFormat;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.eatingandexercise.control.valuenode.LinearValueControlNode;


public class HumanSlider extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinearValueControlNode linearValueControlNode;
    private PNodeComponent pNodeComponent;

    public HumanSlider( double min, double max, double value, String label, String textFieldPattern, String units ) {
        linearValueControlNode = new LinearValueControlNode( label, units, min, max, value, new DefaultDecimalFormat( textFieldPattern ) );
        linearValueControlNode.setTextFieldColumns( 7 );//current limiting factor is age
        pNodeComponent = new PNodeComponent( linearValueControlNode );
        add( pNodeComponent );
    }

    public double getValue() {
        return linearValueControlNode.getValue();
    }

    public void addChangeListener( final ChangeListener changeListener ) {
        linearValueControlNode.addListener( new LinearValueControlNode.Listener() {
            public void valueChanged( double value ) {
                changeListener.stateChanged( null );
            }
        } );
    }

    public void setValue( double v ) {
        linearValueControlNode.setValue( v );
    }

    public void setRange( double min, double max ) {
        linearValueControlNode.setSliderRange( min, max );
    }

    public void setUnits( String distanceUnit ) {
        linearValueControlNode.setUnits( distanceUnit );
    }

    public void setPaintLabels( boolean b ) {
    }

    public void setPaintTicks( boolean b ) {
    }

    public void setTextFieldFormat( NumberFormat numberFormat ) {
        linearValueControlNode.setTextFieldFormat( numberFormat );
    }

    public void setTickLabels( Hashtable table ) {
    }

    public static void layout( HumanSlider[] s ) {
        LinearValueControlNode[] nodes = new LinearValueControlNode[s.length];
        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i] = s[i].linearValueControlNode;
        }
        for ( int i = 0; i < nodes.length; i++ ) {
            nodes[i].setLayoutStrategy( nodes[i].getGridLayout( nodes ) );
        }
        for ( int i = 0; i < s.length; i++ ) {
            s[i].pNodeComponent.updatePreferredSize();
        }
    }

}
