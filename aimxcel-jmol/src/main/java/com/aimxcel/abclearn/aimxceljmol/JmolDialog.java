package com.aimxcel.abclearn.aimxceljmol;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

public class JmolDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JmolPanel jmolPanel;

    public JmolDialog( Frame owner, Molecule molecule, final String spacefillString, final String ballAndStickString, String loadingString ) {
        super( owner );

        setTitle( molecule.getDisplayName() );
        setSize( 410, 410 );

        JPanel container = new JPanel( new BorderLayout() );
        setContentPane( container );

        jmolPanel = new JmolPanel( molecule, loadingString );
        getContentPane().add( jmolPanel, BorderLayout.CENTER );

        getContentPane().add( new JPanel() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final ButtonGroup group = new ButtonGroup();
            add( new JRadioButton( spacefillString, true ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{ // 50% size
                group.add( this );
                addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent e ) {
                        jmolPanel.setSpaceFill();
                    }
                } );
            }} );
            add( new JRadioButton( ballAndStickString, false ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                group.add( this );
                addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent e ) {
                        jmolPanel.setBallAndStick();
                    }
                } );
            }} );
        }}, BorderLayout.SOUTH );

        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
    }

    @Override public void dispose() {
        jmolPanel.destroy();

        super.dispose();
    }

    public JmolPanel getJmolPanel() {
        return jmolPanel;
    }

    public static JmolDialog displayMolecule3D( Frame frame, Molecule completeMolecule, String spaceFillString, String ballAndStickString, String loadingString ) {
        JmolDialog jmolDialog = new JmolDialog( frame, completeMolecule, spaceFillString, ballAndStickString, loadingString );
        SwingUtils.centerInParent( jmolDialog );
        jmolDialog.setVisible( true );
        return jmolDialog;
    }
}
