package com.aimxcel.abclearn.lwjgl.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJCheckBox;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public class GLSimSharingPropertyCheckBox extends SimSharingJCheckBox {
    public GLSimSharingPropertyCheckBox( IUserComponent userComponent, String text, final SettableProperty<Boolean> lwjglProperty ) {
        super( userComponent, text );

        // during construction, assume both threads are synchronized
        setSelected( lwjglProperty.get() );

        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent actionEvent ) {
                // this call needs to be done in the Swing EDT
                final boolean showThem = isSelected();

                // then set the property in the LWJGL thread
                LWJGLUtils.invoke( new Runnable() {
                    public void run() {
                        lwjglProperty.set( showThem );
                    }
                } );
            }
        } );
        lwjglProperty.addObserver( new SimpleObserver() {
            public void update() {
                // access the property in the LWJGL thread
                final boolean set = lwjglProperty.get();

                // then set whether we are selected in the LWJGL thread
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        setSelected( set );
                    }
                } );
            }
        } );
    }
}
