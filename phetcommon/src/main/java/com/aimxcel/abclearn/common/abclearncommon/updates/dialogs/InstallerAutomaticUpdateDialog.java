

package com.aimxcel.abclearn.common.abclearncommon.updates.dialogs;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnInstallerVersion;
import com.aimxcel.abclearn.common.abclearncommon.updates.IAskMeLaterStrategy;
import com.aimxcel.abclearn.common.abclearncommon.updates.InstallerAskMeLaterStrategy;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

/**
 * Notifies the user about a recommended installer update that was automatically discovered.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class InstallerAutomaticUpdateDialog extends InstallerAbstractUpdateDialog {

    private final IAskMeLaterStrategy askMeLaterStrategy;

    public InstallerAutomaticUpdateDialog( Frame owner, IAskMeLaterStrategy askMeLaterStrategy, AbcLearnInstallerVersion currentVersion, AbcLearnInstallerVersion newVersion ) {
        super( owner );
        this.askMeLaterStrategy = askMeLaterStrategy;
        initGUI( currentVersion, newVersion );
    }

    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add( new UpdateButton( this ) );
        buttonPanel.add( new AskMeLaterButton( this, askMeLaterStrategy ) );
        buttonPanel.add( new MoreButton( this ) );
        return buttonPanel;
    }

    /*
    * Test, this edits the real preferences file!
    */
    public static void main( String[] args ) {
        AbcLearnInstallerVersion currentVersion = new AbcLearnInstallerVersion( 1170313200L ); // Feb 1, 2007
        AbcLearnInstallerVersion newVersion = new AbcLearnInstallerVersion( 1175407200L ); // Apr 1, 2007
        InstallerAutomaticUpdateDialog dialog = new InstallerAutomaticUpdateDialog( null, new InstallerAskMeLaterStrategy(), currentVersion, newVersion );
        dialog.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit( 0 );
            }

            public void windowClosed( WindowEvent e ) {
                System.exit( 0 );
            }
        } );
        SwingUtils.centerWindowOnScreen( dialog );
        dialog.setVisible( true );
    }

}
