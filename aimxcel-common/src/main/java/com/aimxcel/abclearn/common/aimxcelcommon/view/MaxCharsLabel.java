

package com.aimxcel.abclearn.common.aimxcelcommon.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.view.MaxCharsLabel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;


public class MaxCharsLabel extends JLabel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ELLIPSIS = "...";

    private final int maxChars;
    private final int endChars;

    /**
     * Puts the ellipsis on the end of the label's text.
     *
     * @param maxChars
     */
    public MaxCharsLabel( int maxChars ) {
        this( maxChars, 0 );
    }

    /**
     * Puts the ellipsis endChars from the end of the label's text.
     * Allows you to always show the last endChars characters.
     * This is useful when dealing with long file path, where the last part
     * of the pathname is the most informative.
     *
     * @param maxChars
     * @param endChars
     */
    public MaxCharsLabel( int maxChars, int endChars ) {
        super();
        assert ( maxChars > 0 );
        assert ( endChars <= maxChars - ELLIPSIS.length() );
        assert ( endChars <= maxChars );
        this.maxChars = maxChars;
        this.endChars = endChars;
    }

    /**
     * Sets the text.
     * If the text length is more than maxChars, it is truncated, and an ellipsis is inserted.
     *
     * @param text
     */
    public void setText( String text ) {
        String adjustedText = text;
        if ( adjustedText.length() > maxChars ) {
            int endIndex = text.length() - endChars;
            String endString = text.substring( endIndex );
            assert ( endString.length() == endChars );
            String beginString = text.substring( 0, maxChars - ELLIPSIS.length() - endChars );
            adjustedText = beginString + ELLIPSIS + endString;
            assert ( adjustedText.length() == maxChars );
        }
        super.setText( adjustedText );
    }

    /**
     * Gets the length of the ellipsis.
     *
     * @return
     */
    public static int getEllipsisLength() {
        return ELLIPSIS.length();
    }

    /*
     * Test
     */
    public static void main( String[] args ) {

        System.out.println( "Are you running this test with assertions enabled? (java -ea)" );

        final String s = "1234567890123456789012345"; // 25 chars

        // ellipsis at end
        MaxCharsLabel l1 = new MaxCharsLabel( 20 );
        l1.setText( s );

        // ellipsis at beginning
        MaxCharsLabel l2 = new MaxCharsLabel( 20, 20 - MaxCharsLabel.getEllipsisLength() );
        l2.setText( s );

        // ellipsis in middle
        MaxCharsLabel l3 = new MaxCharsLabel( 20, 10 );
        l3.setText( s );

        // no ellipsis, exact length
        MaxCharsLabel l4 = new MaxCharsLabel( s.length() );
        l4.setText( s );

        // no ellipsis, shorter string
        MaxCharsLabel l5 = new MaxCharsLabel( s.length() );
        l5.setText( s.substring( 0, 10 ) );

        // empty string
        MaxCharsLabel l6 = new MaxCharsLabel( s.length() );
        l6.setText( "" );

        JPanel panel = new VerticalLayoutPanel();
        panel.add( l1 );
        panel.add( l2 );
        panel.add( l3 );
        panel.add( l4 );
        panel.add( l5 );
        panel.add( l6 );

        JFrame frame = new JFrame();
        frame.getContentPane().add( panel );
        frame.setSize( 200, 200 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
