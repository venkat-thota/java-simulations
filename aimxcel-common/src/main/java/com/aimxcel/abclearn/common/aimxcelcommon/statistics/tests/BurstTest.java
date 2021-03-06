
package com.aimxcel.abclearn.common.aimxcelcommon.statistics.tests;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SessionCounter;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.SessionMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.StatisticsMessageSender;


public class BurstTest {
    public static void main( String[] args ) {
        SessionCounter.initInstance( "moving-man", "moving-man" );
        SessionMessage.initInstance( new AimxcelApplicationConfig( args, "moving-man" ) );
//        int numThreads = 100;
//        int numMessagesPerThread = 100;

        int numThreads = 250;
        int numMessagesPerThread = 1;

        for ( int i = 0; i < numThreads; i++ ) {
            new BurstTestThread( i, numMessagesPerThread ).start();
        }
    }

    private static class BurstTestThread extends Thread {
        private int index;
        private int numMessagesPerThread;

        private BurstTestThread( int index, int numMessagesPerThread ) {
            this.index = index;
            this.numMessagesPerThread = numMessagesPerThread;
        }

        public void run() {
            super.run();
            System.out.println( "Started thread: " + index );
            for ( int i = 0; i < numMessagesPerThread; i++ ) {
                System.out.println( "Thread[" + index + "] sending message " + i + "..." );
                sendMessage();
            }
        }

        private void sendMessage() {
            SessionMessage sessionMessage = SessionMessage.getInstance();
            new StatisticsMessageSender().sendMessage( sessionMessage );
        }
    }
}
