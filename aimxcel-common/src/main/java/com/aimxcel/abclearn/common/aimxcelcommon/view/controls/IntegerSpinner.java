

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJSpinner;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.util.IntegerRange;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;


public class IntegerSpinner extends SimSharingJSpinner {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IntegerRange range;
    private final JFormattedTextField textField;

    public IntegerSpinner( IUserComponent simSharingObject, IntegerRange range ) {
        super( simSharingObject );

        this.range = range;

        // number model
        setModel( new SpinnerNumberModel( range.getDefault(), range.getMin(), range.getMax(), 1 ) );

        // editor
        NumberEditor editor = new NumberEditor( this );
        setEditor( editor );

        // text field, commits when Enter is pressed or focus is lost
        textField = editor.getTextField();
        textField.setColumns( String.valueOf( range.getMax() ).length() );
        textField.addKeyListener( new KeyAdapter() {
            public void keyPressed( KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    commitEdit();
                }
            }
        } );
        textField.addFocusListener( new FocusAdapter() {

            public void focusLost( FocusEvent e ) {
                commitEdit();
            }

            /*
             * Workaround to select contents when textfield get focus.
             * See bug ID 4699955 at bugs.sun.com
             */
            public void focusGained( FocusEvent e ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        textField.selectAll();
                    }
                } );
            }
        } );
    }

    public void setTextForeground( Color color ) {
        textField.setForeground( color );
    }

    public void setTextBackground( Color color ) {
        textField.setBackground( color );
    }

    public void setIntValue( int value ) {
        setValue( new Integer( value ) );
    }

    public int getIntValue() {
        return ( (Integer) getValue() ).intValue();
    }

    @Override
    public void commitEdit() {
        try {
            //TODO this converts invalid entries like "12abc" to "12", standard JSpinner behavior but not desirable for Aimxcel
            super.commitEdit();
        }
        catch ( ParseException pe ) {
            handleInvalidValueDelayed( textField.getText() );
        }
    }

    // Workaround for #2218.
    private void handleInvalidValueDelayed( final String invalidValue ) {
        Timer t = new Timer( 500, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                handleInvalidValue( invalidValue );
            }
        } );
        t.setRepeats( false );
        t.start();
    }

    private void handleInvalidValue( String invalidValue ) {
        textField.setValue( getValue() ); // revert, sync text field to value
        Toolkit.getDefaultToolkit().beep();
        showInvalidValueDialog( invalidValue );
    }

    private void showInvalidValueDialog( String invalidValue ) {
        Object[] args = { new Integer( range.getMin() ), new Integer( range.getMax() ) };
        String message = MessageFormat.format( AimxcelCommonResources.getString( "message.valueOutOfRange" ), args );
        SimSharingManager.sendSystemMessage( SystemComponents.invalidValueDialog, SystemComponentTypes.window, SystemActions.windowOpened,
                                             ParameterSet.parameterSet( ParameterKeys.value, invalidValue ) );
        AimxcelOptionPane.showErrorDialog( this, message );
    }
}
