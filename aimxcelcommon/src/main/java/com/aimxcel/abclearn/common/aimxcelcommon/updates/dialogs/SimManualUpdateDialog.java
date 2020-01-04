
package com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs;

import java.awt.Frame;

import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;

/**
 * Notifies the user about a sim update when the user requested one.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SimManualUpdateDialog extends SimAbstractUpdateDialog {

    public SimManualUpdateDialog( Frame owner, ISimInfo simInfo, AimxcelVersion newVersion ) {
        super( owner, simInfo, newVersion );
        initGUI();
    }

    protected JPanel createButtonPanel( ISimInfo simInfo, final AimxcelVersion newVersion ) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add( new UpdateButton( this, simInfo, newVersion ) );
        buttonPanel.add( new CancelButton( this ) );
        return buttonPanel;
    }

}
