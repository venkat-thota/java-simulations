

package com.aimxcel.abclearn.common.abclearncommon.dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.aimxcel.abclearn.common.abclearncommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;
import com.aimxcel.abclearn.common.abclearncommon.view.util.HTMLUtils.InteractiveHTMLPane;

/**
 * This dialog shows license information for a particular license (such as GPL) for
 * a contributed project.
 *
 * @author Sam Reid
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class ContribLicenseDialog extends PaintImmediateDialog {

    // preferred size for the scrollpane, change this to affect initial dialog size
    private static final Dimension SCROLLPANE_SIZE = new Dimension( 440, 300 );

    private static final String CLOSE_BUTTON = AbcLearnCommonResources.getString( "Common.choice.close" );

    public ContribLicenseDialog( Dialog owner, String title, String text ) {
        super( owner, title );
        setModal( true );

        // license in a scroll pane
        InteractiveHTMLPane htmlPane = new InteractiveHTMLPane( text );
        JScrollPane scrollPane = new JScrollPane( htmlPane );
        scrollPane.setPreferredSize( SCROLLPANE_SIZE );

        // Close button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton( CLOSE_BUTTON );
        closeButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                setVisible( false );
                dispose();
            }
        } );
        //buttonPanel.add( new LibraryLicensesButton(this ) );
        buttonPanel.add( closeButton );

        // layout
        JPanel panel = new JPanel( new BorderLayout() );
        panel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        panel.add( scrollPane, BorderLayout.CENTER );
        panel.add( buttonPanel, BorderLayout.SOUTH );
        setContentPane( panel );
        pack();
        SwingUtils.centerDialogInParent( this );
        htmlPane.setCaretPosition( 0 );
    }
}
