// Copyright 2002-2011, University of Colorado

/**
 * Class: SpeakerGraphic
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 11, 2004
 */
package com.aimxcel.abclearn.sound.view;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.sound.SoundConfig;
import com.aimxcel.abclearn.sound.model.WaveMedium;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;

public class SpeakerGraphic extends CompositeAimxcelGraphic {

    public static int s_speakerConeOffsetX = 34;

    private AimxcelImageGraphic speakerFrame;
    private AimxcelImageGraphic speakerCone;
    private BufferedImage speakerFrameImg;
    private BufferedImage speakerConeImg;
    private Point2D.Double location = new Point2D.Double();

    public SpeakerGraphic( ApparatusPanel apparatusPanel, final WaveMedium waveMedium ) {

        super( apparatusPanel );

        // Set up the speaker
        try {
            speakerFrameImg = ImageLoader.loadBufferedImage( SoundConfig.SPEAKER_FRAME_IMAGE_FILE );
            speakerConeImg = ImageLoader.loadBufferedImage( SoundConfig.SPEAKER_CONE_IMAGE_FILE );
        }
        catch( IOException e ) {
            e.printStackTrace();
            throw new RuntimeException( "Image files not found" );
        }
        speakerFrame = new AimxcelImageGraphic( apparatusPanel, speakerFrameImg );
        speakerCone = new AimxcelImageGraphic( apparatusPanel, speakerConeImg );
        setLocation( SoundConfig.s_speakerBaseX, SoundConfig.s_wavefrontBaseY );
        this.addGraphic( speakerFrame );
        this.addGraphic( speakerCone );
        waveMedium.addObserver( new SimpleObserver() {
            private int s_maxSpeakerConeExcursion = 6;

            public void update() {
                int coneOffset = (int)( waveMedium.getAmplitudeAt( 0 ) / SoundConfig.s_maxAmplitude * s_maxSpeakerConeExcursion );
                speakerCone.setLocation( (int)location.getX() + s_speakerConeOffsetX + coneOffset,
                                         (int)location.getY() - speakerConeImg.getHeight( null ) / 2 );
            }
        } );
    }

    public void setLocation( int x, int y ) {
        this.location.setLocation( x, y );
        speakerFrame.setLocation( x, y - speakerFrameImg.getHeight( null ) / 2 );
        speakerCone.setLocation( x + s_speakerConeOffsetX, y - speakerConeImg.getHeight( null ) / 2 );
    }

    public Point getLocation() {
        return new Point( (int)location.getX(), (int)location.getY() );
    }

    protected void syncBounds() {
        super.syncBounds();
    }

    public void setConePosition( int x ) {
        speakerCone.setLocation( (int)location.getX() + s_speakerConeOffsetX + x,
                                 (int)location.getY() - speakerConeImg.getHeight( null ) / 2 );
    }
}
