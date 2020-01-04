
package com.aimxcel.abclearn.common.aimxcelcommon.sponsorship;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;

/**
 * Menu item for displaying simulation sponsor.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SponsorMenuItem extends JMenuItem {

    private JDialog dialog;

    public SponsorMenuItem( final AimxcelApplicationConfig config, final Frame frame ) {
        super( AimxcelCommonResources.getString( "Sponsor.sponsorMenuItem" ) );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( dialog == null ) {
                    dialog = SponsorDialog.show( config, frame, false /* startDisposeTimer */ );
                    dialog.addWindowListener( new WindowAdapter() {

                        // called when the close button in the dialog's window dressing is clicked
                        public void windowClosing( WindowEvent e ) {
                            dialog.dispose();
                        }

                        // called by JDialog.dispose
                        public void windowClosed( WindowEvent e ) {
                            dialog = null;
                        }
                    } );
                }
            }
        } );
    }
}
