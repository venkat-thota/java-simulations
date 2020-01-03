
package com.aimxcel.abclearn.common.abclearncommon.view.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.aimxcel.abclearn.common.abclearncommon.dialogs.ColorChooserFactory;
import com.aimxcel.abclearn.common.abclearncommon.dialogs.ColorChooserFactory.Listener;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Property;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

/**
 * This JMenuItem shows a Color dialog when selected so the user can change the color of the given color property.
 *
 * @author Sam Reid
 */
public class ColorDialogMenuItem extends SimSharingJMenuItem {
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
