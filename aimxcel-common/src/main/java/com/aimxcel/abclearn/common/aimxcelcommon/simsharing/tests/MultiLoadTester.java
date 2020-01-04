
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.tests;

import java.io.IOException;

public class MultiLoadTester {
    public static void main( String[] args ) throws IOException, InterruptedException {
        String command = "java -classpath C:\\workingcopy\\aimxcel\\svn\\trunk\\util\\simsharing\\deploy\\simsharing_all.jar com.aimxcel.abclearn.common.aimxcelcommon.simsharing.tests.LoadTester 60 -study aimxceldev";
        for ( int i = 0; i < 50; i++ ) {
            Process p = Runtime.getRuntime().exec( command );
            Thread.sleep( 500 );

            System.out.println( "Launched i=" + i );
        }
    }
}
