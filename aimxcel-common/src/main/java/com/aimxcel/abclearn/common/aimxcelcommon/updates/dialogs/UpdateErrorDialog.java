

package com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class UpdateErrorDialog extends ErrorDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MESSAGE = AimxcelCommonResources.getString( "Common.updates.errorMessage" );

    public UpdateErrorDialog( Frame owner, final Exception exception ) {
        super( owner, getErrorMessage(), exception );
        SwingUtils.centerDialogInParent( this );
    }

    protected static String getErrorMessage() {
        Object[] args = { HTMLUtils.getAimxcelHomeHref() };
        String message = MessageFormat.format( ERROR_MESSAGE, args );
        return message;
    }

    // test
    public static void main( String[] args ) {
        // dialog must have an owner if you want cursor to change over hyperlinks
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
        JDialog dialog = new UpdateErrorDialog( frame, new IOException() );
        dialog.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit( 0 );
            }

            public void windowClosed( WindowEvent e ) {
                System.exit( 0 );
            }
        } );
        dialog.setVisible( true );
    }
}
