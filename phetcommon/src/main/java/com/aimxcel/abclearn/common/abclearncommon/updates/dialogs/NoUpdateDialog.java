
package com.aimxcel.abclearn.common.abclearncommon.updates.dialogs;

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

import com.aimxcel.abclearn.common.abclearncommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnInstallerVersion;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnVersion;
import com.aimxcel.abclearn.common.abclearncommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.abclearncommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.abclearncommon.view.util.HTMLUtils.InteractiveHTMLPane;

/**
 * Dialog uses to inform the user that no sim update is available.
 * This is used in situations where the user has manually requested an update check.
 */
public abstract class NoUpdateDialog extends PaintImmediateDialog {

    private static final String TITLE = AbcLearnCommonResources.getString( "Common.updates.updateToDate" );
    private static final String OK_BUTTON = AbcLearnCommonResources.getString( "Common.choice.ok" );
    private static final String MESSAGE_PATTERN = AbcLearnCommonResources.getString( "Common.updates.youHaveCurrent" );
    private static final String ABC_LEARN_INSTALLER = AbcLearnCommonResources.getString( "Common.phetInstaller" );

    public static class SimNoUpdateDialog extends NoUpdateDialog {
        public SimNoUpdateDialog( Frame owner, String simName, AbcLearnVersion currentVersion ) {
            super( owner, simName, currentVersion.formatMajorMinor() );
        }
    }

    public static class InstallerNoUpdateDialog extends NoUpdateDialog {
        public InstallerNoUpdateDialog( Frame owner, AbcLearnInstallerVersion currentVersion ) {
            super( owner, ABC_LEARN_INSTALLER, currentVersion.formatTimestamp() );
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

        AbcLearnVersion version = new AbcLearnVersion( "1", "01", "02", "123456789", String.valueOf( new Date().getTime() / 1000L ) );
        JDialog dialog = new SimNoUpdateDialog( null, "Glaciers", version );
        SwingUtils.centerWindowOnScreen( dialog );
        dialog.setVisible( true );

        AbcLearnInstallerVersion installerVersion = new AbcLearnInstallerVersion( new Date().getTime() / 1000L );
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
