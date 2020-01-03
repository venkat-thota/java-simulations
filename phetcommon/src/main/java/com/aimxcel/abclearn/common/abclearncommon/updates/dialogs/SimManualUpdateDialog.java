
package com.aimxcel.abclearn.common.abclearncommon.updates.dialogs;

import java.awt.Frame;

import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.application.ISimInfo;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnVersion;

/**
 * Notifies the user about a sim update when the user requested one.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SimManualUpdateDialog extends SimAbstractUpdateDialog {

    public SimManualUpdateDialog( Frame owner, ISimInfo simInfo, AbcLearnVersion newVersion ) {
        super( owner, simInfo, newVersion );
        initGUI();
    }

    protected JPanel createButtonPanel( ISimInfo simInfo, final AbcLearnVersion newVersion ) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add( new UpdateButton( this, simInfo, newVersion ) );
        buttonPanel.add( new CancelButton( this ) );
        return buttonPanel;
    }

}
