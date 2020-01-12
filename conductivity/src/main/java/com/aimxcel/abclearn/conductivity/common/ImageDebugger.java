package com.aimxcel.abclearn.conductivity.common;

import java.awt.*;

import javax.swing.*;


public class ImageDebugger {
    public static void show( String name, Image image ) {
        JFrame frame = new JFrame( name );
        JLabel contentPane = new JLabel( new ImageIcon( image ) );
        contentPane.setOpaque( true );
        frame.setContentPane( contentPane );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
    }
}
