

package com.aimxcel.abclearn.core.aimxcelcore.help;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ListIterator;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class HelpPane extends GlassPaneCanvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     * @param parentFrame we serve as the glass pane for this frame
     */
    public HelpPane( JFrame parentFrame ) {
        super( parentFrame );
        addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                updateHelpItems();
            }
        } );
    }

    /**
     * Adds a help item.
     *
     * @param helpItem
     */
    public void add( AbstractHelpItem helpItem ) {
        getLayer().addChild( helpItem );
    }

    /**
     * Removes a help item.
     *
     * @param helpItem
     */
    public void remove( AbstractHelpItem helpItem ) {
        getLayer().removeChild( helpItem );
    }

    /**
     * Removes all help items.
     * Other nodes on the help pane are not affected.
     */
    public void clear() {
        ListIterator i = getLayer().getChildrenIterator();
        while ( i.hasNext() ) {
            PNode child = (PNode) i.next();
            if ( child instanceof AbstractHelpItem ) {
                getLayer().removeChild( child );
            }
        }
    }

    /**
     * Updates all help items.
     * Other nodes on the help pane are not affected.
     */
    public void updateHelpItems() {
        if ( isVisible() ) {
            ListIterator i = getLayer().getChildrenIterator();
            while ( i.hasNext() ) {
                PNode child = (PNode) i.next();
                if ( child instanceof AbstractHelpItem ) {
                    AbstractHelpItem helpItem = (AbstractHelpItem) child;
                    helpItem.updatePosition();
                }
            }
        }
    }

    /**
     * Shows and hides the help pane.
     * When the help pane is invisible, its help items are disabled
     * so that they don't consume resources tracking the position
     * and visibility of their target objects.
     *
     * @param visible
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        ListIterator i = getLayer().getChildrenIterator();
        while ( i.hasNext() ) {
            PNode child = (PNode) i.next();
            if ( child instanceof AbstractHelpItem ) {
                AbstractHelpItem helpItem = (AbstractHelpItem) child;
                helpItem.setEnabled( visible );
            }
        }
    }
}
