


package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AdvancedPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;

/**
 * Has an expand and collapse button.
 */

public class AdvancedPanel extends VerticalLayoutPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton advanced;
    private JButton hideButton;
    private VerticalLayoutPanel controls;
    private boolean expanded = false;

    /**
     * Create an AdvancedPanel with specified expansion and collapse button labels.
     *
     * @param show
     * @param hide
     */
    public AdvancedPanel( String show, String hide ) {
        controls = new VerticalLayoutPanel();
        controls.setFillNone();

        advanced = new JButton( show );
        advanced.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                showAdvanced();
            }
        } );

        hideButton = new JButton( hide );
        hideButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                hideAdvanced();
            }
        } );
        setFill( GridBagConstraints.NONE );
        add( advanced );
    }

    /**
     * Add another control to the expanded Advanced Panel.
     *
     * @param control
     */
    public void addControl( JComponent control ) {
        controls.add( control );
    }

    /**
     * Add another control to the expanded Advanced Panel, to span the width.
     *
     * @param control
     */
    public void addControlFullWidth( JComponent control ) {
        controls.addFullWidth( control );
    }

    protected void showAdvanced() {
        if ( !isExpanded() ) {
            expanded = true;
            remove( advanced );
            add( hideButton );
            add( controls );
            validateAll();
            setBorder( BorderFactory.createRaisedBevelBorder() );
            for ( int i = 0; i < listeners.size(); i++ ) {
                Listener listener = (Listener) listeners.get( i );
                listener.advancedPanelShown( this );
            }

        }
    }

    private void validateAll() {
        invalidate();
        controls.invalidate();
        if ( getParent() != null ) {
            getParent().invalidate();
            if ( getParent().getParent() != null ) {
                getParent().getParent().invalidate();
                getParent().getParent().validate();
            }
        }
        Window parent = SwingUtilities.getWindowAncestor( this );
//        if( parent instanceof JFrame ) {
//            JFrame parent = (JFrame)SwingUtilities.getWindowAncestor( this );
        parent.invalidate();
        parent.validate();
        parent.repaint();
//        }
    }

    /**
     * Sets whether the advanced controls should be displayed.
     *
     * @param visible true if the advanced controls should be displayed.
     */
    public void setAdvancedControlsVisible( boolean visible ) {
        if ( visible ) {
            showAdvanced();
        }
        else {
            hideAdvanced();
        }
    }

    protected void hideAdvanced() {
        if ( isExpanded() ) {
            expanded = false;
            remove( hideButton );
            remove( controls );
            add( advanced );
            validateAll();
            for ( int i = 0; i < listeners.size(); i++ ) {
                Listener listener = (Listener) listeners.get( i );
                listener.advancedPanelHidden( this );
            }
            setBorder( null );
        }
    }

    private boolean isExpanded() {
        return expanded;
    }

    ArrayList listeners = new ArrayList();

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public interface Listener {
        void advancedPanelHidden( AdvancedPanel advancedPanel );

        void advancedPanelShown( AdvancedPanel advancedPanel );
    }

    public VerticalLayoutPanel getControls() {
        return controls;
    }
}