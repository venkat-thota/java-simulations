

package com.aimxcel.abclearn.common.aimxcelcommon.sponsorship;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.windowClosing;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.sponsorDialog;

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

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

public class SponsorDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DISPLAY_TIME = 5; // seconds

    // Constructor is private, creation and display is handled by static methods.
    private SponsorDialog( AimxcelApplicationConfig config, Frame parent ) {
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
    public static JDialog show( AimxcelApplicationConfig config, Frame parent, boolean startDisposeTimer ) {

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
    public static boolean shouldShow( AimxcelApplicationConfig config ) {
        boolean isFeatureEnabled = config.isSponsorFeatureEnabled();
        boolean isWellFormed = new SponsorProperties( config ).isWellFormed();
        return isFeatureEnabled && isWellFormed;
    }
}
