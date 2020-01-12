package com.aimxcel.abclearn.platetectonics.dev;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.lwjgl.utils.GLActionListener;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants;

public class PerformanceFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerformanceFrame() throws HeadlessException {
        super( "Performance" );

        JPanel container = new JPanel( new GridBagLayout() );
        setContentPane( container );

        container.add( new JLabel( "Target FPS" ), new GridBagConstraints() );
        container.add( new FrameRateButton( 1024 ), new GridBagConstraints() );
        container.add( new FrameRateButton( 60 ), new GridBagConstraints() );
        container.add( new FrameRateButton( 20 ), new GridBagConstraints() );
        container.add( new FrameRateButton( 5 ), new GridBagConstraints() );

        pack();
        setVisible( true );
    }

    private class FrameRateButton extends JButton {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FrameRateButton( final int frameRate ) {
            super( frameRate + "" );

            addActionListener( new GLActionListener( new Runnable() {
                public void run() {
                    PlateTectonicsConstants.FRAMES_PER_SECOND_LIMIT.set( frameRate );
                }
            } ) );
        }
    }
}
