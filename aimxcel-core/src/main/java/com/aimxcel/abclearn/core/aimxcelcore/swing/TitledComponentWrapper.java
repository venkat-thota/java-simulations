
package com.aimxcel.abclearn.core.aimxcelcore.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelTitledBorder;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class TitledComponentWrapper {

    /*
     * Wraps the specified child swing component with the specified title.
     */
    public static JComponent wrapWithTitle( final String title, final Font titleFont, final Color titleColor, final JComponent child ) {
        //Uses a VerticalLayoutPanel internally to make sure the layout shows the entire title
        return new VerticalLayoutPanel() {{
            setBorder( new AimxcelTitledBorder( title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont, titleColor ) );
            setAnchor( GridBagConstraints.WEST );
            setFillNone();

            //Determine the size of the title
            final int width = (int) new JLabel( title ) {{
                setFont( titleFont );
            }}.getPreferredSize().getWidth() +
                              5;//Add some extra spacing so that you can see the curve of the AimxcelTitledBorder
            add( Box.createHorizontalStrut( width ) );//This strut ensures that the component will be big enough to show the entire title
            add( child );
        }};
    }

    /**
     * This sample main demonstrates usage of the AimxcelTitledPanel
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JComponent contentPane = wrapWithTitle( "aoensuthasnotehuBorder", new AimxcelFont( 18 ), Color.blue, new VerticalLayoutPanel() {{
            for ( int i = 0; i < 10; i++ ) {
                add( new JLabel( "medium sized label " + i ) );
            }
            add( new JButton( "A button" ) );
        }} );

        frame.setContentPane( contentPane );
        frame.pack();
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
