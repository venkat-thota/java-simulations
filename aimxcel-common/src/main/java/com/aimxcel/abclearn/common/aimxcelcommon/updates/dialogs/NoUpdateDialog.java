
package com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelInstallerVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils.InteractiveHTMLPane;


public abstract class NoUpdateDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = AimxcelCommonResources.getString( "Common.updates.updateToDate" );
    private static final String OK_BUTTON = AimxcelCommonResources.getString( "Common.choice.ok" );
    private static final String MESSAGE_PATTERN = AimxcelCommonResources.getString( "Common.updates.youHaveCurrent" );
    private static final String AIMXCEL_INSTALLER = AimxcelCommonResources.getString( "Common.aimxcelInstaller" );

    public static class SimNoUpdateDialog extends NoUpdateDialog {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SimNoUpdateDialog( Frame owner, String simName, AimxcelVersion currentVersion ) {
            super( owner, simName, currentVersion.formatMajorMinor() );
        }
    }

    public static class InstallerNoUpdateDialog extends NoUpdateDialog {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InstallerNoUpdateDialog( Frame owner, AimxcelInstallerVersion currentVersion ) {
            super( owner, AIMXCEL_INSTALLER, currentVersion.formatTimestamp() );
        }
    }

    protected NoUpdateDialog( Frame owner, String productName, String currentVersion ) {
        super( owner, TITLE );
        setModal( true );
        setResizable( false );

        // notification that there is no need to update
        String html = getUpToDateHTML( currentVersion, productName );
        JComponent htmlPane = new InteractiveHTMLPane( html );
        htmlPane.setBackground( new JPanel().getBackground() ); // see #1532
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        messagePanel.add( htmlPane );

        // closes the dialog
        JButton okButton = new JButton( OK_BUTTON );
        okButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );

        JPanel buttonPanel = new JPanel();
        buttonPanel.add( okButton );

        // main panel layout
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

    private static String getUpToDateHTML( String currentVersion, String simName ) {
        Object[] args = { currentVersion, simName };
        String htmlFragment = MessageFormat.format( MESSAGE_PATTERN, args );
        return HTMLUtils.createStyledHTMLFromFragment( htmlFragment );
    }

    public static void main( String[] args ) {

        AimxcelVersion version = new AimxcelVersion( "1", "01", "02", "123456789", String.valueOf( new Date().getTime() / 1000L ) );
        JDialog dialog = new SimNoUpdateDialog( null, "Glaciers", version );
        SwingUtils.centerWindowOnScreen( dialog );
        dialog.setVisible( true );

        AimxcelInstallerVersion installerVersion = new AimxcelInstallerVersion( new Date().getTime() / 1000L );
        JDialog dialog2 = new InstallerNoUpdateDialog( null, installerVersion );
        dialog2.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit( 0 );
            }

            public void windowClosed( WindowEvent e ) {
                System.exit( 0 );
            }
        } );
        SwingUtils.centerWindowOnScreen( dialog2 );
        dialog2.setVisible( true );
    }

}
