

package com.aimxcel.abclearn.common.aimxcelcommon.dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.StackTraceDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils.InteractiveHTMLPane;

public class ErrorDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = AimxcelCommonResources.getString( "Common.title.error" );
    private static final String CLOSE_BUTTON = AimxcelCommonResources.getString( "Common.choice.close" );
    private static final String DETAILS_BUTTON = AimxcelCommonResources.getString( "Common.ErrorDialog.detailsButton" );
    private static final String DETAILS_TITLE = AimxcelCommonResources.getString( "Common.ErrorDialog.detailsTitle" );
    private static final String CONTACT_ABC_LEARN = AimxcelCommonResources.getString( "Common.ErrorDialog.contactAimxcel" );

    public ErrorDialog( Frame owner, String message ) {
        this( owner, message, null /* exception */ );
    }

    public ErrorDialog( Frame owner, String message, final Exception exception ) {
        super( owner, TITLE );
        init( message, exception );
    }

    public ErrorDialog( JDialog owner, String message ) {
        this( owner, message, null /* exception */ );
    }

    public ErrorDialog( JDialog owner, String message, final Exception exception ) {
        super( owner, TITLE );
        init( message, exception );
    }

    private void init( String message, final Exception exception ) {
        setResizable( false );
        setModal( true );

        JPanel messagePanel = new JPanel();
        messagePanel.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        String htmlString = HTMLUtils.createStyledHTMLFromFragment( message );
        JComponent htmlPane = new InteractiveHTMLPane( htmlString );
        messagePanel.add( htmlPane );
        htmlPane.setBackground( messagePanel.getBackground() );

        JPanel buttonPanel = new JPanel();

        // closes the dialog
        JButton closeButton = new JButton( CLOSE_BUTTON );
        closeButton.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );
        buttonPanel.add( closeButton );

        // Details button
        if ( exception != null ) {
            final JButton detailsButton = new JButton( DETAILS_BUTTON );
            detailsButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent event ) {
                    // show a stack trace
                    String htmlMessage = getContactAimxcelMessageHTML();
                    JDialog dialog = new StackTraceDialog( ErrorDialog.this, DETAILS_TITLE, htmlMessage, exception );
                    SwingUtils.centerDialogInParent( dialog );
                    dialog.setModal( ErrorDialog.this.isModal() ); // use same modality for details
                    dialog.setVisible( true );
                }
            } );
            buttonPanel.add( detailsButton );
        }

        // layout
        JPanel panel = new JPanel();
        EasyGridBagLayout layout = new EasyGridBagLayout( panel );
        panel.setLayout( layout );
        int row = 0;
        int column = 0;
        layout.addComponent( messagePanel, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.addAnchoredComponent( buttonPanel, row++, column, GridBagConstraints.CENTER );

        setContentPane( panel );
        pack();
        SwingUtils.centerDialogInParent( this );
    }

    private static String getContactAimxcelMessageHTML() {
        Object[] args = { HTMLUtils.getAimxcelMailtoHref() };
        String htmlFragment = MessageFormat.format( CONTACT_ABC_LEARN, args );
        return HTMLUtils.createStyledHTMLFromFragment( htmlFragment );
    }

    // test
    public static void main( String[] args ) {
        // dialog must have an owner if you want cursor to change over hyperlinks
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
        String htmlMessage = "<html>Something very bad<br>just happened.</html>";
        JDialog dialog = new ErrorDialog( frame, htmlMessage, new IOException() );
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
