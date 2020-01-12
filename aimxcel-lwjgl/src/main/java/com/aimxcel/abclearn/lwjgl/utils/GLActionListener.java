package com.aimxcel.abclearn.lwjgl.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GLActionListener implements ActionListener {
    private Runnable runnable;

    public GLActionListener( Runnable runnable ) {
        this.runnable = runnable;
    }

    public void actionPerformed( ActionEvent e ) {
        LWJGLUtils.invoke( runnable );
    }
}
