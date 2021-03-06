
package com.aimxcel.abclearn.common.aimxcelcommon.preferences;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.SessionMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

/**
 * Dialog that appears when you press the "Details" button in the Statistics preferences panel.
 */
public class StatisticsDetailsDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PREFERRED_WIDTH = 450;

    private static final String TITLE = AimxcelCommonResources.getString( "Common.statistics.details.title" );
    private static final String DESCRIPTION = AimxcelCommonResources.getString( "Common.statistics.details.description" );
    private static final String CLOSE_BUTTON = AimxcelCommonResources.getString( "Common.choice.close" );

    public StatisticsDetailsDialog( Frame owner, SessionMessage sessionMessage ) {
        super( owner );
        init( sessionMessage );
    }

    public StatisticsDetailsDialog( Dialog owner, SessionMessage sessionMessage ) {
        super( owner );
        init( sessionMessage );
    }

    private void init( SessionMessage sessionMessage ) {

        setTitle( TITLE );
        setModal( true );
        setResizable( true );

        JComponent description = createDescription();
        JComponent report = createReport( sessionMessage );
        JComponent buttonPanel = createButtonPanel();

        JPanel panel = new JPanel( new BorderLayout() );
        panel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        panel.add( description, BorderLayout.NORTH );
        panel.add( report, BorderLayout.CENTER );
        panel.add( buttonPanel, BorderLayout.SOUTH );

        panel.setPreferredSize( new Dimension( PREFERRED_WIDTH, panel.getPreferredSize().height ) );
        setContentPane( panel );
        pack();
        SwingUtils.centerDialogInParent( this );
    }

    protected JComponent createDescription() {
        return new JLabel( DESCRIPTION );
    }

    protected JComponent createReport( SessionMessage sessionMessage ) {

        final JTextArea textArea = new JTextArea( "" );
        final String text = sessionMessage.toHumanReadable();
        if ( text != null ) {
            textArea.setText( text );
        }
        textArea.setEditable( false );

        JScrollPane scrollPane = new JScrollPane( textArea );
        scrollPane.setPreferredSize( new Dimension( scrollPane.getPreferredSize().width + 30, 200 ) );

        // this ensures that the first line of text is at the top of the scrollpane
        textArea.setCaretPosition( 0 );

        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        JButton closeButton = new JButton( CLOSE_BUTTON );
        closeButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );
        panel.add( closeButton );
        return panel;
    }
}
