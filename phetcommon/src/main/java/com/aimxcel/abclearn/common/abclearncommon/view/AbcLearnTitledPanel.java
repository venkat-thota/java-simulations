
package com.aimxcel.abclearn.common.abclearncommon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnTitledBorder;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnTitledPanel;

/**
 * The PhET Titled Panel uses the AbcLearnTitledBorder, but expands the rest of the component to ensure the titled border is fully visible.
 * See #2476
 *
 * @author Sam Reid
 */
public class AbcLearnTitledPanel extends JPanel {
    private final AbcLearnTitledBorder titledBorder;
    private static final int AMOUNT_TO_EXTEND_BEYOND_TITLE = 5;//so that you can see the curve of the line border, without this factor, the line border drops down instead of curving to the right

    public AbcLearnTitledPanel( String title ) {
        this( title, AbcLearnTitledBorder.DEFAULT_FONT );
    }

    public AbcLearnTitledPanel( String title, Font font ) {
        titledBorder = new AbcLearnTitledBorder( title, font );
        setBorder( titledBorder );
        addContainerListener( new ContainerAdapter() {
            @Override
            public void componentAdded( ContainerEvent e ) {
                updateLayout();
            }

            @Override
            public void componentRemoved( ContainerEvent e ) {
                updateLayout();
            }
        } );
        updateLayout();
    }

    public void setTitleColor( Color color ) {
        titledBorder.setTitleColor( color );
    }

    /**
     * Updates the preferred size to account for the titled border (if necessary).
     */
    private void updateLayout() {
        setPreferredSize( null ); //forget the old preferred size so that getPreferredSize() will recompute it
        final int minBorderWidth = titledBorder.getMinimumSize( this ).width + AMOUNT_TO_EXTEND_BEYOND_TITLE;
        if ( getPreferredSize().getWidth() < minBorderWidth ) {
            setPreferredSize( new Dimension( minBorderWidth, getPreferredSize().height ) );
        }
    }

    /**
     * This sample main demonstrates usage of the AbcLearnTitledPanel
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JPanel contentPane = new AbcLearnTitledPanel( "aoensuthasnotehuBorder" );
        contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ) );
        for ( int i = 0; i < 10; i++ ) {
            contentPane.add( new JLabel( "medium sized label " + i ) );
        }
        contentPane.add( new JButton( "A button" ) );
        frame.setContentPane( contentPane );
        frame.pack();
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
