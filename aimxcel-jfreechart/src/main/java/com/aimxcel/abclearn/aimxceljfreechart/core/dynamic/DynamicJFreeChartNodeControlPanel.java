
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.aimxcel2dcore.util.PDebug;

public class DynamicJFreeChartNodeControlPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DynamicJFreeChartNode dynamicJFreeChartNode;

    public DynamicJFreeChartNodeControlPanel( final DynamicJFreeChartNode dynamicJFreeChartNode ) {
        this.dynamicJFreeChartNode = dynamicJFreeChartNode;
        JPanel panel = this;
        ButtonGroup buttonGroup = new ButtonGroup();
        panel.add( createButton( "JFreeChart", buttonGroup, DynamicJFreeChartNode.RENDERER_JFREECHART ) );
        panel.add( createButton( "Core", buttonGroup, DynamicJFreeChartNode.RENDERER_PICCOLO ) );
        panel.add( createButton( "Core (incremental)", buttonGroup, DynamicJFreeChartNode.RENDERER_PICCOLO_INCREMENTAL ) );
        panel.add( createButton( "Core (incremental + immediate)", buttonGroup, DynamicJFreeChartNode.RENDERER_PICCOLO_INCREMENTAL_IMMEDIATE ) );
        panel.add( createButton( "Direct", buttonGroup, DynamicJFreeChartNode.RENDERER_BUFFERED ) );
        panel.add( createButton( "Direct (immediate)", buttonGroup, DynamicJFreeChartNode.RENDERER_BUFFERED_IMMEDIATE ) );
        final JCheckBox jCheckBox = new JCheckBox( "Buffered", dynamicJFreeChartNode.isBuffered() );
        jCheckBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dynamicJFreeChartNode.setBuffered( jCheckBox.isSelected() );
            }
        } );
        dynamicJFreeChartNode.addBufferedPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                jCheckBox.setSelected( dynamicJFreeChartNode.isBuffered() );
            }
        } );
        final JCheckBox debug = new JCheckBox( "Show Regions", PDebug.debugBounds );
        debug.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                PDebug.debugRegionManagement = debug.isSelected();
            }
        } );
        panel.add( jCheckBox );
        panel.add( debug );
    }

    private JComponent createButton( String text, ButtonGroup buttonGroup, final SeriesViewFactory viewFactory ) {
        JRadioButton jRadioButton = new JRadioButton( text, dynamicJFreeChartNode.getViewFactory() == viewFactory );
        buttonGroup.add( jRadioButton );
        jRadioButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dynamicJFreeChartNode.setViewFactory( viewFactory );
            }
        } );
        return jRadioButton;
    }

}
