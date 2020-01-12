package com.aimxcel.abclearn.greenhouse.common.graphics;

import java.awt.*;

import com.aimxcel.abclearn.greenhouse.GreenhouseResources;

public class Animation {

    private Image[] frames;
    private int currFrameNum = 0;

    /**
     * @param filePrefix The prefix for the names of all files to be animated
     * @param numFrames  The number of files to be animated
     */
    public Animation( String filePrefix, int numFrames ) {
        frames = loadAnimation( filePrefix, numFrames );
    }

    /**
     * Gets the current image of the animation
     *
     * @return The current image of the animation
     */
    public Image getCurrFrame() {
        return frames[currFrameNum];
    }

    /**
     * Gets the next frame in the animation
     *
     * @return The next frame in the animation
     */
    public Image getNextFrame() {
        stepCurrFrameNum( +1 );
        return getCurrFrame();
    }

    /**
     * Gets the previous frame of the animation
     *
     * @return The previous frame of the animation
     */
    public Image getPrevFrame() {
        stepCurrFrameNum( -1 );
        return getCurrFrame();
    }

    /**
     * Steps the animation a specified number of frames
     *
     * @param dir +1 steps forward, -1 steps back
     * @return the new current frame number for the animation
     */
    private int stepCurrFrameNum( int dir ) {
        currFrameNum = ( currFrameNum + frames.length + dir )
                       % frames.length;
        return currFrameNum;
    }

    /**
     * @return
     */
    public int getCurrFrameNum() {
        return currFrameNum;
    }

    /**
     * @return
     */
    public int getNumFrames() {
        return frames.length;
    }


    //
    // Static fields and methods
    //

    /**
     *
     */
    private static Image[] loadAnimation( String filePrefix, int numFrames ) {
        Image[] frames = new Image[numFrames];
        for ( int i = 1; i <= numFrames; i++ ) {
            String fileName = Animation.genAnimationFileName( filePrefix, i );
            frames[i - 1] = GreenhouseResources.getImage( fileName );
        }
        return frames;
    }

    /**
     * Generates a complete TIFF file name for a frame in a Poser-generated animation
     */
    private static String genAnimationFileName( String fileNamePrefix, int frameNum ) {
        String zeroStr = "";
        int i = 0;
        for ( int temp = frameNum; temp != 0; i++ ) {
            temp /= 10;
        }
        for ( ; i < 4; i++ ) {
            zeroStr = zeroStr.concat( "0" );
        }
        String fileName = fileNamePrefix + "_" + zeroStr + Integer.toString( frameNum ) + ".gif";
        return fileName;
    }
}
