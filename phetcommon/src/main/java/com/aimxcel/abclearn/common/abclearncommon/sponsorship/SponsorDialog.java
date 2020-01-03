

package com.aimxcel.abclearn.common.abclearncommon.sponsorship;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions.windowClosing;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents.sponsorDialog;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.Timer;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.SystemActions;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.SystemComponentTypes;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.SystemComponents;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

/**
 * Dialog that displays a simulation's sponsor.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SponsorDialog extends JDialog {

    private static final int DISPLAY_TIME = 5; // seconds

    // Constructor is private, creation and display is handled by static methods.
    private SponsorDialog( AbcLearnApplicationConfig config, Frame parent ) {
        super( parent );
        setResizable( false );
        setContentPane( new SponsorPanel( config ) );
        SwingUtils.setBackgroundDeep( this, Color.WHITE ); // color expected by sponsors
        pack();

        // click on the dialog to make it go away
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent event ) {
                SimSharingManager.sendUserMessage( sponsorDialog, UserComponentTypes.dialog, UserActions.pressed );
                dispose();
            }
        } );
    }

    // Show the dialog, centered in the parent frame.
    public static JDialog show( AbcLearnApplicationConfig config, Frame parent, boolean startDisposeTimer ) {

        final JDialog dialog = new SponsorDialog( config, parent );
        dialog.addWindowListener( new WindowAdapter() {
            // called when the close button in the dialog's window dressing is clicked
            public void windowClosing( WindowEvent e ) {
                SimSharingManager.sendUserMessage( sponsorDialog, UserComponentTypes.dialog, windowClosing );
                dialog.dispose();
            }
        } );
        SwingUtils.centerInParent( dialog );
        dialog.setVisible( true );

        if ( startDisposeTimer ) {
            // Dispose of the dialog after DISPLAY_TIME seconds. Take care to call dispose in the Swing thread.
            final Timer timer = new Timer( DISPLAY_TIME * 1000, new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    if ( dialog.isDisplayable() ) {
                        SimSharingManager.sendSystemMessage( SystemComponents.sponsorDialog, SystemComponentTypes.dialog, SystemActions.windowClosed );
                        dialog.dispose();
                    }
                }
            } );
            timer.setRepeats( false );
            dialog.addWindowListener( new WindowAdapter() {
                @Override public void windowClosed( WindowEvent e ) {
                    timer.stop();
                }
            } );
            timer.start();
        }
        return dialog;
    }

    // Should the dialog be displayed?
    public static boolean shouldShow( AbcLearnApplicationConfig config ) {
        boolean isFeatureEnabled = config.isSponsorFeatureEnabled();
        boolean isWellFormed = new SponsorProperties( config ).isWellFormed();
        return isFeatureEnabled && isWellFormed;
    }
}
