
package com.aimxcel.abclearn.common.aimxcelcommon.view.util;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

public class AimxcelOptionPane {

    private static final String TITLE_MESSAGE = AimxcelCommonResources.getInstance().getLocalizedString( "Common.title.message" );
    private static final String TITLE_CONFIRM = AimxcelCommonResources.getInstance().getLocalizedString( "Common.title.confirm" );
    private static final String TITLE_WARNING = AimxcelCommonResources.getInstance().getLocalizedString( "Common.title.warning" );
    private static final String TITLE_ERROR = AimxcelCommonResources.getInstance().getLocalizedString( "Common.title.error" );

    public static int showMessageDialog( Component parent, Object message ) {
        return showMessageDialog( parent, message, TITLE_MESSAGE );
    }

    public static int showMessageDialog( Component parent, Object message, String title ) {
        return showMessageDialog( parent, message, title, JOptionPane.INFORMATION_MESSAGE );
    }

    public static int showMessageDialog( Component parent, Object message, String title, int messageType ) {
        return showJOptionPaneDialog( parent, message, title, messageType );
    }

    public static int showOKCancelDialog( Component parent, Object message ) {
        return showOKCancelDialog( parent, message, TITLE_CONFIRM );
    }

    public static int showOKCancelDialog( Component parent, Object message, String title ) {
        return showJOptionPaneDialog( parent, message, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION );
    }

    public static int showYesNoDialog( Component parent, Object message ) {
        return showYesNoDialog( parent, message, TITLE_CONFIRM );
    }

    public static int showYesNoDialog( Component parent, Object message, String title ) {
        return showJOptionPaneDialog( parent, message, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION );
    }

    public static int showYesNoCancelDialog( Component parent, Object message ) {
        return showYesNoCancelDialog( parent, message, TITLE_CONFIRM );
    }

    public static int showYesNoCancelDialog( Component parent, Object message, String title ) {
        return showJOptionPaneDialog( parent, message, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION );
    }

    public static int showWarningDialog( Component parent, Object message ) {
        return showJOptionPaneDialog( parent, message, TITLE_WARNING, JOptionPane.WARNING_MESSAGE );
    }

    public static int showErrorDialog( Component parent, Object message ) {
        return showJOptionPaneDialog( parent, message, TITLE_ERROR, JOptionPane.ERROR_MESSAGE );
    }

    private static int showJOptionPaneDialog( Component parent, Object message, String title, int messageType ) {
        return showJOptionPaneDialog( parent, message, title, messageType, JOptionPane.DEFAULT_OPTION );
    }

    /*
    * Shows a dialog using a JOptionPane.
    * Return values are obtained from JOptionPane, see its javadoc.
    */
    private static int showJOptionPaneDialog( Component parent, Object message, String title, int messageType, int optionType ) {

        // Use a JOptionPane to get the right dialog look and layout
        JOptionPane pane = new JOptionPane( message, messageType, optionType );
        pane.selectInitialValue();

        // Create our own dialog to solve issue #89
        final JDialog dialog = createDialog( parent, title );
        dialog.getContentPane().add( pane );

        // Close the dialog when the user makes a selection
        pane.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent event ) {
                if ( event.getPropertyName().equals( JOptionPane.VALUE_PROPERTY ) ) {
                    dialog.setVisible( false );
                }
            }
        } );

        // pack the dialog first so it will be centered correctly
        dialog.pack();
        SwingUtils.centerDialog( dialog, parent );
        dialog.setVisible( true );

        // blocks here until user makes a choice
        dialog.dispose();

        // any not-int selection assumes the user closed the dialog via the window decoration
        int returnValue = JOptionPane.CLOSED_OPTION;
        Object paneValue = pane.getValue();
        if ( paneValue instanceof Integer ) {
            returnValue = ( (Integer) paneValue ).intValue();
        }
        return returnValue;
    }

    /*
     * Creates a PaintImmediateDialog.
     */
    private static JDialog createDialog( Component parent, String title ) {
        JDialog dialog = null;
        Window window = getWindowForComponent( parent );
        if ( window instanceof Frame ) {
            dialog = new PaintImmediateDialog( (Frame) window, title );
        }
        else {
            dialog = new PaintImmediateDialog( (Dialog) window, title );
        }
        dialog.setModal( true );
        dialog.setResizable( false );
        return dialog;
    }

    /*
    * JOptionPane.getWindowForComponent isn't public, reproduced here
    */
    private static Window getWindowForComponent( Component parentComponent ) throws HeadlessException {
        if ( parentComponent == null ) {
            return JOptionPane.getRootFrame();
        }
        if ( parentComponent instanceof Frame || parentComponent instanceof Dialog ) {
            return (Window) parentComponent;
        }
        return getWindowForComponent( parentComponent.getParent() );
    }

    /* tests */
    public static void main( String[] args ) {
        int value = AimxcelOptionPane.showMessageDialog( null, "message", "title" );
        System.out.println( "value=" + value );
        value = AimxcelOptionPane.showMessageDialog( null, new JLabel( "JLabel" ), "title" );
        System.out.println( "value=" + value );
        value = AimxcelOptionPane.showOKCancelDialog( null, "ok question", "title" );
        System.out.println( "value=" + value );
        value = AimxcelOptionPane.showYesNoDialog( null, "yes/no question", "title" );
        System.out.println( "value=" + value );
        value = AimxcelOptionPane.showWarningDialog( null, "warning" );
        System.out.println( "value=" + value );
        value = AimxcelOptionPane.showErrorDialog( null, "error" );
        System.out.println( "value=" + value );
        System.exit( 0 );
    }
}
