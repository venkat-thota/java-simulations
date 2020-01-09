// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;

/**
 * HelpButton is a button for toggling module help.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class HelpButton extends JButton {
    
    public static final String SHOW_HELP = AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpPanel.ShowHelp" );
    public static final String HIDE_HELP = AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpPanel.HideHelp" );

    private final Module _module;
    
    public HelpButton( Module module ) {
        super();
        
        _module = module;
        
        setEnabled( module.hasHelp() );
        
        // set button to maximum width
        setText( HIDE_HELP );
        double hideWidth = getPreferredSize().getWidth();
        setText( SHOW_HELP );
        double showWidth = getPreferredSize().getWidth();
        setPreferredSize( new Dimension( (int) Math.max( hideWidth, showWidth ), (int) getPreferredSize().getHeight() ) );
        
        // toggle help when the button is pressed
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                setHelpEnabled( !isHelpEnabled() );
            }
        } );
    }
    
    public void setHelpEnabled( boolean b ) {
        if ( b != isHelpEnabled() ) {
            setText( b ? HIDE_HELP : SHOW_HELP );
            _module.setHelpEnabled( b );
        }
    }
    
    public boolean isHelpEnabled() {
        return getText().equals( HIDE_HELP );
    }
}
