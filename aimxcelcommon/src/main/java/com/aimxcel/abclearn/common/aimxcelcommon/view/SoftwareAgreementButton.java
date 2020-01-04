
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.aboutDialogSoftwareAgreementButton;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.SoftwareAgreementDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJButton;

/**
 * Pressing this button displays the Software Agreement in a dialog.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SoftwareAgreementButton extends SimSharingJButton {

    private static final String LABEL = AimxcelCommonResources.getString( "Common.About.AgreementButton" );

    public SoftwareAgreementButton( final Dialog parent ) {
        super( aboutDialogSoftwareAgreementButton );
        setText( LABEL );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                new SoftwareAgreementDialog( parent ).setVisible( true );
            }
        } );
    }
}