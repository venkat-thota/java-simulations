
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;


public class TabbedPanePropertiesMenuItem extends JCheckBoxMenuItem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ITEM_LABEL = "Tabbed Pane properties..."; // developer control, i18n not required

    private JDialog dialog;

    public TabbedPanePropertiesMenuItem( final Frame owner, final AimxcelTabbedPane tabbedPane ) {
        super( ITEM_LABEL );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                handleAction( owner, tabbedPane );
            }
        } );
    }

    private void handleAction( final Frame owner, final AimxcelTabbedPane tabbedPane ) {
        if ( isSelected() ) {
            dialog = new TabbedPanePropertiesDialog( owner, tabbedPane );
            dialog.setVisible( true );
            dialog.addWindowListener( new WindowAdapter() {

                public void windowClosed( WindowEvent e ) {
                    cleanup();
                }

                public void windowClosing( WindowEvent e ) {
                    cleanup();
                }

                private void cleanup() {
                    setSelected( false );
                    dialog = null;
                }
            } );
        }
        else {
            dialog.dispose();
        }
    }
}
