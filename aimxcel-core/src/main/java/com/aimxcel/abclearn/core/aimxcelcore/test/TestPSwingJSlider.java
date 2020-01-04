

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import edu.umd.cs.piccolox.pswing.PSwing;


public class TestPSwingJSlider {

    public static void main( String[] args ) {

        double xOffset = 10;
        double yOffset = 10;

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 600, 300 ) );

        // OK, demonstrated workaround
        {
            PSwing pswing = new PSwing( new SliderWithTicks() );
            canvas.getLayer().addChild( pswing );
            pswing.setOffset( xOffset, yOffset );
            xOffset += 100;
        }

        // Very broken, border
        {
            JPanel panel = new JPanel();
            panel.setBorder( new TitledBorder( "broken" ) );
            panel.add( new SliderWithTicks() );
            PSwing pswing = new PSwing( panel );
            canvas.getLayer().addChild( pswing );
            pswing.setOffset( xOffset, yOffset );
            xOffset += 100;
        }

        // Less broken, no border
        {
            JPanel panel = new JPanel();
            panel.add( new SliderWithTicks() );
            PSwing pswing = new PSwing( panel );
            canvas.getLayer().addChild( pswing );
            pswing.setOffset( xOffset, yOffset );
            xOffset += 100;
        }

        // OK, no ticks
        {
            JPanel panel = new JPanel();
            panel.setBorder( new TitledBorder( "OK" ) );
            panel.add( new SliderNoTicks() );
            PSwing pswing = new PSwing( panel );
            canvas.getLayer().addChild( pswing );
            pswing.setOffset( xOffset, yOffset );
            xOffset += 100;
        }

        // OK
        JPanel swingControlPanel = new JPanel();
        swingControlPanel.setBorder( new TitledBorder( "OK" ) );
        swingControlPanel.add( new SliderWithTicks() );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( swingControlPanel, BorderLayout.EAST );
        mainPanel.add( canvas, BorderLayout.CENTER );

        JFrame frame = new JFrame();
        frame.setContentPane( mainPanel );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }

    private static class SliderNoTicks extends JSlider {

        public SliderNoTicks() {
            setOrientation( JSlider.VERTICAL );
            setMinimum( 0 );
            setMaximum( 100 );
            setValue( 0 );
        }
    }

    private static class SliderWithTicks extends SliderNoTicks {

        public SliderWithTicks() {
            setMajorTickSpacing( 50 );
            setMinorTickSpacing( 10 );
            setPaintTicks( true );
            setPaintLabels( true );
        }
    }
}
