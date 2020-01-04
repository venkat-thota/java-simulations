
package com.aimxcel.abclearn.core.aimxcelcore.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelTitledBorder;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PPaintContext;


public class AimxcelTitledPanel extends VerticalLayoutPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AimxcelTitledPanel( String title ) {
        this( title, AimxcelTitledBorder.DEFAULT_FONT );
    }

    public AimxcelTitledPanel( String title, final Font font ) {
        //Set up the default behavior for a vertical panel
        setFillNone(); // don't expand children to fill the width
        setAnchor( GridBagConstraints.WEST );//and put every component on the left edge

        //Create and set the AimxcelTitledPanelBorder which will render the border
        final AimxcelTitledPanelBorder border = new AimxcelTitledPanelBorder( Color.black, title, font );
        setBorder( border );

        //Add a dummy component to this panel which will ensure its layout is the same minimum width as the panels title border
        add( Box.createHorizontalStrut( border.getMinimumWidth() ) );
    }

    /**
     * This border renders the title and a round rectangle border.  The bounds of the text is used to determine the layout of the parent container.
     */
    public static class AimxcelTitledPanelBorder extends AbstractBorder {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected Color lineColor;
        protected PText text;//We use piccolo to compute bounds and render text.

        public AimxcelTitledPanelBorder( Color color, String title, final Font font ) {
            lineColor = color;
            text = new PText( title ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{setFont( font );}};
            text.setOffset( 8, 0 );//Move the title to the right a bit so the curve of the line border is visible
        }

        public void paintBorder( Component c, Graphics g, int x, int y, int width, int height ) {
            //Store original graphic state
            Graphics2D g2 = (Graphics2D) g;
            Shape origClip = g.getClip();
            Paint origPaint = g2.getPaint();
            Stroke origStroke = g2.getStroke();
            Object oldAntialiasHint = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING );

            //Prepare to draw
            final double offset = text.getFullBounds().getHeight() / 2;
            RoundRectangle2D.Double shape = new RoundRectangle2D.Double( x + 2, y + offset, width - 3, height - 1 - offset, 8, 8 );
            Area clip = new Area( g.getClip() );
            clip.subtract( new Area( text.getFullBounds() ) );

            //Mutate the graphics state
            g.setColor( lineColor );
            g2.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
            g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setClip( clip );

            //Draw the shape
            g2.draw( shape );

            //Restore the clip for rendering the text
            g2.setClip( origClip );

            //Render the text
            text.fullPaint( new PPaintContext( g2 ) );

            //Restore the rest of the graphics state
            g2.setPaint( origPaint );
            g2.setStroke( origStroke );
            g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, oldAntialiasHint );
        }

        /**
         * Gets a border insets big enough to show the line border and title border.
         *
         * @param c component in which this border will be rendered
         * @return the insets
         */
        public Insets getBorderInsets( Component c ) {
            return new Insets( (int) Math.ceil( text.getFullBounds().getHeight() ), 5, 3, 5 );
        }

        public boolean isBorderOpaque() {
            return true;
        }

        /**
         * Determines the minimum width of this component, enough space to display the entire title without adding ellipses.
         *
         * @return the title width
         */
        public int getMinimumWidth() {
            return (int) Math.ceil( text.getFullBounds().getWidth() + 5 );
        }
    }

    /**
     * This sample main demonstrates usage of the AimxcelTitledPanel
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JPanel contentPane = new AimxcelTitledPanel( "aoensuthasnotehuBorder" );
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
