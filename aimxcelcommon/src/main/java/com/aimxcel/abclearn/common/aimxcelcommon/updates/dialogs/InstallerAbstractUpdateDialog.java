

package com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelInstallerVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.IAskMeLaterStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

/**
 * Base class for installer update dialogs.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public abstract class InstallerAbstractUpdateDialog extends PaintImmediateDialog {

    private static final String TITLE = AimxcelCommonResources.getString( "Common.updates.updateAvailable" );
    private static final String UPDATE_BUTTON = AimxcelCommonResources.getString( "Common.updates.installer.yes" );
    private static final String ASK_ME_LATER_BUTTON = AimxcelCommonResources.getString( "Common.updates.askMeLater" );
    private static final String MORE_BUTTON = AimxcelCommonResources.getString( "Common.updates.installer.more" );
    private static final String NO_BUTTON = AimxcelCommonResources.getString( "Common.choice.no" );
    private static final String MESSAGE_PATTERN = AimxcelCommonResources.getString( "Common.updates.installer.message" );
    private static final String MORE_MESSAGE = AimxcelCommonResources.getString( "Common.updates.installer.moreMessage" );

    public InstallerAbstractUpdateDialog( Frame owner ) {
        super( owner, TITLE );
        setModal( true );
        setResizable( false );
    }

    /*
    * Subclass must call this at the end of their constructor,
    * so that the GUI is initialized *after* member data is initialized.
    */
    protected void initGUI( AimxcelInstallerVersion currentVersion, AimxcelInstallerVersion newVersion ) {
        // subpanels
        JPanel messagePanel = createMessagePanel( currentVersion, newVersion );
        JPanel buttonPanel = createButtonPanel();

        // main panel
        JPanel panel = new JPanel();
        panel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
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

    private JPanel createMessagePanel( AimxcelInstallerVersion currentVersion, AimxcelInstallerVersion newVersion ) {

        String currentTimestamp = currentVersion.formatTimestamp();
        String newTimestamp = newVersion.formatTimestamp();
        Object[] args = { currentTimestamp, newTimestamp };
        String s = MessageFormat.format( MESSAGE_PATTERN, args );
        JLabel messageLabel = new JLabel( s );

        JPanel panel = new JPanel();
        panel.add( messageLabel );
        return panel;
    }

    protected abstract JPanel createButtonPanel();

    protected static class UpdateButton extends JButton {
        public UpdateButton( final JDialog dialog ) {
            super( UPDATE_BUTTON );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    AimxcelServiceManager.showWebPage( AimxcelCommonConstants.AIMXCEL_INSTALLER_URL );
                    dialog.dispose();
                }
            } );
        }
    }

    protected static class AskMeLaterButton extends JButton {
        public AskMeLaterButton( final JDialog dialog, final IAskMeLaterStrategy askMeLaterStrategy ) {
            super( ASK_ME_LATER_BUTTON );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    askMeLaterStrategy.setStartTime( System.currentTimeMillis() );
                    dialog.dispose();
                }
            } );
        }
    }

    protected static class NoButton extends JButton {
        public NoButton( final JDialog dialog ) {
            super( NO_BUTTON );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    dialog.dispose();
                }
            } );
        }
    }

    protected static class MoreButton extends JButton {
        public MoreButton( final JDialog parent ) {
            super( MORE_BUTTON );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    AimxcelOptionPane.showMessageDialog( parent, MORE_MESSAGE, TITLE );
                }
            } );
        }
    }
}
