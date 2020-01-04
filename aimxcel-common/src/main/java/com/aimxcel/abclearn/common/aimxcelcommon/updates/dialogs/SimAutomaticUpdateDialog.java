
package com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs;

import java.awt.Frame;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.IAskMeLaterStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.IVersionSkipper;


public class SimAutomaticUpdateDialog extends SimAbstractUpdateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String PREFEENCES_MESSAGE = AimxcelCommonResources.getString( "Common.updates.seePreferences" );

    private final IAskMeLaterStrategy askMeLaterStrategy;
    private final IVersionSkipper versionSkipper;

    public SimAutomaticUpdateDialog( Frame owner, ISimInfo simInfo, AimxcelVersion newVersion, IAskMeLaterStrategy askMeLaterStrategy, IVersionSkipper versionSkipper ) {
        super( owner, simInfo, newVersion );
        this.askMeLaterStrategy = askMeLaterStrategy;
        this.versionSkipper = versionSkipper;
        initGUI();
    }

    protected JPanel createButtonPanel( ISimInfo simInfo, final AimxcelVersion newVersion ) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add( new UpdateButton( this, simInfo, newVersion ) );
        buttonPanel.add( Box.createHorizontalStrut( 30 ) );
        buttonPanel.add( new AskMeLaterButton( this, askMeLaterStrategy ) );
        buttonPanel.add( new SkipVersionButton( this, versionSkipper, newVersion ) );
        return buttonPanel;
    }

    /*
    * Message that will be added below the standard message, and above the button panel.
    */
    protected JComponent createAdditionalMessageComponent() {
        // message about how to access the Preferences dialog
        String preferencesHTML = "<html><font size=\"2\">" + PREFEENCES_MESSAGE + "</font></html>";
        return new JLabel( preferencesHTML );
    }

    public static void main( String[] args ) {
        System.out.println( "I'm running!" );
        AimxcelApplicationConfig config = new AimxcelApplicationConfig( args, "moving-man" );
        final SimAutomaticUpdateDialog dialog = new SimAutomaticUpdateDialog( new JFrame(), config, new AimxcelVersion( "1", "00", "05", "30000", "1234567890" ), new IAskMeLaterStrategy() {
            public void setStartTime( long time ) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public long getStartTime() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void setDuration( long duration ) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public long getDuration() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean isDurationExceeded() {
                return true;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, new IVersionSkipper() {
            public void setSkippedVersion( int skipVersion ) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public int getSkippedVersion() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public boolean isSkipped( int version ) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }
        );

        dialog.setVisible( true );

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                dialog.setSize( 800, 800 );
            }
        } );


    }
}
