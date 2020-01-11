// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.greenhouse.model;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;


public class GreenhouseClock extends ConstantDtClock {

	public static final double DEFAULT_TIME_DELTA_PER_TICK = 10;
	public static final int DEFAULT_DELAY_BETWEEN_TICKS = 30;
	
    public GreenhouseClock() {
        super( DEFAULT_DELAY_BETWEEN_TICKS, DEFAULT_TIME_DELTA_PER_TICK );
    }

}
