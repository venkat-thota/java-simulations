

package com.aimxcel.abclearn.common.aimxcelcommon.dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.DownloadProgressDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DownloadThread;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DownloadThread.DebugDownloadThreadListener;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DownloadThread.DownloadThreadListener;
import com.aimxcel.abclearn.common.aimxcelcommon.view.MaxCharsLabel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

public class DownloadProgressDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    private static final int MIN_PANEL_WIDTH = 400;
    private static final int STATUS_MAX_CHARS = 50;
    private static final int STATUS_END_CHARS = STATUS_MAX_CHARS / 2;  // ellipsis will be this many chars from end of status message

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private final DownloadThread downloadThread;
    private final DownloadThreadListener downloadThreadListener;
    private final JProgressBar progressBar;
    private final MaxCharsLabel statusLabel;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public DownloadProgressDialog( Frame owner, String title, String message, DownloadThread downloader ) {
        super( owner, title );
        setModal( true );
        setResizable( false );

        this.downloadThread = downloader;
        this.downloadThreadListener = new ThisDownloadThreadListener();
        this.downloadThread.addListener( downloadThreadListener );

        // message
        JLabel messageLabel = new JLabel( message );

        // progress bar
        progressBar = new JProgressBar( 0, 100 );

        // status message
        statusLabel = new MaxCharsLabel( STATUS_MAX_CHARS, STATUS_END_CHARS );
        statusLabel.setFont( new AimxcelFont( 10 ) );

        // Cancel button
        JButton cancelButton = new JButton( AimxcelCommonResources.getString( "Common.choice.cancel" ) );
        cancelButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                doCancel();
            }
        } );

        // Close button in window dressing acts like Cancel button
        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                doCancel();
            }
        } );

        // layout
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        EasyGridBagLayout layout = new EasyGridBagLayout( panel );
        layout.setMinimumWidth( 0, MIN_PANEL_WIDTH ); //TODO: shouldn't have to do this, but layout is messed up if we don't
        panel.setLayout( layout );
        int row = 0;
        int column = 0;
        layout.setInsets( new Insets( 10, 5, 10, 5 ) ); // top, left, bottom, right
        layout.addComponent( messageLabel, row++, column );
        layout.setInsets( new Insets( 5, 5, 5, 5 ) ); // top, left, bottom, right
        layout.addFilledComponent( progressBar, row++, column, GridBagConstraints.HORIZONTAL );
        layout.addComponent( statusLabel, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.setInsets( new Insets( 5, 5, 10, 5 ) ); // top, left, bottom, right
        layout.addAnchoredComponent( cancelButton, row, column, GridBagConstraints.CENTER );

        getContentPane().add( panel );
        pack();
        SwingUtils.centerDialog( this, owner );
    }

    private void doCancel() {
        downloadThread.cancel();
    }

    //----------------------------------------------------------------------------
    // Superclass overrides
    //----------------------------------------------------------------------------

    public void dispose() {
        downloadThread.removeListener( downloadThreadListener );
        super.dispose();
    }

    //----------------------------------------------------------------------------
    // DownloaderListener implementation
    //----------------------------------------------------------------------------

    /*
    * These callbacks are made from the download thread, and they perform Swing operations.
    * So all of the Swing operations must be wrapped in SwingUtilities.invokeLater to
    * ensure that they are performed in the Swing thread.
    */
    private class ThisDownloadThreadListener implements DownloadThreadListener {

        public void succeeded() {
            disposeProgressDialog();
        }

        public void failed() {
            // do nothing, specific error reported by error()
        }

        public void canceled() {
            disposeProgressDialog();
        }

        public void requestAdded( String requestName, String sourceURL, File destinationFile ) {
            // don't care about request additions
        }

        public void progress( final String requestName, String sourceURL, File destinationFile, double percentOfSource, final double percentOfTotal ) {
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    // update the progress display
                    statusLabel.setText( requestName );
                    progressBar.setValue( (int) ( percentOfTotal * ( progressBar.getMaximum() - progressBar.getMinimum() ) ) );
                }
            } );
        }

        public void completed( String requestName, String sourceURL, File destinationFile ) {
            // do nothing
        }

        public void error( String requestName, String sourceURL, File destinationFile, final String message, final Exception e ) {
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    // display the error
                    ErrorDialog dialog = new ErrorDialog( DownloadProgressDialog.this, message, e );
                    dialog.setVisible( true );
                }
            } );
            disposeProgressDialog();
        }

        // disposes of the progress dialog
        private void disposeProgressDialog() {
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    dispose();
                }
            } );
        }
    }

    //----------------------------------------------------------------------------
    // Test
    //----------------------------------------------------------------------------

    public static void main( String[] args ) throws IOException {

        // create download thread
        DownloadThread downloadThread = new DownloadThread();

        // add a listener
        downloadThread.addListener( new DebugDownloadThreadListener() );

        // add download requests
        String tmpDirName = System.getProperty( "java.io.tmpdir" ) + System.getProperty( "file.separator" );
        downloadThread.addRequest( "downloading glaciers.jar", HTMLUtils.getSimJarURL( "glaciers", "glaciers", new Locale( "en" ) ), tmpDirName + "glaciers.jar" );
        downloadThread.addRequest( "downloading ph-scale.jar", HTMLUtils.getSimJarURL( "ph-scale", "ph-scale", new Locale( "en" ) ), tmpDirName + "ph-scale.jar" );

        // progress dialog
        DownloadProgressDialog dialog = new DownloadProgressDialog( null, "Download Progress", "Downloading simulation JAR files", downloadThread );
        dialog.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit( 0 );
            }

            public void windowClosed( WindowEvent e ) {
                System.exit( 0 );
            }
        } );

        // start the download
        downloadThread.start();
        dialog.setVisible( true );
    }
}
