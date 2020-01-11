
package com.aimxcel.abclearn.theramp.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioSourceDataLinePlayer {
    private static final int EXTERNAL_BUFFER_SIZE = 4000;
    private static boolean audioEnabled = true;
    private static ArrayList listeners = new ArrayList();

    public static interface Listener {
        void propertyChanged();
    }

    public static void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public static boolean isAudioEnabled() {
        return audioEnabled;
    }

    public static void setAudioEnabled( boolean audioEnabled ) {
        AudioSourceDataLinePlayer.audioEnabled = audioEnabled;
        for ( int i = 0; i < listeners.size(); i++ ) {
            Listener listener = (Listener) listeners.get( i );
            listener.propertyChanged();
        }
    }


    public static double getLength( URL url ) throws IOException, UnsupportedAudioFileException {
        AudioFileFormat aff = AudioSystem.getAudioFileFormat( url );
        AudioFormat audioFormat = aff.getFormat();
        double sec = ( aff.getFrameLength() / (double) audioFormat.getFrameRate() );
        System.out.println( "sec = " + sec );
        return sec;
    }

    public static void loop( final URL url ) {
        Runnable r = new Runnable() {
            public void run() {
                while ( true ) {
                    try {
                        play( url );
                    }
                    catch ( IOException e ) {
                        e.printStackTrace();
                    }
                    catch ( UnsupportedAudioFileException e ) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread( r );
        t.setPriority( Thread.MIN_PRIORITY );
        t.start();
    }

    
    public static void play( URL url ) throws IOException, UnsupportedAudioFileException {
        if ( audioEnabled ) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( new BufferedInputStream( url.openStream() ) );

            AudioFileFormat aff = AudioSystem.getAudioFileFormat( url );
            AudioFormat audioFormat = aff.getFormat();
            SourceDataLine line = null;
            DataLine.Info info = new DataLine.Info( SourceDataLine.class,
                                                    audioFormat );
            try {
                line = (SourceDataLine) AudioSystem.getLine( info );

              
                line.open( audioFormat );
            }
            catch ( LineUnavailableException e ) {
                e.printStackTrace();
                System.exit( 1 );
            }
            catch ( Exception e ) {
                e.printStackTrace();
                System.exit( 1 );
            }

           
            line.start();

          
            int nBytesRead = 0;

            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
            while ( nBytesRead != -1 ) {
                try {
                    nBytesRead = audioInputStream.read( abData, 0, abData.length );
                }
                catch ( IOException e ) {
                    e.printStackTrace();
                }
                if ( nBytesRead >= 0 ) {
                    int nBytesWritten = line.write( abData, 0, nBytesRead );
                }
            }
            line.drain();
            line.close();
        }
    }
}