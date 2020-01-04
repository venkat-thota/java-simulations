
package com.aimxcel.abclearn.common.piccoloaimxcel.test;

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
import com.aimxcel.abclearn.common.piccoloaimxcel.AimxcelTabbedPane;

public class TestAimxcelTabbedPane {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Tab Test" );
        final AimxcelTabbedPane phetTabbedPane = new AimxcelTabbedPane();
        VerticalLayoutPanel verticalLayoutPanel = new VerticalLayoutPanel();

        JLabel c = new JLabel( "Hello" );
        verticalLayoutPanel.add( c );
        final JCheckBox comp = new JCheckBox( "Logo Visible", phetTabbedPane.getLogoVisible() );
        comp.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                phetTabbedPane.setLogoVisible( comp.isSelected() );
            }
        } );
        verticalLayoutPanel.add( comp );
        phetTabbedPane.addTab( null, "Hello!", verticalLayoutPanel );
        final JSlider slider = new JSlider( 6, 60, 10 );
        slider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                System.out.println( "slider.getValue() = " + slider.getValue() );
                phetTabbedPane.setTabFont( new AimxcelFont( Font.BOLD, slider.getValue() ) );
            }
        } );
//        phetTabbedPane.addTab( "<html>Font<br>Size</html>", slider );

        final JColorChooser colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent evt ) {
                ColorSelectionModel model = colorChooser.getSelectionModel();
                // Get the new color value
                Color newColor = model.getSelectedColor();
                phetTabbedPane.setSelectedTabColor( newColor );
            }
        } );
        phetTabbedPane.addTab( null, "Tab Colors", colorChooser );
        JButton content = new JButton( "button" );
        phetTabbedPane.addTab( null, "Large Button", content );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        frame.setContentPane( phetTabbedPane );
        frame.setSize( 1000, 400 );

        frame.setVisible( true );
    }
}
