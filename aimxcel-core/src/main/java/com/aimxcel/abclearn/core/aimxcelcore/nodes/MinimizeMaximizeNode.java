

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class MinimizeMaximizeNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Public class data
    //----------------------------------------------------------------------------

    public static final int BUTTON_LEFT = SwingConstants.LEFT;
    public static final int BUTTON_RIGHT = SwingConstants.RIGHT;

    //----------------------------------------------------------------------------
    // Private class data
    //----------------------------------------------------------------------------

    private static final double DEFAULT_SPACING = 5; // space between text and button
    private static final Font DEFAULT_FONT = new AimxcelFont();
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private final static Image MINIMIZE_IMAGE;
    private final static Image MAXIMIZE_IMAGE;

    static {
        MINIMIZE_IMAGE = AimxcelCommonResources.getMinimizeButtonImage();
        MAXIMIZE_IMAGE = AimxcelCommonResources.getMaximizeButtonImage();
    }

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private boolean minimized;
    private EventListenerList listenerList;
    private PText textNode;
    private PImage buttonNode;
    private final IUserComponent minimizeButtonComponent;
    private final IUserComponent maximizeButtonComponent;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructor that uses default properties.
     *
     * @param text
     * @param buttonPosition BUTTON_LEFT or BUTTON_RIGHT
     */
    public MinimizeMaximizeNode( IUserComponent minimizeButtonComponent, IUserComponent maximizeButtonComponent, String text, int buttonPosition ) {
        this( minimizeButtonComponent, maximizeButtonComponent, text, buttonPosition, DEFAULT_FONT, DEFAULT_TEXT_COLOR, DEFAULT_SPACING );
    }

    /**
     * Fully-specified constructor.
     *
     * @param text
     * @param buttonPosition BUTTON_LEFT or BUTTON_RIGHT
     * @param textFont
     * @param textPaint
     * @param spacing        horizontal space between text and button
     */
    public MinimizeMaximizeNode( final IUserComponent minimizeButtonComponent, final IUserComponent maximizeButtonComponent, String text, int buttonPosition, Font textFont, Color textPaint, double spacing ) {
        super();
        this.minimizeButtonComponent = minimizeButtonComponent;
        this.maximizeButtonComponent = maximizeButtonComponent;

        if ( buttonPosition != BUTTON_LEFT && buttonPosition != BUTTON_RIGHT ) {
            throw new IllegalArgumentException( "invalid buttonPosition: " + buttonPosition );
        }

        listenerList = new EventListenerList();

        textNode = new PText( text );
        textNode.setPaint( null );
        textNode.setFont( textFont );
        textNode.setTextPaint( textPaint );
        addChild( textNode );

        buttonNode = new PImage( MAXIMIZE_IMAGE );
        addChild( buttonNode );

        final PNode thisNode = this;
        buttonNode.addInputEventListener( new PBasicInputEventHandler() {
            public void mouseReleased( PInputEvent event ) {
                minimized = !minimized;
                SimSharingManager.sendUserMessage( isMinimized() ? minimizeButtonComponent : maximizeButtonComponent, UserComponentTypes.button, UserActions.pressed );
                updateView();
                fireChangeEvent( new ChangeEvent( thisNode ) );
            }
        } );
        buttonNode.addInputEventListener( new CursorHandler() );

        // initialize state
        minimized = true;
        updateView();

        // positions, text and button centers are vertically aligned
        double maxHeight = Math.max( textNode.getFullBounds().getHeight(), buttonNode.getFullBounds().getHeight() );
        if ( buttonPosition == SwingConstants.RIGHT ) {
            textNode.setOffset( 0, ( maxHeight - textNode.getFullBounds().getHeight() ) / 2 );
            buttonNode.setOffset( textNode.getFullBounds().getWidth() + spacing, ( maxHeight - buttonNode.getFullBounds().getHeight() ) / 2 );
        }
        else {
            buttonNode.setOffset( 0, ( maxHeight - buttonNode.getFullBounds().getHeight() ) / 2 );
            textNode.setOffset( buttonNode.getFullBounds().getWidth() + spacing, ( maxHeight - textNode.getFullBounds().getHeight() ) / 2 );
        }
    }

    //----------------------------------------------------------------------------
    // Mutators and accessors
    //----------------------------------------------------------------------------

    public void setMinimized( boolean minimized ) {
        if ( minimized != this.minimized ) {
            this.minimized = minimized;
            updateView();
            fireChangeEvent( new ChangeEvent( this ) );
        }
    }

    public void setMaximized( boolean maximized ) {
        setMinimized( !maximized );
    }

    /**
     * Returns the state that things listening to this control should be in.
     * E.g., when this method returns true, listeners should be in the minimized state,
     * and but the control will show a maximize button.
     *
     * @return true or false
     */
    public boolean isMinimized() {
        return minimized;
    }

    /**
     * See isMinimized.
     *
     * @return true or false
     */
    public boolean isMaximized() {
        return !minimized;
    }

    /*
    * Updates the text and button to match the state.
    */
    private void updateView() {
        textNode.setVisible( minimized );
        buttonNode.setImage( minimized ? MAXIMIZE_IMAGE : MINIMIZE_IMAGE );
    }

    //----------------------------------------------------------------------------
    // Event handling
    //----------------------------------------------------------------------------

    public void addChangeListener( ChangeListener listener ) {
        listenerList.add( ChangeListener.class, listener );
    }

    public void removeChangeListener( ChangeListener listener ) {
        listenerList.remove( ChangeListener.class, listener );
    }

    public void fireChangeEvent( ChangeEvent event ) {
        Object[] listeners = listenerList.getListenerList();
        for ( int i = 0; i < listeners.length; i += 2 ) {
            if ( listeners[i] == ChangeListener.class ) {
                ( (ChangeListener) listeners[i + 1] ).stateChanged( event );
            }
        }
    }
}
