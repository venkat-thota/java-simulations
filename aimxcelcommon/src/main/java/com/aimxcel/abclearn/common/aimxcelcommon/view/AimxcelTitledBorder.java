
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLineBorder;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelTitledBorder;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

/**
 * This is the default Border to be used in subsections of the control panel in phet simulations.
 * It provides the proper font and font style, and provides antialiasing and curved edges for the border.
 * See ticket #2476: https://phet.unfuddle.com/a#/projects/9404/tickets/by_number/2476
 * <p/>
 * Note that using the border by itself may crop the text if it extends beyond the component length,
 * therefore clients should typically use AbcLearnTitledPanel instead, which guarantees that the entire titled border
 * will remain visible by expanding the component width.
 *
 * @author Sam Reid
 * @author Chris Malley
 */
public class AimxcelTitledBorder extends TitledBorder {
    public static final AimxcelFont DEFAULT_FONT = new AimxcelFont( Font.BOLD, AimxcelFont.getDefaultFontSize() + 4 );

    public AimxcelTitledBorder( String title ) {
        super( new AimxcelLineBorder(), title );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( String title, int titleJustification, int titlePosition ) {
        super( new AimxcelLineBorder(), title, titleJustification, titlePosition );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( String title, int titleJustification, int titlePosition, Font titleFont ) {
        super( new AimxcelLineBorder(), title, titleJustification, titlePosition, titleFont );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor ) {
        super( new AimxcelLineBorder(), title, titleJustification, titlePosition, titleFont, titleColor );
        init( DEFAULT_FONT );
    }

    //The following methods allow the client to specify an underlying border other than the default line border

    public AimxcelTitledBorder( Border border ) {
        super( border );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( Border border, String title ) {
        super( border, title );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( Border border, String title, int titleJustification, int titlePosition ) {
        super( border, title, titleJustification, titlePosition );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( Border border, String title, int titleJustification, int titlePosition, Font titleFont ) {
        super( border, title, titleJustification, titlePosition, titleFont );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( Border border, String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor ) {
        super( border, title, titleJustification, titlePosition, titleFont, titleColor );
        init( DEFAULT_FONT );
    }

    public AimxcelTitledBorder( String title, Font font ) {
        super( new AimxcelLineBorder(), title );
        init( font );
    }

    private void init( Font font ) {
        setTitleFont( font );
    }

    @Override
    public void paintBorder( Component c, Graphics g, int x, int y, int width, int height ) {
        //Overrides to add antialiasing, otherwise looks terrible on Windows
        Graphics2D g2 = (Graphics2D) g;
        Object oldAntialiasHint = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING );
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        super.paintBorder( c, g, x, y, width, height );
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, oldAntialiasHint );
    }

    /**
     * This sample main demonstrates usage of the AbcLearnTitledBorder
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JPanel contentPane = new VerticalLayoutPanel();
        contentPane.setBorder( new AimxcelTitledBorder( "Border" ) );
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