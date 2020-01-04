
package com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.jnlp.FileContents;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;

/**
 * @author Sam Reid
 */
public class TestAimxcelServiceManagerSaveLoad {
    private JFrame frame;

    public TestAimxcelServiceManagerSaveLoad() {

        frame = new JFrame( TestAimxcelServiceManagerSaveLoad.class.getName() );
        JPanel panel = new JPanel();
        frame.setContentPane( panel );

        JButton save = new JButton( "Save" );
        save.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                try {
                    String s = "Sample output...";
                    AimxcelServiceManager.getFileSaveService( frame ).saveFileDialog( null, new String[] { "txt" }, new ByteArrayInputStream( s.getBytes() ), "Test File Save" );
                    System.out.println( "Wrote: " + s );
                }
                catch ( IOException e1 ) {
                    e1.printStackTrace();
                }
                catch ( UnavailableServiceException e1 ) {
                    e1.printStackTrace();
                }
            }
        } );
        panel.add( save );

        JButton load = new JButton( "Load" );
        load.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                try {
                    FileContents fileContents = AimxcelServiceManager.getFileOpenService( frame ).openFileDialog( "Test File Open", new String[] { "txt" } );
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( fileContents.getInputStream() ) );
                    ArrayList text = new ArrayList();
                    String line = bufferedReader.readLine();
                    while ( line != null ) {
                        text.add( line );
                        line = bufferedReader.readLine();
                    }
                    System.out.println( "Read Text:" );
                    for ( int i = 0; i < text.size(); i++ ) {
                        java.lang.String s = (java.lang.String) text.get( i );
                        System.out.println( s );
                    }
                }
                catch ( IOException e1 ) {
                    e1.printStackTrace();
                }
                catch ( UnavailableServiceException e1 ) {
                    e1.printStackTrace();
                }
            }
        } );
        panel.add( load );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public static void main( String[] args ) {
        new TestAimxcelServiceManagerSaveLoad().start();
    }

    private void start() {
        frame.show();
    }
}
