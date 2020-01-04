

package com.aimxcel.abclearn.common.aimxcelcommon.view;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.resetAllButton;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ResetAllButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ResetAllDelegate;


public class ResetAllButton extends SimSharingJButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ResetAllDelegate delegate; // delegate that implements Reset All behavior

    /**
     * @param parent parent component for the confirmation dialog
     */
    public ResetAllButton( final Component parent ) {
        this( new Resettable[0], parent );
    }

    /**
     * @param resettable thing to reset
     * @param parent     parent component for the confirmation dialog
     */
    public ResetAllButton( final Resettable resettable, final Component parent ) {
        this( new Resettable[] { resettable }, parent );
    }

    /**
     * @param resettables things to reset
     * @param parent      parent component for the confirmation dialog
     */
    public ResetAllButton( final Resettable[] resettables, final Component parent ) {
        super( resetAllButton, AimxcelCommonResources.getInstance().getLocalizedString( AimxcelCommonResources.STRING_RESET_ALL ) );
        this.delegate = new ResetAllDelegate( resettables, parent );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                delegate.resetAll();
            }
        } );
    }

    public void setConfirmationEnabled( boolean confirmationEnabled ) {
        delegate.setConfirmationEnabled( confirmationEnabled );
    }

    public boolean isConfirmationEnabled() {
        return delegate.isConfirmationEnabled();
    }

    public void addResettable( Resettable resettable ) {
        delegate.addResettable( resettable );
    }

    public void removeResettable( Resettable resettable ) {
        delegate.removeResettable( resettable );
    }

    public static void main( String[] args ) {

        Resettable resettable1 = new Resettable() {
            public void reset() {
                System.out.println( "resettable1.reset" );
            }
        };
        Resettable resettable2 = new Resettable() {
            public void reset() {
                System.out.println( "resettable2.reset" );
            }
        };

        ResetAllButton button1 = new ResetAllButton( resettable1, null );
        button1.addResettable( resettable2 );

        ResetAllButton button2 = new ResetAllButton( resettable1, null );
        button2.setConfirmationEnabled( false ); // disable confirmation
        button2.addResettable( resettable2 );

        JPanel panel = new JPanel();
        panel.add( button1 );
        panel.add( button2 );

        JFrame frame = new JFrame();
        frame.setContentPane( panel );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
