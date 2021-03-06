

package com.aimxcel.abclearn.core.aimxcelcore.nodes.photon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.ColorControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.VisibleColor;
import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class PhotonImageGenerator extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double MIN_DIAMETER = 5;
    private static final double MAX_DIAMETER = 200;
    private static final double DEFAULT_DIAMETER = 30;

    private static final double MIN_WAVELENGTH = VisibleColor.MIN_WAVELENGTH - 1; // include UV
    private static final double MAX_WAVELENGTH = VisibleColor.MAX_WAVELENGTH + 1; // include IR
    private static final double DEFAULT_WAVELENGTH = 600;

    private static final Color DEFAULT_BACKGROUND = Color.BLACK;

    private LinearValueControl diameterControl;
    private LinearValueControl wavelengthControl;
    private ColorControl backgroundControl;
    private PCanvas canvas;
    private PNode parentNode;

    public PhotonImageGenerator() {
        super( "Photon Image Generator" );

        diameterControl = new LinearValueControl( MIN_DIAMETER, MAX_DIAMETER, "diameter:", "##0", "pixels" );
        diameterControl.setValue( DEFAULT_DIAMETER );
        diameterControl.setUpDownArrowDelta( 1 );
        diameterControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                updateImagePreview();
            }
        } );

        wavelengthControl = new LinearValueControl( MIN_WAVELENGTH, MAX_WAVELENGTH, "wavelength:", "##0", "nm" );
        wavelengthControl.setValue( DEFAULT_WAVELENGTH );
        wavelengthControl.setUpDownArrowDelta( 1 );
        wavelengthControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                updateImagePreview();
            }
        } );

        backgroundControl = new ColorControl( this, "background:", DEFAULT_BACKGROUND, new Dimension( 30, 30 ) /* chipSize */ );
        backgroundControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                updateBackground();
            }
        } );

        canvas = new PCanvas();
        int canvasSize = (int) ( 1.2 * MAX_DIAMETER );
        canvas.setPreferredSize( new Dimension( canvasSize, canvasSize ) );
        parentNode = new PComposite();
        canvas.getLayer().addChild( parentNode );

        final JFrame thisFrame = this;
        JButton saveButton = new JButton( "Save..." );
        saveButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                // create the image
                double wavelength = wavelengthControl.getValue();
                double diameter = diameterControl.getValue();
                Image image = PhotonNode.createImage( wavelength, diameter );

                // save the image to a file
                //TODO - verify that the filename ends with .png
                JFileChooser fc = new JFileChooser();
                fc.showSaveDialog( thisFrame ); // blocks until dialog is closed
                File outputFile = fc.getSelectedFile();
                if ( outputFile != null ) {
                    try {
                        ImageIO.write( BufferedImageUtils.toBufferedImage( image ), "PNG", outputFile );
                    }
                    catch ( IOException e1 ) {
                        e1.printStackTrace();
                    }
                }
            }
        } );

        JPanel controlPanel = new JPanel();
        EasyGridBagLayout layout = new EasyGridBagLayout( controlPanel );
        layout.setAnchor( GridBagConstraints.CENTER );
        controlPanel.setLayout( layout );
        int row = 0;
        int column = 0;
        layout.addComponent( diameterControl, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.addComponent( wavelengthControl, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.addComponent( backgroundControl, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.addComponent( saveButton, row++, column );

        JPanel contentPane = new JPanel();
        contentPane.add( canvas );
        contentPane.add( controlPanel );

        setContentPane( contentPane );
        pack();
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        updateImagePreview();
        updateBackground();
    }

    private void updateImagePreview() {
        double wavelength = wavelengthControl.getValue();
        double diameter = diameterControl.getValue();
        Image image = PhotonNode.createImage( wavelength, diameter );
        PImage imageNode = new PImage( image );
        imageNode.setOffset( ( canvas.getWidth() - imageNode.getWidth() ) / 2, ( canvas.getHeight() - imageNode.getHeight() ) / 2 );
        parentNode.removeAllChildren();
        parentNode.addChild( imageNode );
    }

    private void updateBackground() {
        Color color = backgroundControl.getColor();
        canvas.setBackground( color );
    }

    public static void main( String[] args ) {
        JFrame frame = new PhotonImageGenerator();
        frame.show();
    }
}
