
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class DialogCheckBoxMenuItem extends JCheckBoxMenuItem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Frame parent;
    private final String dialogTitle;
    private final Function0<Container> createDialogContentPane;
    private JDialog dialog;

    /**
     * Constructor
     *
     * @param menuItemText            text of the menu item
     * @param dialogTitle             title of the dialog
     * @param parent                  parent frame of the dialog
     * @param createDialogContentPane function to create content pane of the dialog
     */
    public DialogCheckBoxMenuItem( String menuItemText, String dialogTitle, final Frame parent, Function0<Container> createDialogContentPane ) {
        super( menuItemText );
        this.parent = parent;
        this.dialogTitle = dialogTitle;
        this.createDialogContentPane = createDialogContentPane;
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( isSelected() ) {
                    openDialog();
                }
                else {
                    closeDialog();
                }
            }
        } );
    }

    protected void openDialog() {
        if ( dialog != null ) {
            closeDialog();
        }
        dialog = new JDialog( parent ) {{
            setTitle( dialogTitle );
            setContentPane( createDialogContentPane.apply() );
            pack();
            addWindowListener( new WindowAdapter() {

                // called when the close button in the dialog's window dressing is clicked
                @Override
                public void windowClosing( WindowEvent e ) {
                    closeDialog();
                }

                // called by JDialog.dispose
                @Override
                public void windowClosed( WindowEvent e ) {
                    closeDialog();
                    if ( isSelected() ) {
                        setSelected( false );
                    }
                }
            } );
        }};
        SwingUtils.centerInParent( dialog );
        dialog.setVisible( true );
    }

    protected void closeDialog() {
        if ( dialog != null ) {
            dialog.dispose();
            dialog = null;
        }
    }
}
