
package edu.colorado.phet.theramp.v2.view;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import edu.colorado.phet.theramp.v2.model.RampModel;
import edu.colorado.phet.theramp.v2.model.RampObject;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

public class RampModelView extends PNode {
    private TestRampModule module;

    public RampModelView( TestRampModule module ) {
        this.module = module;
        module.addListener( new TestRampModule.Listener() {
            public void notifyChanged() {
                update();
            }
        } );
        update();
    }

    private void update() {
        boolean createNewNodes = true;

        if ( createNewNodes ) {
            updateNewNodes();
        }
        else {
            updateOldNodes();
        }

    }

    private void updateNewNodes() {
        removeAllChildren();
        RampModel state = module.getCurrentState();
        for ( int i = 0; i < state.getObjectCount(); i++ ) {
            addChild( createNode( state.getObject( i ) ) );
        }
    }

    private void updateOldNodes() {
        if ( getChildrenCount() == 0 ) {
            updateNewNodes();
        }
        else {
            getChild( 0 ).translate( 1, 1 );
        }
    }

    private PNode createNode( final RampObject object ) {
        Rectangle2D.Double aDouble = new Rectangle2D.Double( object.getPosition().getX(), object.getPosition().getY(), 20, 20 );
        final PPath pPath = new PPath( aDouble );
        pPath.setPaint( Color.blue );
        PBasicInputEventHandler listener = new PBasicInputEventHandler() {
            public void mousePressed( PInputEvent event ) {
                super.mousePressed( event );
                module.updateCurrentState( object, object.setInteracting( true ) );
            }

            public void mouseDragged( PInputEvent event ) {
                super.mouseDragged( event );
                RampObject newObject = object.setPosition( event.getCanvasPosition().getX(), event.getCanvasPosition().getY() );
                module.updateCurrentState( object, newObject );
            }

            public void mouseReleased( PInputEvent event ) {
                super.mouseReleased( event );
                module.updateCurrentState( object, object.setInteracting( false ) );
            }
        };

        pPath.addInputEventListener( listener );
        pPath.addInputEventListener( new CursorHandler() );
        return pPath;
    }
}
