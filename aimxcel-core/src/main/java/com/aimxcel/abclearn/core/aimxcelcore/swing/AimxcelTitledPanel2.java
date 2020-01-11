
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
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;


public class AimxcelTitledPanel2 extends VerticalLayoutPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AimxcelTitledPanel2( String title ) {
        this( title, AimxcelTitledBorder.DEFAULT_FONT );
    }

    public AimxcelTitledPanel2( String title, final Font font ) {
        setFillNone();
        setAnchor( GridBagConstraints.WEST );
        final AimxcelTitledPanelBorder border = new AimxcelTitledPanelBorder( Color.blue, title, font );
        setBorder( border );
        add( Box.createHorizontalStrut( border.getMinimumWidth() ) );
    }

    /**
     * This sample main demonstrates usage of the AimxcelTitledPanel
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JPanel contentPane = new AimxcelTitledPanel2( "aoensuthasnotehuBorder" );
        for ( int i = 0; i < 10; i++ ) {
            contentPane.add( new JLabel( "medium sized label " + i + "aonetuhasnoetuh" ) );
        }
        contentPane.add( new JButton( "A button" ) );
        frame.setContentPane( contentPane );
        frame.pack();
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }

    public static class AimxcelTitledPanelBorder extends AbstractBorder {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected Color lineColor;
        private final Font font;
        protected PText text;

        public AimxcelTitledPanelBorder( Color color, String title, final Font font ) {
            lineColor = color;
            this.font = font;
            text = new PText( title ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{setFont( font );}};
            text.setOffset( 8, 0 );
        }

        public void paintBorder( Component c, Graphics g, int x, int y, int width, int height ) {
            Graphics2D g2 = (Graphics2D) g;
            Shape origClip = g.getClip();
            Paint origPaint = g2.getPaint();
            Stroke origStroke = g2.getStroke();
            Object oldAntialiasHint = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING );

            final double offset = text.getFullBounds().getHeight() / 2;
            RoundRectangle2D.Double shape = new RoundRectangle2D.Double( x + 2, y + offset, width - 3, height - 1 - offset, 8, 8 );
            Area clip = new Area( g.getClip() );
            clip.subtract( new Area( text.getFullBounds() ) );

            g.setColor( lineColor );
            g2.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
            g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g2.setClip( clip );

            g2.draw( shape );

            g2.setStroke( origStroke );
            g2.setClip( origClip );
            g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, oldAntialiasHint );

            g2.setColor( Color.black );
            g2.setFont( font );
            text.fullPaint( new PPaintContext( g2 ) );

            g2.setPaint( origPaint );
        }

        public Insets getBorderInsets( Component c ) {
            return new Insets( (int) Math.ceil( text.getFullBounds().getHeight() ), 5, 3, 5 );
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public int getMinimumWidth() {
            return (int) Math.ceil( text.getFullBounds().getWidth() );
        }
    }
}
