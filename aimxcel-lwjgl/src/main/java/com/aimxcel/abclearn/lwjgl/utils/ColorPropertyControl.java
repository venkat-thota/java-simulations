package com.aimxcel.abclearn.lwjgl.utils;

import java.awt.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.ColorControl;
public class ColorPropertyControl extends ColorControl {
    public ColorPropertyControl( Frame parentFrame, String labelString, final Property<Color> color ) {
        super( parentFrame, labelString, color.get() );
        addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                LWJGLUtils.invoke( new Runnable() {
                    public void run() {
                        color.set( getColor() );
                    }
                } );
            }
        } );
    }
}