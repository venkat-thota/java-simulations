// Copyright 2002-2011, University of Colorado

/**
 * Class: InteractiveSpeakerGraphic
 * Package: edu.colorado.phet.sound.view
 * Author: Another Guy
 * Date: Aug 31, 2004
 */
package com.aimxcel.abclearn.sound.view;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.sound.SoundConfig;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.util.EventChannel;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.CompositeAimxcelGraphic;

public class InteractiveSpeakerGraphic extends CompositeAimxcelGraphic {

    public interface InteractiveSpeakerChangeListener extends EventListener {
        void stateChanged(ChangeEvent e);
    }

    private SpeakerGraphic speakerGraphic;
    private BufferedWaveMediumGraphic waveMediumGraphic;
    private ArrayList mouseReleaseListeners = new ArrayList();
    private EventChannel changeEventChannel = new EventChannel( InteractiveSpeakerChangeListener.class );
    private InteractiveSpeakerChangeListener changeListenerProxy = (InteractiveSpeakerChangeListener)changeEventChannel.getListenerProxy();

    public InteractiveSpeakerGraphic( SpeakerGraphic speakerGraphic,
                                      BufferedWaveMediumGraphic waveMediumGraphic ) {
        this.speakerGraphic = speakerGraphic;
        addGraphic( speakerGraphic );
        this.waveMediumGraphic = waveMediumGraphic;
        this.setCursorHand();
        this.addTranslationListener( new SpeakerTranslator() );
    }

    public Point2D.Double getAudioSourceLocation() {
        return new Point2D.Double( waveMediumGraphic.getOrigin().getX(), waveMediumGraphic.getOrigin().getY() );
    }

    /**
     * Agent that moves the speaker and wave graphics if the the speaker is moved. Also notifies any change listeners
     */
    private class SpeakerTranslator implements TranslationListener {
        public void translationOccurred( TranslationEvent event ) {
            Point2D p = speakerGraphic.getLocation();
            speakerGraphic.setLocation( (int)p.getX(), (int)( p.getY() + 2 * MathUtil.getSign( event.getDy() ) ) );
            waveMediumGraphic.setOrigin( new Point2D.Double( SoundConfig.s_wavefrontBaseX, speakerGraphic.getLocation().y ) );
            changeListenerProxy.stateChanged( new ChangeEvent( InteractiveSpeakerGraphic.this ) );
        }
    }

    public void addChangeListener( InteractiveSpeakerChangeListener changeListener ) {
        changeEventChannel.addListener( changeListener );
    }

}
