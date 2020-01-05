

package com.aimxcel.abclearn.magnetsandelectromagnets.control.panel;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;



public class MagnetsAndElectromagnetsPanel extends JPanel {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    public static final Dimension SLIDER_SIZE = new Dimension( 100, 20 );
    public static final Dimension SPINNER_SIZE = new Dimension( 50, 20 );
    public static final String UNKNOWN_VALUE = "??";
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private Font _titleFont;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public MagnetsAndElectromagnetsPanel() {
        super();
        _titleFont = new AimxcelFont( Font.BOLD, AimxcelFont.getDefaultFontSize() + 2 );
    }
    
    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------
    
    /**
     * Gets the font to be used for titled borders.
     * 
     * @return the font
     */
    public Font getTitleFont() {
        return _titleFont; // Fonts are immutable
    }
}
