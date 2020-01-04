
package edu.colorado.phet.common.motion.charts;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.MinimizeMaximizeNode;

import edu.umd.cs.piccolo.PNode;

/**
 * @author Sam Reid
 */
public class MinimizeMaximizeButton extends PNode {
    private MinimizeMaximizeNode node;
    private BooleanProperty maximized;
    private final boolean defaultMaximizedValue;

    public MinimizeMaximizeButton( IUserComponent minimizeButtonComponent, IUserComponent maximizeButtonComponent, String title ) {
        this( minimizeButtonComponent, maximizeButtonComponent, title, true );
    }

    public MinimizeMaximizeButton( IUserComponent minimizeButtonComponent, IUserComponent maximizeButtonComponent, String title, boolean maximizedValue ) {

        node = new MinimizeMaximizeNode( minimizeButtonComponent, maximizeButtonComponent, title, MinimizeMaximizeNode.BUTTON_RIGHT );
        addChild( node );

        SimpleObserver maximizedObserver = new SimpleObserver() {
            public void update() {
                node.setMaximized( maximized.get() );
            }
        };

        this.maximized = new BooleanProperty( maximizedValue );
        this.maximized.addObserver( maximizedObserver );

        this.defaultMaximizedValue = maximizedValue;
        node.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                maximized.set( node.isMaximized() );
            }
        } );
    }

    public BooleanProperty getMaximized() {
        return maximized;
    }

    public void reset() {
        maximized.set( defaultMaximizedValue );
    }
}
