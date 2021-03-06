
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestJButtonAlignment extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestJButtonAlignment() {
        super();

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 400, 300 ) );

        final double xOffset = 50;

        // a PNode rectangle, for alignment reference
        PPath rectNode = new PPath( new Rectangle2D.Double( 0, 0, 100, 50 ) );
        rectNode.setPaint( Color.RED );
        canvas.getLayer().addChild( rectNode );
        rectNode.setOffset( xOffset, 50 );

        // JLabel left-aligns OK
        PSwing labelNode = new PSwing( new JLabel( "PSwing JLabel" ) );
        canvas.getLayer().addChild( labelNode );
        labelNode.setOffset( xOffset, rectNode.getFullBoundsReference().getMaxY() + 10 );

        // JButton doesn't left-align perfectly, there is a bit of blank space to the left of the button
        JButton button = new JButton( "PSwing JButton" );
        PSwing buttonNode = new PSwing( button );
        canvas.getLayer().addChild( buttonNode );
        buttonNode.setOffset( xOffset, labelNode.getFullBoundsReference().getMaxY() + 10 );

        // Workaround, a little better but still some space.
        // And as a general workaround, this will violate the Aqua style guide for usage of button types.
        // See button types at http://nadeausoftware.com/node/87
        JButton button2 = new JButton( "Workaround JButton" );
        if ( AimxcelUtilities.isMacintosh() ) {
            button2.putClientProperty( "JButton.buttonType", "bevel" );
        }
        PSwing buttonNode2 = new PSwing( button2 );
        canvas.getLayer().addChild( buttonNode2 );
        buttonNode2.setOffset( xOffset, buttonNode.getFullBoundsReference().getMaxY() + 10 );

        // JPanel with a JLabel and JButton, same space appears to left of JButton
        JPanel panel = new JPanel();
        panel.setBorder( new LineBorder( Color.BLACK ) );
        panel.setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST; // left align
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        panel.add( new JLabel( "JLabel" ), constraints );
        panel.add( new JButton( "JButton" ), constraints );
        PSwing pswing = new PSwing( panel );
        canvas.getLayer().addChild( pswing );
        pswing.setOffset( xOffset, buttonNode2.getFullBoundsReference().getMaxY() + 10 );

        getContentPane().add( canvas );
        pack();
    }

    public static void main( String[] args ) {
        JFrame frame = new TestJButtonAlignment();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
