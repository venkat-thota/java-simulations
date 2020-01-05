
package com.aimxcel.abclearn.motion.graphs;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.motion.util.GraphicsUtil;

/**
 * User: Sam Reid
 * Date: Dec 28, 2006
 * Time: 8:16:34 AM
 */

public class GraphSelectionControl extends JPanel {

    public GraphSelectionControl( GraphSuiteSet graphSuiteSet, final GraphSetModel graphSetModel ) {
        setLayout( new GridBagLayout() );
        GridBagConstraints gridBagConstraints = new GridBagConstraints( 0, GridBagConstraints.RELATIVE, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 );
        JLabel label = new JLabel( AimxcelCommonResources.getString( "charts.show-graphs" ) );
        label.setFont( new AimxcelFont( Font.PLAIN, 16 ) );
        add( label, gridBagConstraints );
        for ( int i = 0; i < graphSuiteSet.getNumGraphSuites(); i++ ) {
            add( new GraphSuiteRadioButton( graphSetModel, graphSuiteSet.getGraphSuite( i ) ), gridBagConstraints );
        }
    }

    public static class GraphSuiteRadioButton extends JRadioButton {
        private GraphSetModel graphSetPanel;
        private GraphSuite graphSuite;

        public GraphSuiteRadioButton( final GraphSetModel graphSetModel, final GraphSuite graphSuite ) {
            super( graphSuite.getLabel(), graphSetModel.getGraphSuite() == graphSuite );
            this.graphSetPanel = graphSetModel;
            this.graphSuite = graphSuite;
            setFont( new AimxcelFont( Font.PLAIN, 16 ) );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    graphSetModel.setGraphSuite( graphSuite );
                }
            } );
            graphSetModel.addListener( new GraphSetModel.Listener() {
                public void graphSuiteChanged() {
                    setSelected( graphSetModel.getGraphSuite() == graphSuite );
                }
            } );
        }

        protected void paintComponent( Graphics g ) {
            boolean aa = GraphicsUtil.antialias( g, true );
            super.paintComponent( g );
            GraphicsUtil.antialias( g, aa );
        }
    }

}
