package com.aimxcel.abclearn.forcesandmotionbasics;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;
import com.aimxcel.abclearn.forcesandmotionbasics.motion.MotionModule;
import com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.TugOfWarModule;


public class ForcesAndMotionBasicsApplication extends CoreAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color BROWN = new Color( 197, 154, 91 );
    public static final Color TOOLBOX_COLOR = new Color( 231, 232, 233 );

    public ForcesAndMotionBasicsApplication( AimxcelApplicationConfig config ) {
        super( config );
        addModule( new TugOfWarModule() );
        addModule( new MotionModule( UserComponents.motionTab, Strings.MOTION, false, false ) );
        addModule( new MotionModule( UserComponents.frictionTab, Strings.FRICTION, true, false ) );
        addModule( new MotionModule( UserComponents.accelerationLabTab, Strings.ACCELERATION_LAB, true, true ) );
    }

    public static void main( String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, ForcesAndMotionBasicsResources.PROJECT_NAME, ForcesAndMotionBasicsApplication.class );
    }
}