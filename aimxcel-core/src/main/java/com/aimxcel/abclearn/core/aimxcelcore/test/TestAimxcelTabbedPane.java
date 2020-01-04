
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelTabbedPane;

public class TestAimxcelTabbedPane {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Tab Test" );
        final AimxcelTabbedPane aimxcelTabbedPane = new AimxcelTabbedPane();
        VerticalLayoutPanel verticalLayoutPanel = new VerticalLayoutPanel();

        JLabel c = new JLabel( "Hello" );
        verticalLayoutPanel.add( c );
        final JCheckBox comp = new JCheckBox( "Logo Visible", aimxcelTabbedPane.getLogoVisible() );
        comp.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                aimxcelTabbedPane.setLogoVisible( comp.isSelected() );
            }
        } );
        verticalLayoutPanel.add( comp );
        aimxcelTabbedPane.addTab( null, "Hello!", verticalLayoutPanel );
        final JSlider slider = new JSlider( 6, 60, 10 );
        slider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                System.out.println( "slider.getValue() = " + slider.getValue() );
                aimxcelTabbedPane.setTabFont( new AimxcelFont( Font.BOLD, slider.getValue() ) );
            }
        } );
//        aimxcelTabbedPane.addTab( "<html>Font<br>Size</html>", slider );

        final JColorChooser colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent evt ) {
                ColorSelectionModel model = colorChooser.getSelectionModel();
                // Get the new color value
                Color newColor = model.getSelectedColor();
                aimxcelTabbedPane.setSelectedTabColor( newColor );
            }
        } );
        aimxcelTabbedPane.addTab( null, "Tab Colors", colorChooser );
        JButton content = new JButton( "button" );
        aimxcelTabbedPane.addTab( null, "Large Button", content );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.setContentPane( aimxcelTabbedPane );
        frame.setSize( 1000, 400 );

        frame.setVisible( true );
    }
}
