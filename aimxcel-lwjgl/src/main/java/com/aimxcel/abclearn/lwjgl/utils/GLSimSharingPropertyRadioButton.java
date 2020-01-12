package com.aimxcel.abclearn.lwjgl.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public class GLSimSharingPropertyRadioButton<T> extends SimSharingJRadioButton {

    public GLSimSharingPropertyRadioButton( IUserComponent userComponent, String text, final SettableProperty<T> lwjglProperty, final T value ) {
        super( userComponent, text );

        // during construction, assume both threads are synchronized
        setSelected( lwjglProperty.get() == value );

        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                // set that we are selected in the Swing EDT
                setSelected( true );

                // and modify the underlying property in the LWJGL thread
                LWJGLUtils.invoke( new Runnable() {
                    public void run() {
                        lwjglProperty.set( value );
                    }
                } );
            }
        } );
        lwjglProperty.addObserver( new SimpleObserver() {
            public void update() {
                // access the property in the LWJGL thread
                final boolean set = lwjglProperty.get() == value;

                // and modify whether we are selected in the Swing EDT
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        setSelected( set );
                    }
                } );
            }
        } );
    }
}
