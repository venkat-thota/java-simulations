

package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.ColorControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class TabbedPanePropertiesDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabbedPanePropertiesDialog( final Frame parent, AimxcelTabbedPane tabbedPane ) {
        super( parent, "Tabbed Pane properties" );
        setResizable( false );
        setModal( false );

        JPanel inputPanel = createInputPanel( parent, tabbedPane );

        VerticalLayoutPanel panel = new VerticalLayoutPanel();
        panel.setFillHorizontal();
        panel.add( inputPanel );

        setContentPane( panel );
        pack();
        SwingUtils.centerDialogInParent( this );
    }

    private JPanel createInputPanel( Frame parent, final AimxcelTabbedPane tabbedPane ) {

        // background color
        Color backgroundColor = tabbedPane.getBackground();
        final ColorControl backgroundColorControl = new ColorControl( parent, "background color: ", backgroundColor );
        backgroundColorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                tabbedPane.setBackground( backgroundColorControl.getColor() );
            }
        } );

        // selected tab color
        Color selectedTabColor = tabbedPane.getSelectedTabColor();
        final ColorControl selectedTabColorControl = new ColorControl( parent, "tab color (selected): ", selectedTabColor );
        selectedTabColorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                tabbedPane.setSelectedTabColor( selectedTabColorControl.getColor() );
            }
        } );

        // unselected tab color
        Color unselectedTabColor = tabbedPane.getUnselectedTabColor();
        final ColorControl unselectedTabColorControl = new ColorControl( parent, "tab color (unselected): ", unselectedTabColor );
        unselectedTabColorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                tabbedPane.setUnselectedTabColor( unselectedTabColorControl.getColor() );
            }
        } );

        // selected text color
        Color selectedTextColor = tabbedPane.getSelectedTextColor();
        final ColorControl selectedTextColorControl = new ColorControl( parent, "text color (selected): ", selectedTextColor );
        selectedTextColorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                tabbedPane.setSelectedTextColor( selectedTextColorControl.getColor() );
            }
        } );

        // unselected text color
        Color unselectedTextColor = tabbedPane.getUnselectedTextColor();
        final ColorControl unselectedTextColorControl = new ColorControl( parent, "text color (unselected): ", unselectedTextColor );
        unselectedTextColorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                tabbedPane.setUnselectedTextColor( unselectedTextColorControl.getColor() );
            }
        } );

        // Layout
        JPanel panel = new JPanel();
        panel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        EasyGridBagLayout layout = new EasyGridBagLayout( panel );
        layout.setInsets( new Insets( 3, 5, 3, 5 ) );
        panel.setLayout( layout );
        int row = 0;
        int column = 0;
        layout.addComponent( backgroundColorControl, row++, column );
        layout.addComponent( selectedTabColorControl, row++, column );
        layout.addComponent( unselectedTabColorControl, row++, column );
        layout.addComponent( selectedTextColorControl, row++, column );
        layout.addComponent( unselectedTextColorControl, row++, column );

        return panel;
    }
}
