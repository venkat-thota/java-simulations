
package com.aimxcel.abclearn.core.aimxcelcore.test.help;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;
import com.aimxcel.abclearn.aimxcel2dcore.util.PUtil;

public class TestMotionHelpBalloonStartTime extends AimxcelTestApplication {

    public TestMotionHelpBalloonStartTime( String[] args ) throws InterruptedException {
        super( args );
        addModule( new TestMotionHelpBalloonStartTimeModule() );
    }

    private static class TestMotionHelpBalloonStartTimeModule extends Module {
        private MotionHelpBalloon helpBalloon;

        public TestMotionHelpBalloonStartTimeModule() throws InterruptedException {
            super( "test", new SwingClock( 30, 1 ) );

            PCanvas pCanvas = new PCanvas();
            pCanvas.setAnimatingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
            pCanvas.setDefaultRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
            pCanvas.setInteractingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
            helpBalloon = new MotionHelpBalloon( pCanvas, "<html>Help!<br>Wiggle Me!</html>" );

            helpBalloon.setOffset( 250, 0 );
            pCanvas.getLayer().addChild( helpBalloon );

            setSimulationPanel( pCanvas );
            Thread.sleep( 5000 );//simulate long startup time
        }

        public void activate() {
            super.activate();
            helpBalloon.animateTo( 500, 500 );
        }
    }

    public static void main( String[] args ) throws InterruptedException {
        PUtil.DEFAULT_ACTIVITY_STEP_RATE = 1;
        PUtil.ACTIVITY_SCHEDULER_FRAME_DELAY = 1;
        new TestMotionHelpBalloonStartTime( args ).startApplication();
    }

}
