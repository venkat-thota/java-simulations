package com.aimxcel.abclearn.sound.view;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.sound.model.SoundModel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel3;

//Use ApparatusPanel3 to improve scaling for low res screens, see #2860
public class SoundApparatusPanel extends ApparatusPanel3 {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int audioSource = SPEAKER_SOURCE;
    private double frequency = 0;
    private double amplitude = 0;
    // The point for which audio shoud be generated
    Point2D.Double audioReferencePt;

    //
    // Static fields and methods
    //
    public static int s_speakerConeOffsetX = 34;
    public static int s_speakerConeOffsetY = 2;
    public static int s_maxSpeakcerConeExcursion = 13;
    public final static int SPEAKER_SOURCE = 1;
    public final static int LISTENER_SOURCE = 2;

    private SoundModel model;

    public SoundApparatusPanel( SoundModel model, IClock clock ) {
        super( clock ,769, 568);
    }
}
