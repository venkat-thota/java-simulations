
package com.aimxcel.abclearn.common.abclearncommon.sponsorship;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;

/**
 * Menu item for displaying simulation sponsor.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SponsorMenuItem extends JMenuItem {

    private JDialog dialog;

    public SponsorMenuItem( final AbcLearnApplicationConfig config, final Frame frame ) {
        super( AbcLearnCommonResources.getString( "Sponsor.sponsorMenuItem" ) );
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
