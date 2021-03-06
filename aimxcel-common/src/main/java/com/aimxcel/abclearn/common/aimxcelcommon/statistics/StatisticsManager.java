
package com.aimxcel.abclearn.common.aimxcelcommon.statistics;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SessionCounter;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SoftwareAgreementManager;


public class StatisticsManager {

    private static final Object MONITOR = new Object();

    /* singleton */
    public static StatisticsManager instance;

    private final ISimInfo simInfo;
    private final Frame parentFrame;
    private final Vector messageQueue = new Vector();
    private final StatisticsThread statisticsThread = new StatisticsThread();
    private final StatisticsMessageSender statisticsService = new StatisticsMessageSender();
    private final ArrayList listeners = new ArrayList();
    private boolean applicationStartedCalled = false;

    /* singleton */
    private StatisticsManager( AimxcelApplication app ) {
        simInfo = app.getSimInfo();
        parentFrame = app.getAimxcelFrame();
        statisticsThread.start();
    }

    public static StatisticsManager initInstance( AimxcelApplication app ) {
        if ( instance != null ) {
            throw new RuntimeException( "StatisticsManager instance is already initialized" );
        }
        instance = new StatisticsManager( app );
        return instance;
    }

    public static StatisticsManager getInstance() {
        return instance;
    }

    /**
     * Blocks until all queued messages have been sent, up to a maximum of maxWaitTime milliseconds.
     */
    public static void waitFor( long maxWaitTimeMillis ) {
        if ( isStatisticsEnabled() ) {
            instance._waitFor( maxWaitTimeMillis );
        }
    }

    //Currently implemented with polling, should probably be converted to non-polling solution
    private void _waitFor( long maxWaitTimeMillis ) {
        long startTime = System.currentTimeMillis();
        while ( true ) {
            if ( messageQueue.isEmpty() || System.currentTimeMillis() - startTime >= maxWaitTimeMillis ) {
                return;
            }
            else {
                try {
                    Thread.sleep( 10 );
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessageImpl( final StatisticsMessage statisticsMessage ) {
        if ( isStatisticsEnabled() ) {
            messageQueue.add( statisticsMessage );
            synchronized ( MONITOR ) {
                MONITOR.notifyAll();
            }
        }
    }

    private class StatisticsThread extends Thread {
        private StatisticsThread() {
            super( new StatisticsRunnable() );
        }
    }

    public class StatisticsRunnable implements Runnable {
        public void run() {
            while ( true ) {
            //    sendAllMessages();
                synchronized ( MONITOR ) {
                    try {
                        MONITOR.wait();
                    }
                    catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendAllMessages() {
        while ( messageQueue.size() > 0 ) {
            StatisticsMessage m = (StatisticsMessage) messageQueue.get( 0 );
            boolean success = statisticsService.sendMessage( m );
            messageQueue.remove( m ); // remove message from queue after post, so that messageQueue won't be considered empty prematurely
            notifyListeners( success, m );
        }
    }

    public static boolean isStatisticsEnabled() {
        return instance != null && instance.simInfo.isStatisticsEnabled();
    }

    public static void sendMessage( StatisticsMessage statisticsMessage ) {
        // check for statistics enabled before message construction
        // because construction may cause java.security.AccessControlException under web start.
        if ( isStatisticsEnabled() ) {
            instance.sendMessageImpl( statisticsMessage );
        }
    }

    public void start() {

        // this method should only be called once
        if ( applicationStartedCalled ) {
            throw new IllegalStateException( "attempted to call applicationStarted more than once" );
        }
        applicationStartedCalled = true;

        if ( isStatisticsEnabled() ) {

            // increment session counts
            SessionCounter sessionCounter = SessionCounter.initInstance( simInfo.getProjectName(), simInfo.getFlavor() );
            if ( sessionCounter != null ) {
                sessionCounter.incrementCounts();
            }

            // create the session message
            final SessionMessage sessionMessage = SessionMessage.initInstance( simInfo );

            // Software Use Agreement
            SoftwareAgreementManager.validate( parentFrame, sessionMessage );

            // send session message
            addListener( new StatisticsManagerListener() {
                public void receiveResponse( boolean success, StatisticsMessage m ) {
                    if ( success && m == sessionMessage ) {
                        // if the session message is successfully sent, reset this session count
                        SessionCounter.getInstance().resetCountSince();
                    }
                }
            } );
            sendMessage( sessionMessage );
        }
    }

    private interface StatisticsManagerListener {
        public void receiveResponse( boolean success, StatisticsMessage m );
    }

    private synchronized void addListener( StatisticsManagerListener listener ) {
        listeners.add( listener );
    }

    // called from another thread, StatisticsThread
    private synchronized void notifyListeners( boolean success, StatisticsMessage m ) {
        ArrayList listenersCopy = new ArrayList( listeners ); // iterate on a copy to avoid ConcurrentModificationException
        Iterator i = listenersCopy.iterator();
        while ( i.hasNext() ) {
            ( (StatisticsManagerListener) i.next() ).receiveResponse( success, m );
        }
    }
}
