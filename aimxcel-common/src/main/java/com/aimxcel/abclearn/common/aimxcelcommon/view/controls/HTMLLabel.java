

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;


public class HTMLLabel extends JLabel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color foreground;

    /**
     * Constructor
     *
     * @param text plain text, HTML fragment, or HTML document
     */
    public HTMLLabel( String text ) {
        super( HTMLUtils.toHTMLString( text ) );
        this.foreground = getForeground();
    }

    @Override
    public void setForeground( Color foreground ) {
        this.foreground = foreground;
        update();
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        update();
    }

    @Override
    public void setText( String text ) {
        super.setText( HTMLUtils.toHTMLString( text ) );
    }

    private void update() {
        super.setForeground( isEnabled() ? foreground : getDisabledColor() );
    }

    private Color getDisabledColor() {
        Color color = UIManager.getColor( "Label.disabledText" );
        if ( color == null ) {
            color = Color.GRAY;
        }
        return color;
    }

    // test
    public static void main( String[] args ) {

        HTMLLabel label1 = new HTMLLabel( "label<sub>1" );
        HTMLLabel label2 = new HTMLLabel( "label<sub>2</sub>" );
        label2.setText( "label<sub>two</sub>" ); // test setText
        label2.setEnabled( false );

        JPanel panel = new JPanel();
        panel.add( label1 );
        panel.add( label2 );

        JFrame frame = new JFrame();
        frame.setContentPane( panel );
        frame.pack();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
