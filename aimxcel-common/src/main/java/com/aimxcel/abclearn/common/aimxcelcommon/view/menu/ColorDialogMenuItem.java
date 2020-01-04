
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ColorChooserFactory;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ColorChooserFactory.Listener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class ColorDialogMenuItem extends SimSharingJMenuItem {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColorDialogMenuItem( IUserComponent component, Component parent, String title, final Property<Color> colorProperty ) {
        super( component, title );

        //Adapter to pass information from the ColorChooserFactory.Listener to the Property<Color>
        Listener listener = new Listener() {
            public void colorChanged( Color color ) {
                colorProperty.set( color );
            }

            public void ok( Color color ) {
                colorProperty.set( color );
            }

            public void cancelled( Color originalColor ) {
                colorProperty.set( originalColor );
            }
        };

        //Create the dialog
        final JDialog dialog = ColorChooserFactory.createDialog( title, parent, colorProperty.get(), listener );

        //Show the dialog if/when the user presses the button
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                SwingUtils.centerInParent( dialog );
                dialog.setVisible( true );
            }
        } );
    }
}
