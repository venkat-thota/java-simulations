

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.Color;

import javax.swing.UIManager;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;

/**
 * A radio button whose text is HTML.  Swing doesn't properly handle the "graying out"
 * of text for JComponents that use HTML strings. See Unfuddle #1704.  This is a
 * quick-and-dirty workaround for one type of JComponent. A more general solution
 * is needed - or better yet, a Java fix.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class HTMLRadioButton extends SimSharingJRadioButton {

    private Color foreground;

    /**
     * Constructor
     *
     * @param text plain text, HTML fragment, or HTML document
     */
    public HTMLRadioButton( IUserComponent simSharingObject, String text ) {
        super( simSharingObject, HTMLUtils.toHTMLString( text ) );
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
        Color color = UIManager.getColor( "RadioButton.disabledText" );
        if ( color == null ) {
            color = Color.GRAY;
        }
        return color;
    }
}
