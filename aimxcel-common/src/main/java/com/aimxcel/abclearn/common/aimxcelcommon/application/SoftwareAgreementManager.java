
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SessionCounter;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SoftwareAgreement;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.StatisticsDetailsDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.SessionMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelExit;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils.HTMLEditorPane;

public class SoftwareAgreementManager {

    /* not intended for instantiation */
    private SoftwareAgreementManager() {
    }

    /**
     * Ensures that the user has accepted the agreements that pertain to this software.
     */
    public static void validate( Frame owner, SessionMessage sessionMessage ) {
        boolean alwaysAsk = AimxcelPreferences.getInstance().isAlwaysShowSoftwareAgreement();
        int acceptedVersion = AimxcelPreferences.getInstance().getSoftwareAgreementVersion();
        int currentVersion = SoftwareAgreement.getInstance().getVersion();
        if ( alwaysAsk || acceptedVersion < currentVersion ) {
            negotiate( owner, sessionMessage );
        }
    }

    /*
    * Negotiates the agreement with the user.
    */
    private static void negotiate( Frame owner, SessionMessage sessionMessage ) {
        final AcceptanceDialog dialog = new AcceptanceDialog( owner, sessionMessage );
        dialog.setVisible( true );
    }

    /*
     * Dialog that provides options to accept or decline.
     */
    private static class AcceptanceDialog extends PaintImmediateDialog {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final String TITLE = AimxcelCommonResources.getString( "Common.softwareAgreement.title" );
        private static final String ACCEPT_BUTTON = AimxcelCommonResources.getString( "Common.softwareAgreement.accept" );
        private static final String DECLINE_BUTTON = AimxcelCommonResources.getString( "Common.softwareAgreement.decline" );

        private JButton acceptButton;

        public AcceptanceDialog( Frame owner, SessionMessage sessionMessage ) {
            super( owner );
            setTitle( TITLE );
            setModal( true );
            setResizable( false );

            JComponent message = createMessagePanel( sessionMessage );
            JComponent buttonPanel = createButtonPanel();

            JPanel panel = new JPanel();
            EasyGridBagLayout layout = new EasyGridBagLayout( panel );
            panel.setLayout( layout );
            int row = 0;
            int column = 0;
            layout.addComponent( message, row++, column );
            layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
            layout.addFilledComponent( buttonPanel, row++, column, GridBagConstraints.HORIZONTAL );

            // close button in window dressing is identical to decline button
            addWindowListener( new WindowAdapter() {
                public void windowClosing( WindowEvent e ) {
                    decline();
                }
            } );

            setContentPane( panel );
            pack();
            SwingUtils.centerWindowOnScreen( this );

            // make "Accept" the default button and give it focus, DO THIS LAST!
            getRootPane().setDefaultButton( acceptButton );
            acceptButton.requestFocusInWindow();
        }

        private JComponent createMessagePanel( SessionMessage sessionMessage ) {
            JComponent htmlPane = new MessagePane( this, sessionMessage );
            JPanel panel = new JPanel();
            panel.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
            panel.add( htmlPane );
            return panel;
        }

        private JComponent createButtonPanel() {

            acceptButton = new JButton( ACCEPT_BUTTON );
            acceptButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    accept();
                }
            } );

            JButton declineButton = new JButton( DECLINE_BUTTON );
            declineButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    decline();
                }
            } );

            // layout
            JPanel panel = new JPanel( new FlowLayout() );
            panel.add( acceptButton );
            panel.add( declineButton );
            return panel;
        }

        /*
         * If the agreement is accepted, write the agreement version number to the preferences file.
         */
        private void accept() {
            int version = SoftwareAgreement.getInstance().getVersion();
            AimxcelPreferences.getInstance().setSoftwareAgreementVersion( version );
            dispose();
        }

        /*
         * If the agreement is declined, exit, do not allow the software to run.
         */
        private void decline() {
            SessionCounter.getInstance().decrementCounts(); // see #1254
            AimxcelExit.exit();
        }
    }

    /*
    * This is an HTML editor pane interactive hyperlinks.
    * But instead of opening a web browser, it opens a Swing dialog.
    */
    private static class MessagePane extends HTMLEditorPane {

        private static final String MESSAGE_PATTERN = AimxcelCommonResources.getString( "Common.softwareAgreement.message" );

        // identifiers for hyperlink actions
        private static final String LINK_SHOW_STATISTICS_DETAILS = "showStatisticsDetails";
        private static final String LINK_SHOW_SOFTWARE_AGREEMENT = "showSoftwareAgreements";

        public MessagePane( final JDialog owner, final SessionMessage sessionMessage ) {
            super( "" );

            // insert our own hyperlink descriptions into the message, so translators can't mess them up
            Object[] args = { LINK_SHOW_STATISTICS_DETAILS, LINK_SHOW_SOFTWARE_AGREEMENT };
            String htmlFragment = MessageFormat.format( MESSAGE_PATTERN, args );
            setText( HTMLUtils.createStyledHTMLFromFragment( htmlFragment ) );

            addHyperlinkListener( new HyperlinkListener() {
                public void hyperlinkUpdate( HyperlinkEvent e ) {
                    if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {
                        if ( e.getDescription().equals( LINK_SHOW_STATISTICS_DETAILS ) ) {
                            showStatisticsDetails( owner, sessionMessage );
                        }
                      //  else if ( e.getDescription().equals( LINK_SHOW_SOFTWARE_AGREEMENT ) ) {
                        //    showSoftwareAgreement( owner );
                        //}
                        else {
                            System.err.println( "SoftwareAgreementManager.MessagePane.hyperlinkUpdate: unsupported hyperlink, description=" + e.getDescription() );
                        }
                    }
                }
            } );
            setBackground( new JPanel().getBackground() );//see #1275
        }

        private static void showStatisticsDetails( JDialog owner, SessionMessage sessionMessage ) {
            new StatisticsDetailsDialog( owner, sessionMessage ).setVisible( true );
        }

        //private static void showSoftwareAgreement( JDialog owner ) {
           // new SoftwareAgreementDialog( owner ).setVisible( true );
        //}
    }

    public static void main( String[] args ) {
        JDialog dialog = new AcceptanceDialog( null, SessionMessage.getInstance() );
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
