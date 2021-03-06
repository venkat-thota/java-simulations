package com.aimxcel.abclearn.batteryresistorcircuit.regions;

import java.util.ArrayList;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.WireRegion;

public class AndRegion implements WireRegion {
    ArrayList list = new ArrayList();

    public void addRegion( WireRegion wr ) {
        list.add( wr );
    }

    public boolean contains( WireParticle wp ) {
        for ( int i = 0; i < list.size(); i++ ) {
            if ( ( (WireRegion) list.get( i ) ).contains( wp ) ) {
                return true;
            }
        }
        return false;
    }
}
