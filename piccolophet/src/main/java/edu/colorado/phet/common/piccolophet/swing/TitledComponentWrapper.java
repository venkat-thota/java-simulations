
package edu.colorado.phet.common.piccolophet.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnTitledBorder;
import com.aimxcel.abclearn.common.abclearncommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

/**
 * In PhET panels, it is ofter necessary to ensure that the entire title of a panel is visible (for English as well as translations).
 * This can be done with a regular component such as a JLabel, but we also prefer the visual styling of a border
 * combined with the title.  The previous implementation in phetcommon had several disadvantages because it was using
 * setPreferredSize() based on the size of children.
 * <p/>
 * In this implementation, we add a new layout component (Box.createHorizontalStrut) to ensure the layout is correct,
 * and the border is rendered with a AbcLearnTitledBorder.  Here the client is required to pass in a JComponent child that will be wrapped with the title.
 * <p/>
 * One current problem with this implementation is that there is excess (too much?) space between the title and the first component in the wrapped child component.
 * See #2476
 *
 * @author Sam Reid
 */
public class TitledComponentWrapper {

    /*
     * Wraps the specified child swing component with the specified title.
     */
    public static JComponent wrapWithTitle( final String title, final Font titleFont, final Color titleColor, final JComponent child ) {
        //Uses a VerticalLayoutPanel internally to make sure the layout shows the entire title
        return new VerticalLayoutPanel() {{
            setBorder( new AbcLearnTitledBorder( title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont, titleColor ) );
            setAnchor( GridBagConstraints.WEST );
            setFillNone();

            //Determine the size of the title
            final int width = (int) new JLabel( title ) {{
                setFont( titleFont );
            }}.getPreferredSize().getWidth() +
                              5;//Add some extra spacing so that you can see the curve of the AbcLearnTitledBorder
            add( Box.createHorizontalStrut( width ) );//This strut ensures that the component will be big enough to show the entire title
            add( child );
        }};
    }

    /**
     * This sample main demonstrates usage of the AbcLearnTitledPanel
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final JComponent contentPane = wrapWithTitle( "aoensuthasnotehuBorder", new AbcLearnFont( 18 ), Color.blue, new VerticalLayoutPanel() {{
            for ( int i = 0; i < 10; i++ ) {
                add( new JLabel( "medium sized label " + i ) );
            }
            add( new JButton( "A button" ) );
        }} );

        frame.setContentPane( contentPane );
        frame.pack();
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
