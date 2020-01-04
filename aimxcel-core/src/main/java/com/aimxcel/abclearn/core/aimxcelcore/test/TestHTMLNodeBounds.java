

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.pswing.PSwingCanvas;

public class TestHTMLNodeBounds extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MIN_FONT_SIZE = 10;
    private static final int MAX_FONT_SIZE = 100;
    private static final int DEFAULT_FONT_SIZE = 100;

    private final PPath boundsNode;
    private final HTMLNode htmlNode;

    public TestHTMLNodeBounds() {
        super( TestHTMLNodeBounds.class.getName() );
        setSize( new Dimension( 800, 450 ) );

        // Piccolo canvas
        PSwingCanvas canvas = new PSwingCanvas();
        canvas.removeInputEventListener( canvas.getZoomEventHandler() );
        canvas.removeInputEventListener( canvas.getPanEventHandler() );

        // the HTMLNode
        String html = "<html><center>How many<br>reactants<br>in this reaction?</center></html>";
        htmlNode = new HTMLNode( html );
        htmlNode.setFont( new AimxcelFont( DEFAULT_FONT_SIZE ) );
        htmlNode.addPropertyChangeListener( new PropertyChangeListener() {
            // when the bounds change, update boundsNode
            public void propertyChange( PropertyChangeEvent event ) {
                if ( event.getPropertyName().equals( PNode.PROPERTY_FULL_BOUNDS ) ) {
                    updateBoundsNode();
                }
            }
        } );
        canvas.getLayer().addChild( htmlNode );

        // displays the bounds of htmlNode
        boundsNode = new PPath( htmlNode.getBounds() );
        boundsNode.setStroke( new BasicStroke( 1f ) );
        boundsNode.setStrokePaint( Color.RED );
        canvas.getLayer().addChild( boundsNode );

        // label to show font name
        JLabel fontNameLabel = new JLabel( "font face name: " + new AimxcelFont().getFontName() );

        // spinner to change the font size of htmlNode
        final JSpinner fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setModel( new SpinnerNumberModel( DEFAULT_FONT_SIZE, MIN_FONT_SIZE, MAX_FONT_SIZE, 1 ) );
        NumberEditor editor = new NumberEditor( fontSizeSpinner );
        // don't allow editing of the textfield, so we don't have to handle bogus input
        editor.getTextField().setEditable( false );
        fontSizeSpinner.setEditor( editor );
        fontSizeSpinner.addChangeListener( new ChangeListener() {
            // when the spinner changes, update htmlNode font
            public void stateChanged( ChangeEvent e ) {
                int fontSize = ( (Integer) fontSizeSpinner.getValue() ).intValue();
                htmlNode.setFont( new AimxcelFont( fontSize ) );
            }
        } );

        JPanel controlPanel = new JPanel();
        controlPanel.add( fontNameLabel );
        controlPanel.add( Box.createHorizontalStrut( 40 ) );
        controlPanel.add( new JLabel( "font size:" ) );
        controlPanel.add( fontSizeSpinner );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.add( canvas, BorderLayout.CENTER );
        mainPanel.add( controlPanel, BorderLayout.SOUTH );

        setContentPane( mainPanel );
    }

    private void updateBoundsNode() {
        boundsNode.setPathTo( htmlNode.getBounds() );
    }

    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame frame = new TestHTMLNodeBounds();
                frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
                SwingUtils.centerWindowOnScreen( frame );
                frame.setVisible( true );
            }
        } );
    }
}

//Sample application that mimics the above behavior, but using Swing only (no Piccolo).
class Test2 {
    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                final JLabel label = new JLabel( "<html><center>How many<br>reactants<br>in this reaction?</center></html>" );
                frame.setContentPane( new JComponent() {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					protected void paintComponent( Graphics g ) {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setPaint( Color.blue );
                        g2.setStroke( new BasicStroke( 1 ) );
                        g2.drawRect( 0, 0, label.getPreferredSize().width, label.getPreferredSize().height );
                    }
                } );

                label.setForeground( Color.red );
                label.setFont( new AimxcelFont( 53 ) );
                System.out.println( "label.getPreferredSize() = " + label.getPreferredSize() );
                frame.getContentPane().setLayout( null );
                frame.getContentPane().add( label );
                label.setBounds( 0, 0, label.getPreferredSize().width, label.getPreferredSize().height );

                frame.setVisible( true );
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setSize( new Dimension( 800, 450 ) );
            }
        } );
    }
}
