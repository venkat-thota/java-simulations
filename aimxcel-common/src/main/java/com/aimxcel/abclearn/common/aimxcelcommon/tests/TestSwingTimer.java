
package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class TestSwingTimer {
    static long lastEndTime;

    public static void main( String[] args ) throws InterruptedException {
        Timer timer = new Timer( 30, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                long betweenTime = System.currentTimeMillis() - lastEndTime;
                System.out.print( "betweenTime = " + betweenTime + ", " );
                try {
                    Thread.sleep( 30 );
                }
                catch ( InterruptedException e1 ) {
                    e1.printStackTrace();
                }
                long tickTime = System.currentTimeMillis() - lastEndTime;
                System.out.println( "tickTime = " + tickTime );
                lastEndTime = System.currentTimeMillis();
            }
        } );
        timer.setCoalesce( false );
        timer.start();
        Thread.sleep( 5000 );
        System.exit( 0 );
    }
}
