

/*
  cd E:\java\projects\phets\data
  C:\j2sdk1.4.0_01\bin\java phet.edu.colorado.phet.signal.SignalApplet
  java phet.edu.colorado.phet.signal.SignalApplet
*/

package com.aimxcel.abclearn.signalcircuit;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.signalcircuit.phys2d.laws.Validate;


public class SignalCircuitSimulationPanel extends JPanel {

    public SignalCircuitSimulationPanel( IClock clock) {
        Signal s = new Signal( 600, 300, clock );
        s.getSystem().addLaw( new Validate( this ) );

        setLayout( new BorderLayout() );
        add( s.getPanel(), BorderLayout.CENTER );

        add( s.getControlPanel(), BorderLayout.SOUTH );
        validate();
    }

}
